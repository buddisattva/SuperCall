package com.fakecall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class ScreebReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		 if ( ((TelephonyManager)context.getSystemService("phone")).getCallState() == 0 )
		    {
			Intent l = new Intent();
			
			l.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			l.setClass(context, LockerActivity.class);
			l.setAction(intent.getAction());
			context.startActivity(l);
			
		    } 	 
	}
}
