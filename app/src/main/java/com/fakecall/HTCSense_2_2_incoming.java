package com.fakecall;

import android.content.ContentResolver;
import android.widget.ImageView;
import android.widget.TextView;

public class HTCSense_2_2_incoming extends CallScreenIncoming
{
  protected TextView txtContactName;
  protected TextView txtContactPhoto;
  protected ImageView contactPhoto;
  private String contactID;

  public void btnAcceptClicked()
  {
    accept();
  }

  public void btnDeclineClicked()
  {
    decline();
  }

  public void initActivity()
  {
    super.initActivity();
    this.txtContactName.setText(getContactName());
    this.txtContactPhoto.setText(getContactPhone());
    contactID=getContactID();
    ContentResolver cr = getContentResolver();
    if(MainActivity.PHOTO_BYCONTACT && contactID!="" && loadContactPhoto(cr, Integer.parseInt(contactID))!=null) 
    	this.contactPhoto.setImageBitmap( loadContactPhoto(cr, Integer.parseInt(contactID)) );
    else if(MainActivity.PHOTO_BYFILE && AndroidPreferences.getString(this, "contact_photo")!="")
    	this.setContactImage(this.contactPhoto);
  }
}
