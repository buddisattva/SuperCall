package com.fakecall;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidToast
{
  public static void toast(Context paramContext, String paramString)
  {
    Toast.makeText(paramContext, paramString, 0).show();
  }

  public static void toast(Context paramContext, String paramString, int paramInt)
  {
    toast(paramContext, paramString, paramInt, 1);
  }

  public static void toast(Context paramContext, String paramString, int paramInt1, int paramInt2)
  {
    Toast localToast = Toast.makeText(paramContext, paramString, paramInt2);
    View localView = localToast.getView();
    LinearLayout localLinearLayout = new LinearLayout(paramContext);
    localLinearLayout.setOrientation(0);
    localLinearLayout.setGravity(17);
    localLinearLayout.setBackgroundDrawable(localView.getBackground());
    ImageView localImageView = new ImageView(paramContext);
    localImageView.setImageResource(paramInt1);
    localImageView.setPadding(0, 0, 10, 0);
    TextView localTextView = new TextView(paramContext);
    localTextView.setText(paramString);
    localLinearLayout.addView(localImageView);
    localLinearLayout.addView(localTextView);
    localToast.setView(localLinearLayout);
    localToast.show();
  }
}

