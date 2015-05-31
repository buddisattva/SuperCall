package com.fakecall;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class LockerService extends Service {
  protected static final String FLAG_ACTIVITY_NEW_TASK = null;
  KeyguardManager _keyguardManager = null;
  private KeyguardLock _keyguardLock = null;
private BroadcastReceiver mReceiver = new ScreebReceiver();
  
  public IBinder onBind(Intent arg0){
	  return null;
  }
  
  public void onCreate(){
	 
	  IntentFilter localIntentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
	    localIntentFilter.setPriority(1000);
	    registerReceiver(this.mReceiver, localIntentFilter);
	   
  }
  
  public void onStart(Intent intent,int startId){
	_keyguardManager=(KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);  
	_keyguardLock=_keyguardManager.newKeyguardLock("ScreenLocker");
    _keyguardLock.disableKeyguard();
    
   
  }
  public void onDestroy()
  {
    unregisterReceiver(this.mReceiver);
    super.onDestroy();
  }
}

