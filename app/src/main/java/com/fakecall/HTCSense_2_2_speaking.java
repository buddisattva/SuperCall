package com.fakecall;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class HTCSense_2_2_speaking extends Activity {

	protected Button btnEndCall;
	protected TextView txtCallTimer;
	protected TextView txtContactName;
	protected TextView txtContactPhone;
	protected ImageView ContactPhoto;
	
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_htc_sense_2_2_speaking);
        
        txtContactName = ((TextView)findViewById(R.id.txtContactName));
        btnEndCall = ((Button)findViewById(R.id.btnEndCall));
        txtCallTimer = ((TextView)findViewById(R.id.txtCallTimer));
        txtContactPhone = ((TextView)findViewById(R.id.txtContactPhone));
        ContactPhoto = (ImageView)findViewById(R.id.imgAndroid);
        View localView = findViewById(R.id.btnEndCall);
        final Chronometer timer = (Chronometer)findViewById(R.id.incallTimer);
        
        timer.start();
        txtContactName.setText(AndroidPreferences.getString(this, "contact_name"));
        txtContactPhone.setText(AndroidPreferences.getString(this, "contact_phone"));
        
        ContentResolver cr = getContentResolver();
        if(MainActivity.PHOTO_BYCONTACT && AndroidPreferences.getString(this, "contact_id")!="" &&
        		loadContactPhoto( cr, Integer.parseInt( AndroidPreferences.getString(this, "contact_id") ))!=null) 
        	ContactPhoto.setImageBitmap( loadContactPhoto(cr, Integer.parseInt(AndroidPreferences.getString(this, "contact_id"))) );
        else if(MainActivity.PHOTO_BYFILE && AndroidPreferences.getString(this, "contact_photo")!="")
        	this.setContactImage(ContactPhoto);
        
        if (localView != null)
        	localView.setOnClickListener(new View.OnClickListener()
            {
        		public void onClick(View paramView)
        		{
        			btnEndCall.setBackgroundResource(R.drawable.btn_incall_inactive);
        			//writeCallLog();
        		    //stopConversation();
        		    //stopTimer();
        			timer.stop();
        		    finish();
        		}
            });

    }
    
    private Bitmap loadContactPhoto(ContentResolver cr, int id)
    {
  	  Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
  	  InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
  	  if (input == null) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_htcsense_2_2_speaking, menu);
        return true;
    }
    **/

    
}
