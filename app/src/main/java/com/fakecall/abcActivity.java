package com.fakecall;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class abcActivity extends Activity {
	
    private ImageButton newcall,fastnew,record,video,about,dismiss;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc_main);
        
        record = (ImageButton) findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
    		
    		public void onClick(View v) {
    			Intent intentr = new Intent();
    			intentr.setClass( abcActivity.this,Record.class);
    			//Bundle bundle = new Bundle();
    			//bundle.putString("NAME", editName.getText().toString());
    			//bundle.putString("PHONE", editPhoneNumber.getText().toString());
    		
    			//intent.putExtras(bundle);
    			startActivity(intentr);
    		}
    	});
        
     
        
        newcall = (ImageButton) findViewById(R.id.newcall);
        newcall.setOnClickListener(new View.OnClickListener() {
    		
    		public void onClick(View v) {
    			Intent intentn = new Intent();
    			intentn.setClass( abcActivity.this,MainActivity.class);
    			//Bundle bundle = new Bundle();
    			//bundle.putString("NAME", editName.getText().toString());
    			//bundle.putString("PHONE", editPhoneNumber.getText().toString());
    		
    			//intent.putExtras(bundle);
    			startActivity(intentn);
    		}
        });
        dismiss = (ImageButton) findViewById(R.id.music);
        dismiss.setOnClickListener(new View.OnClickListener() {
    		
    		@SuppressLint("ShowToast")
			public void onClick(View v) {
    			Intent mService = new Intent(abcActivity.this,LockerService.class); 
    			stopService(mService);
    			Toast.makeText(abcActivity.this, "undo the gesturelock", Toast.LENGTH_SHORT ).show();
    		}
        });
    
        
       
        
        fastnew = (ImageButton) findViewById(R.id.fastnew);
        fastnew.setOnClickListener(new View.OnClickListener() {
    		
    		@SuppressLint("ShowToast")
			public void onClick(View v) {
    			Intent mService = new Intent(abcActivity.this,LockerService.class); 
    			mService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    			startService(mService);
    			Toast.makeText(v.getContext(), "已註冊至鎖屏畫面", Toast.LENGTH_SHORT).show();
    		}
        });
    
       
        final Dialog AboutDialog=new Dialog(abcActivity.this);
    	AboutDialog.setTitle("關於");
    	AboutDialog.setContentView(R.layout.aboutdialog);
    	
        about = (ImageButton) findViewById(R.id.warn);
        about.setOnClickListener(new View.OnClickListener() {
    	
    		public void onClick(View v) {
    			AboutDialog.show();
    			 Button back = (Button) AboutDialog.findViewById(R.id.BACKMENU);
    		        back.setOnClickListener(new View.OnClickListener() {
    		    		
    		    		@SuppressLint("ShowToast")
    					public void onClick(View v) {
    		    			AboutDialog.dismiss();
    		    		}
    		      
    		        });
    		}       
    	
    	});
        
    
    
    

    
    
    
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}

    