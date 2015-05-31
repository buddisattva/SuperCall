package com.fakecall;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

public final class HTCSense_2_2_incoming_ extends HTCSense_2_2_incoming
{
  private void afterSetContentView_()
  {
    this.txtContactName = ((TextView)findViewById(R.id.txtContactName));
    this.txtContactPhoto = ((TextView)findViewById(R.id.txtContactPhone));
    this.contactPhoto = (ImageView)findViewById(R.id.imgAndroid);
    
    View localView1 = findViewById(R.id.btnDecline);
    if (localView1 != null)
      localView1.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          HTCSense_2_2_incoming_.this.btnDeclineClicked();
        }
      });
    View localView2 = findViewById(R.id.btnAccept);
    if (localView2 != null)
      localView2.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          HTCSense_2_2_incoming_.this.btnAcceptClicked();
        }
      });
    initActivity();
  }

  private void init_(Bundle paramBundle)
  {
    requestWindowFeature(1);
    getWindow().setFlags(1024, 1024);
  }

  public static IntentBuilder_ intent(Context paramContext)
  {
    return new IntentBuilder_(paramContext);
  }

  public void onCreate(Bundle paramBundle)
  {
    init_(paramBundle);
    super.onCreate(paramBundle);
    setContentView(R.layout.screen_htc_sense_2_2_incoming);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((SdkVersionHelper.getSdkInt() < 5) && (paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
      onBackPressed();
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void setContentView(int paramInt)
  {
    super.setContentView(paramInt);
    afterSetContentView_();
  }

  public void setContentView(View paramView)
  {
    super.setContentView(paramView);
    afterSetContentView_();
  }

  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    super.setContentView(paramView, paramLayoutParams);
    afterSetContentView_();
  }

  public static class IntentBuilder_
  {
    private Context context_;
    private final Intent intent_;

    public IntentBuilder_(Context paramContext)
    {
      this.context_ = paramContext;
      this.intent_ = new Intent(paramContext, HTCSense_2_2_incoming_.class);
    }

    public IntentBuilder_ flags(int paramInt)
    {
      this.intent_.setFlags(paramInt);
      return this;
    }

    public Intent get()
    {
      return this.intent_;
    }

    public void start()
    {
      this.context_.startActivity(this.intent_);
    }
  }
}
