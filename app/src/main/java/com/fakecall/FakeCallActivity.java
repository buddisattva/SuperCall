package com.fakecall;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FakeCallActivity extends Activity{
	
	private MediaPlayer player;
	protected Activity context;
	
	public void onCreate(Bundle savedInstanceState) {
		
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.screen_htc_sense_2_2_incoming);
    	
        ImageView bg=(ImageView)findViewById(R.id.imgBackground);
        TextView txtContactName = (TextView)findViewById(R.id.txtContactName);
        TextView txtContactPhone = (TextView)findViewById(R.id.txtContactPhone);
        
        Bundle bundle = getIntent().getExtras();
        
        txtContactName.setText(bundle.getString("NAME"));
        txtContactPhone.setText(bundle.getString("PHONE"));
    }
	
	public void startRing()
	  {
	    try
	    {
	      Uri localUri = Uri.parse(AndroidPreferences.getString(this.context, "ringtone_uri"));
	      this.player = MediaPlayer.create(this.context, localUri);
	      this.player.start();
	      return;
	    }
	    catch (Exception localException)
	    {
	      while (true)
	        localException.printStackTrace();
	    }
	  }
	
}
