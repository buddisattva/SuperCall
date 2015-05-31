package com.fakecall;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
//import com.mdb.android.fakecall.models.RingTime;
//import com.mdb.android.fakecall.preferences.Prefs;
//import com.mdb.android.fakecall.utils.CallLauncher;
//import com.fakecall.AndroidPreferences;
import com.fakecall.AndroidTimer;

public class CallScreenIncoming extends CallScreen
  implements AndroidTimer.AndroidTimerListener
{
  protected Activity context;
  private AndroidTimer maxRingTimeWaiter;
  private MediaPlayer player;

  public void accept()
  {
	  	Intent localIntent = null;
	    localIntent = new Intent(this.context, HTCSense_2_2_speaking.class);
	    startActivity(localIntent);
	    this.maxRingTimeWaiter.cancel();
	    stopIncomingScreen();
  }

  public void decline()
  {
	this.maxRingTimeWaiter.cancel();
    stopIncomingScreen();
  }

  public void initActivity()
  {
    this.context = this;
    startIncomingScreen();
  }

  public void onTimerFinish()
  {
    stopIncomingScreen();
  }

  public void onTimerTick(int paramInt)
  {
  }

  public void startIncomingScreen()
  {
    startRing();
    startVibration();
    startMaxRintTimeWaiter();
  }

  public void startMaxRintTimeWaiter()
  {
    this.maxRingTimeWaiter = new AndroidTimer(this, 10);
    this.maxRingTimeWaiter.start();
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

  public void startVibration()
  {
    try
    {
      if (AndroidPreferences.getBoolean(this.context, "vibrate_on_call").booleanValue())
      {
        Vibrator localVibrator = (Vibrator)getSystemService("vibrator");
        long[] arrayOfLong = new long[3];
        arrayOfLong[1] = 500L;
        arrayOfLong[2] = 500L;
        localVibrator.vibrate(arrayOfLong, 0);
      }
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void stopIncomingScreen()
  {
    stopRing();
    stopVibration();
    finishSelf();
  }

  public void stopMaxRintTimeWaiter()
  {
    try
    {
      if (this.maxRingTimeWaiter != null)
        this.maxRingTimeWaiter.cancel();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void stopRing()
  {
    try
    {
      if ((this.player != null) && (this.player.isPlaying()))
      {
        this.player.stop();
        this.player.release();
      }
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void stopVibration()
  {
    try
    {
      ((Vibrator)getSystemService("vibrator")).cancel();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

