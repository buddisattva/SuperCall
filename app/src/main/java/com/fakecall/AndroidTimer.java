package com.fakecall;

import android.os.CountDownTimer;

public class AndroidTimer extends CountDownTimer
{
  private static final long COUNTDOWN_INTERVAL = 100L;
  private AndroidTimerListener listener;

  public AndroidTimer(AndroidTimerListener paramAndroidTimerListener, long paramLong)
  {
    super(1000L * paramLong, 100L);
    this.listener = paramAndroidTimerListener;
  }

  public void onFinish()
  {
    this.listener.onTimerFinish();
  }

  public void onTick(long paramLong)
  {
    int i = 1 + (int)Math.floor(paramLong / 1000L);
    this.listener.onTimerTick(i);
  }

  public static abstract interface AndroidTimerListener
  {
    public abstract void onTimerFinish();

    public abstract void onTimerTick(int paramInt);
  }
}
