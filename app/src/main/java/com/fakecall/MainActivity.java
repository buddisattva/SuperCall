package com.fakecall;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText editName;
	EditText editPhoneNumber;
	TextView Name;
	TextView PhoneNumber;
	Button btnSend,btnRing,btnContact,btnPhoto,btnSet;
	private Activity context;
	private boolean uriNotNull=true;
	private AudioManager audioManager;
	String ContactName,ContactPhone,ContactID,ContactPhoto;
	ImageView imageViewContact;
	final int SELECT_RINGTONE=101,SELECT_CONTACT=102,SELECT_IMAGE=103;
	static boolean PHOTO_BYFILE=false,PHOTO_BYCONTACT=false;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        AndroidPreferences.ACTIVITY_NAME = "FakeCall";
        this.context = this;
        editName=(EditText)this.findViewById(R.id.editName);
        Name=(TextView)this.findViewById(R.id.textName);
        editPhoneNumber=(EditText)this.findViewById(R.id.editPhone);
        PhoneNumber=(TextView)this.findViewById(R.id.textPhone);
        btnSend=(Button)this.findViewById(R.id.btnSend);
        btnRing=(Button)this.findViewById(R.id.btnRingtonePick);
        btnContact=(Button)this.findViewById(R.id.btnContact);
        btnPhoto=(Button)this.findViewById(R.id.btnPhoto);
        btnSet=(Button)this.findViewById(R.id.btnSet);
        imageViewContact=(ImageView)this.findViewById(R.id.imageContact);
        imageViewContact.setAdjustViewBounds(true);
        imageViewContact.setMaxHeight(250);
        imageViewContact.setMaxWidth(250);
        
        //取得音量控制器
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        ContentResolver cr = getContentResolver();
        
        if( !AndroidPreferences.getString(this, "contact_name").equals("") )
        	editName.setText(AndroidPreferences.getString(this, "contact_name"));
        if( !AndroidPreferences.getString(this, "contact_phone").equals("") )
        	editPhoneNumber.setText(AndroidPreferences.getString(this, "contact_phone"));
        if(PHOTO_BYFILE && !PHOTO_BYCONTACT && AndroidPreferences.getString(this, "contact_photo")!="")
        	this.setContactImage(imageViewContact);
        else if(!PHOTO_BYFILE && PHOTO_BYCONTACT && AndroidPreferences.getString(this, "contact_id")!="" &&
        		loadContactPhoto( cr, Integer.parseInt( AndroidPreferences.getString(this, "contact_id") ))!=null) 
        	imageViewContact.setImageBitmap( loadContactPhoto(cr, Integer.parseInt(AndroidPreferences.getString(this, "contact_id"))) );
        
        editName.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable arg0) {
				ContactName=editName.getText().toString();
				ContactPhone=editPhoneNumber.getText().toString();
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
        });
        
        btnSend.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass( MainActivity.this, HTCSense_2_2_incoming_.class);
				
				AndroidPreferences.editString(context, "contact_name", editName.getText().toString());
			    AndroidPreferences.editString(context, "contact_phone", editPhoneNumber.getText().toString());
			    AndroidPreferences.editBoolean(context, "vibrate_on_call", true);
			    
				startActivity(intent);
				finish();
			}
		});
        
        btnRing.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
				Intent localIntent = new Intent("android.intent.action.RINGTONE_PICKER");
			    localIntent.putExtra("android.intent.extra.ringtone.TITLE", getString(R.string.ringtone_choose));
			    localIntent.putExtra("android.intent.extra.ringtone.SHOW_SILENT", false);
			    localIntent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", true);
			    localIntent.putExtra("android.intent.extra.ringtone.TYPE", 1);
			    startActivityForResult(localIntent, SELECT_RINGTONE);
			}
		});
        
        btnContact.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
	            startActivityForResult(i,SELECT_CONTACT);
			}
		});
        
        btnPhoto.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK,
			               android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, SELECT_IMAGE);
			}
		});
        
        btnSet.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				AndroidPreferences.editString(context, "contact_name", editName.getText().toString());
			    AndroidPreferences.editString(context, "contact_phone", editPhoneNumber.getText().toString());
			    AndroidPreferences.editBoolean(context, "vibrate_on_call", true);
			    Toast.makeText(MainActivity.this, "已完成設定", Toast.LENGTH_SHORT).show();
			}
		});
 
    }
    
	protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
    	
        ContactName = null;
        ContactPhone = null;
        if ((paramInt1 == SELECT_RINGTONE) && (paramInt2 == RESULT_OK))
        {
        	Uri localUri = (Uri)paramIntent.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
        if (localUri == null){
        	AndroidToast.toast(this.context, getString(R.string.toast_error_unexpected));
        	uriNotNull=false;
        }
        String str = RingtoneManager.getRingtone(this.context, localUri).getTitle(this.context);
        //AndroidLog.log(localUri.toString());
        AndroidPreferences.editString(this.context, "ringtone_name", str);
        AndroidPreferences.editString(this.context, "ringtone_uri", localUri.toString());
        }
        
        else if(paramInt1 == SELECT_CONTACT && paramInt2 == RESULT_OK){

        	ContentResolver cr = getContentResolver();

            Uri contactData = paramIntent.getData();
            Cursor c1 = managedQuery(contactData,null,null,null,null);

            if(c1.moveToFirst()){
            	ContactID = c1.getString(c1.getColumnIndex(ContactsContract.Contacts._ID));
            	ContactName = c1.getString(c1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            	editName.setText(ContactName);
            	
            	if (Integer.parseInt(c1.getString(c1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
            		Cursor pCur = cr.query(Phone.CONTENT_URI,null,Phone.CONTACT_ID +" = ?", new String[]{ContactID}, null);
	
                	while(pCur.moveToNext()){
                		ContactPhone = pCur.getString(pCur.getColumnIndex(Phone.NUMBER));
                		editPhoneNumber.setText(ContactPhone);
                	}
                }
            	if (loadContactPhoto(cr,Integer.parseInt(ContactID))!=null) {
            		imageViewContact.setImageBitmap( loadContactPhoto(cr,Integer.parseInt(ContactID)) );
            		//AndroidPreferences.editString(context, "contact_photo", null);
            		AndroidPreferences.editString(context, "contact_id", ContactID);
            		PHOTO_BYFILE = false;
                    PHOTO_BYCONTACT = true;
            	}
            	else if (loadContactPhoto(cr,Integer.parseInt(ContactID))==null){
            		//AndroidPreferences.editString(context, "contact_photo", null);
            		PHOTO_BYFILE = false;
                    PHOTO_BYCONTACT = false;
            	}
            }
        }
        
        else if(paramInt1 == SELECT_IMAGE && paramInt2 == RESULT_OK){
        	Uri selectedImage = paramIntent.getData();
        	//imageViewContact.setImageURI(selectedImage);
        	InputStream imageStream = null, imageStream2 = null;
			try {
				imageStream = getContentResolver().openInputStream(selectedImage);
				imageStream2 = getContentResolver().openInputStream(selectedImage);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;
	        BitmapFactory.decodeStream(imageStream2, null, o);

	        // The new size we want to scale to
	        final int REQUIRED_SIZE = 250;

	        // Find the correct scale value. It should be the power of 2.
	        int width_tmp = o.outWidth, height_tmp = o.outHeight;
	        int scale = 1;
	        while (true) {
	            if (width_tmp / 2 < REQUIRED_SIZE
	               || height_tmp / 2 < REQUIRED_SIZE) {
	                break;
	            }
	            width_tmp /= 2;
	            height_tmp /= 2;
	            scale *= 2;
	        }
			
			BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize = scale;
            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream, null, o2);
            imageViewContact.setImageBitmap(yourSelectedImage);
            //AndroidPreferences.editString(context, "contact_id", "");
            AndroidPreferences.editString(context, "contact_photo", selectedImage.toString());
            PHOTO_BYFILE = true;
            PHOTO_BYCONTACT = false;
        }
        
        while (true&&uriNotNull)
        {
        //updateCallType();
        //updateSettings();
        	super.onActivityResult(paramInt1, paramInt2, paramIntent);
        	return;
        }
    }
    
    private Bitmap loadContactPhoto(ContentResolver cr, int id) {
    	Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input == null) {
        	imageViewContact.setImageResource(R.drawable.in_call_bg);
            return null;
        }
        return BitmapFactory.decodeStream(input);
    }
    
    private void setContactImage(ImageView paramImageView)
    {
      Uri selectedImage = Uri.parse(AndroidPreferences.getString(this, "contact_photo"));
  	//imageViewContact.setImageURI(selectedImage);
  	InputStream imageStream = null, imageStream2 = null;
  	try {
  		imageStream = getContentResolver().openInputStream(selectedImage);
  		imageStream2 = getContentResolver().openInputStream(selectedImage);
  	} catch (FileNotFoundException e) {
  		e.printStackTrace();
  	}
  	// Decode image size
      BitmapFactory.Options o = new BitmapFactory.Options();
      o.inJustDecodeBounds = true;
      BitmapFactory.decodeStream(imageStream2, null, o);

      // The new size we want to scale to
      final int REQUIRED_SIZE = 150;

      // Find the correct scale value. It should be the power of 2.
      int width_tmp = o.outWidth, height_tmp = o.outHeight;
      int scale = 1;
      while (true) {
          if (height_tmp / 2 < REQUIRED_SIZE
             || width_tmp / 2 < REQUIRED_SIZE) {
              break;
          }
          width_tmp /= 2;
          height_tmp /= 2;
          scale *= 2;
      }
  	
  	BitmapFactory.Options o2 = new BitmapFactory.Options();
      o2.inSampleSize = scale;
      Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream, null, o2);
      paramImageView.setImageBitmap(yourSelectedImage);
    }
    
    
/**
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
**/ 

}
