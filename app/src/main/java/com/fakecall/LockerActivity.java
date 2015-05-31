package com.fakecall;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class LockerActivity extends Activity implements OnGesturePerformedListener { 
	 GestureLibrary _library;
	 
	private GestureOverlayView gesOverlay;

	public void onCreate(Bundle saveInstanceState){
		
	  
		super.onCreate(saveInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.gesturemain);
		gesOverlay = (GestureOverlayView)findViewById(R.id.gestures01);
		gesOverlay.addOnGesturePerformedListener(this);
		
	_library = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if(!_library.load()){
			
			finish();	
		}  
		
		
	  }
  /* �۩w�qOnGesturePerformedListener */
 
    public void onGesturePerformed(GestureOverlayView overlay,
                                   Gesture gesture) 
    {  
      /* ��դ�� */
      ArrayList<Prediction> predictions = _library.recognize(gesture); 
      if (predictions.size() > 0) 
      { 
        /* ���̱��񪺤�� */
        Prediction prediction = (Prediction) predictions.get(0); 
        /* ���o�w���Ȧܤ֤j��1.0 */ 
        if (prediction.score > 1.0) 
        { 
        	if(prediction.name.indexOf("open") !=-1){
        		
        		
        		Intent intent = new Intent();
				intent.setClass( LockerActivity.this, HTCSense_2_2_incoming_.class);
				
			    
				startActivity(intent);
        		
        		LockerActivity.this.finish();
            
				
				
        	}
          /* prediction.name�Y���r��w�����G  */
          
        }
       
  }
    }
}





