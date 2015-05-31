package com.fakecall;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.Window;
import android.widget.ImageView;
import com.fakecall.AndroidPreferences;

public class CallScreen extends Activity
{
  public void finishSelf()
  {
    try
    {
      finish();
      //overridePendingTransition(R.anim.common_nothing, R.anim.common_nothing);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public String getContactName()
  {
    return AndroidPreferences.getString(this, "contact_name");
  }

  public String getContactPhone()
  {
    return AndroidPreferences.getString(this, "contact_phone");
  }
  
  public String getContactID()
  {
	  return AndroidPreferences.getString(this, "contact_id");
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    getWindow().setFormat(1);
  }

  protected void setContactImage(ImageView paramImageView)
  {
	  /**
    Uri localUri = Uri.parse(AndroidPreferences.getString(this, "contact_photo"));
    if (localUri != null)
      paramImageView.setImageURI(localUri);
    while (true)
    {
      paramImageView.setVisibility(0);
      return;
    }**/
    
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
    final int REQUIRED_SIZE = 250;

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
    return;
  }
  
  protected Bitmap loadContactPhoto(ContentResolver cr, int id)
  {
	  Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
	  InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
	  if (input == null) {
		  return null;
	  }
	  return BitmapFactory.decodeStream(input);
  }
}
