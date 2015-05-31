package com.fakecall;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ScreenLockerActivity extends Activity {
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.gesturemain);
		Intent mService = new Intent(ScreenLockerActivity.this,LockerService.class); 
		mService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(mService);
	}

 
 
 }
