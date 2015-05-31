package com.fakecall;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AndroidPreferences
{
  public static String ACTIVITY_NAME = "YuTe";

  public static void editBoolean(Activity paramActivity, String paramString, Boolean paramBoolean)
  {
    setPreferenceBoolean(paramActivity, paramString, paramBoolean);
  }

  public static void editBoolean(Context paramContext, String paramString, Boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(ACTIVITY_NAME, 0).edit();
    localEditor.putBoolean(paramString, paramBoolean.booleanValue());
    localEditor.commit();
  }

  public static void editFloat(Activity paramActivity, String paramString, Float paramFloat)
  {
    setPreferenceFloat(paramActivity, paramString, paramFloat);
  }

  public static void editFloat(Context paramContext, String paramString, Float paramFloat)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(ACTIVITY_NAME, 0).edit();
    localEditor.putFloat(paramString, paramFloat.floatValue());
    localEditor.commit();
  }

  public static void editInt(Activity paramActivity, String paramString, int paramInt)
  {
    setPreferenceInt(paramActivity, paramString, paramInt);
  }

  public static void editInt(Context paramContext, String paramString, int paramInt)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(ACTIVITY_NAME, 0).edit();
    localEditor.putInt(paramString, paramInt);
    localEditor.commit();
  }

  public static void editLong(Context paramContext, String paramString, long paramLong)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(ACTIVITY_NAME, 0).edit();
    localEditor.putLong(paramString, paramLong);
    localEditor.commit();
  }

  public static void editString(Activity paramActivity, String paramString1, String paramString2)
  {
    setPreferenceString(paramActivity, paramString1, paramString2);
  }

  public static void editString(Context paramContext, String paramString1, String paramString2)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(ACTIVITY_NAME, 0).edit();
    localEditor.putString(paramString1, paramString2);
    localEditor.commit();
  }

  public static Boolean getBoolean(Activity paramActivity, String paramString)
  {
    return getPreferenceBoolean(paramActivity, paramString);
  }

  public static Boolean getBoolean(Context paramContext, String paramString)
  {
    return Boolean.valueOf(paramContext.getSharedPreferences(ACTIVITY_NAME, 0).getBoolean(paramString, false));
  }

  public static Float getFloat(Activity paramActivity, String paramString)
  {
    return getPreferenceFloat(paramActivity, paramString);
  }

  public static Float getFloat(Context paramContext, String paramString)
  {
    return Float.valueOf(paramContext.getSharedPreferences(ACTIVITY_NAME, 0).getFloat(paramString, -1.0F));
  }

  public static int getInt(Activity paramActivity, String paramString)
  {
    return getPreferenceInt(paramActivity, paramString);
  }

  public static int getInt(Context paramContext, String paramString)
  {
    return paramContext.getSharedPreferences(ACTIVITY_NAME, 0).getInt(paramString, -1);
  }

  public static long getLong(Context paramContext, String paramString)
  {
    return paramContext.getSharedPreferences(ACTIVITY_NAME, 0).getLong(paramString, -1L);
  }

  public static Boolean getPreferenceBoolean(Activity paramActivity, String paramString)
  {
    return Boolean.valueOf(paramActivity.getSharedPreferences(ACTIVITY_NAME, 0).getBoolean(paramString, false));
  }

  public static Float getPreferenceFloat(Activity paramActivity, String paramString)
  {
    return Float.valueOf(paramActivity.getSharedPreferences(ACTIVITY_NAME, 0).getFloat(paramString, -1.0F));
  }

  public static int getPreferenceInt(Activity paramActivity, String paramString)
  {
    return paramActivity.getSharedPreferences(ACTIVITY_NAME, 0).getInt(paramString, -1);
  }

  public static String getPreferenceString(Activity paramActivity, String paramString)
  {
    return paramActivity.getSharedPreferences(ACTIVITY_NAME, 0).getString(paramString, "");
  }

  public static String getString(Activity paramActivity, String paramString)
  {
    return getPreferenceString(paramActivity, paramString);
  }

  public static String getString(Context paramContext, String paramString)
  {
    return paramContext.getSharedPreferences(ACTIVITY_NAME, 0).getString(paramString, "");
  }

  public static void setPreferenceBoolean(Activity paramActivity, String paramString, Boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramActivity.getSharedPreferences(ACTIVITY_NAME, 0).edit();
    localEditor.putBoolean(paramString, paramBoolean.booleanValue());
    localEditor.commit();
  }

  public static void setPreferenceFloat(Activity paramActivity, String paramString, Float paramFloat)
  {
    SharedPreferences.Editor localEditor = paramActivity.getSharedPreferences(ACTIVITY_NAME, 0).edit();
    localEditor.putFloat(paramString, paramFloat.floatValue());
    localEditor.commit();
  }

  public static void setPreferenceInt(Activity paramActivity, String paramString, int paramInt)
  {
    SharedPreferences.Editor localEditor = paramActivity.getSharedPreferences(ACTIVITY_NAME, 0).edit();
    localEditor.putInt(paramString, paramInt);
    localEditor.commit();
  }

  public static void setPreferenceString(Activity paramActivity, String paramString1, String paramString2)
  {
    SharedPreferences.Editor localEditor = paramActivity.getSharedPreferences(ACTIVITY_NAME, 0).edit();
    localEditor.putString(paramString1, paramString2);
    localEditor.commit();
  }
}
