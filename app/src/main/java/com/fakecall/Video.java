package com.fakecall;

import java.io.File;
import java.net.URISyntaxException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/* Activity��@SurfaceHolder.Callback */
public class Video extends Activity implements SurfaceHolder.Callback
{
  private TextView mTextView01;
  private static final String TAG = "HIPPO_MediaPlayer";
  
  /* �إ�MediaPlayer���� */
  private MediaPlayer mMediaPlayer01;
  
  /* SurfaceView����@��Layout�W����X���� */
  private SurfaceView mSurfaceView01;
  
  /* �HSurfaceHolder�ӱ���SurfaceView */
  private SurfaceHolder mSurfaceHolder01;
  
  /* �|��ImageButton����MediaPlayer�ƥ� */
  private ImageButton mPlay;
  private ImageButton mPause;
  private ImageButton mReset;
  private ImageButton mStop;
  private ImageButton mSet;
  TextView textView2;
  /* MediaPlayer�Ȱ�flag */
  private boolean bIsPaused = false;
  
  /* MediaPlayer�Q����flag */
  private boolean bIsReleased = false;
  
  /* �N.3gp�v���ɦs��bSD�d���ڥؿ��� */
  private String strVideoPath = "";
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.vmain);
    
 
    
    /* �]�w�t��PixelFormae��UNKNOW */
    getWindow().setFormat(PixelFormat.UNKNOWN);
    
    /* ô��Layout�W��SurfaceView */
    mSurfaceView01 = (SurfaceView) findViewById(R.id.mSurfaceView1);
    /* �]�wSurfaceHolder��Layout SurfaceView���ù� */ 
    mSurfaceHolder01 = mSurfaceView01.getHolder();
    mSurfaceHolder01.addCallback(this);
    
    /* ��v����Size��176x144 */
    mSurfaceHolder01.setFixedSize(176,144);
    mSurfaceHolder01.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    
   textView2 = (TextView) findViewById(R.id.textView2);
    
    /* 4��ImageButton */
    mPlay = (ImageButton) findViewById(R.id.play); 
    mPause = (ImageButton) findViewById(R.id.pause); 
    mReset = (ImageButton) findViewById(R.id.reset); 
    mStop = (ImageButton) findViewById(R.id.stop);
    mSet = (ImageButton) findViewById(R.id.set);
    /* �]�w���եμv����m */
    strVideoPath = "";
    
   
    /* ������s */
    mPlay.setOnClickListener(new ImageButton.OnClickListener()
    { 
      
      
      
      public void onClick(View view)
      {
        // TODO Auto-generated method stub

       
    
     
     
     playVideo(strVideoPath);
       
     
       
     
      }
    });
    
    /* �Ȱ����s */
    mPause.setOnClickListener(new ImageButton.OnClickListener()
    {
      public void onClick(View view)
      {
        if(checkSDCard())
        {
          if (mMediaPlayer01 != null)
          {
            if(bIsReleased == false)
            {
              if(bIsPaused==false)
              {
                mMediaPlayer01.pause();
                bIsPaused = true;
                
              }
              else if(bIsPaused==true)
              {
                mMediaPlayer01.start();
                bIsPaused = false;
                
              }
            }
          }
        }
      }
    });
    
    mSet.setOnClickListener(new ImageButton.OnClickListener()
    {
      public void onClick(View view)
      {
        // �إ� "����ɮ� Action" �� Intent
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Intent intent = new Intent( Intent.ACTION_GET_CONTENT );
        // �L�o�ɮ׮榡
        intent.setType("video/*");

        // �إ� "�ɮ׿�ܾ�" �� Intent (�ĤG�ӰѼ�: ��ܾ������D)
        Intent destIntent = Intent.createChooser(intent, "����ɮ�");
        
        // �������ɮ׿�ܾ� (�����B�z���G, �|Ĳ�o onActivityResult �ƥ�)
        startActivityForResult(destIntent, 0);
        
      }
    });
    
    /* ���s������s */
    mReset.setOnClickListener(new ImageButton.OnClickListener()
    { 
      public void onClick(View view)
      {
        if(checkSDCard())
        {
          if(bIsReleased == false)
          {
            if (mMediaPlayer01 != null)
            { 
              mMediaPlayer01.seekTo(0); 
            }
          }
        }
      } 
    });
    
    /* �פ���s */
    mStop.setOnClickListener(new ImageButton.OnClickListener()
    { 
      public void onClick(View view)
      {
        if(checkSDCard())
        {
          if (mMediaPlayer01 != null)
          {
            if(bIsReleased==false)
            {
              mMediaPlayer01.stop();
              mMediaPlayer01.release();
              bIsReleased = true;
             
            }          
          }
        }
      }
    });
  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      switch (requestCode) {
          case 0:      
          if (resultCode == RESULT_OK) {  
              // Get the Uri of the selected file 
              Uri uri = data.getData();
              Log.d(TAG, "File Uri: " + uri.toString());
              // Get the path
              String path="";
              try
              {
                path = getPath(this, uri);
              }
              catch (URISyntaxException e)
              {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
              strVideoPath=path;
              Log.d(TAG, "File Path: " + path);
              //Toast.makeText(this, path, Toast.LENGTH_LONG);
              // Get the file instance
              // File file = new File(path);
              // Initiate the upload
              textView2.setText("File Path: " + path);
          }           
          break;
      }
  super.onActivityResult(requestCode, resultCode, data);
  
  }
  
  public static String getPath(Context context, Uri uri) throws URISyntaxException {
    if ("content".equalsIgnoreCase(uri.getScheme())) {
        String[] projection = { "_data" };
        Cursor cursor = null;

        try {
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor
            .getColumnIndexOrThrow("_data");
            if (cursor.moveToFirst()) {
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            // Eat it
        }
    }

    else if ("file".equalsIgnoreCase(uri.getScheme())) {
        return uri.getPath();
    }

    return null;
}
  
  
  /* �ۭq����v����� */
  private void playVideo(String strPath)
  { 
    mMediaPlayer01 = new MediaPlayer();
    mMediaPlayer01.setAudioStreamType(AudioManager.STREAM_MUSIC);
    
    /* �]�wVideo�v���HSurfaceHolder���� */
    mMediaPlayer01.setDisplay(mSurfaceHolder01);  
    
    try
    { 
      mMediaPlayer01.setDataSource(strPath); 
    }
    catch (Exception e)
    { 
      // TODO Auto-generated catch block
      mTextView01.setText("setDataSource Exceeption:"+e.toString());
      e.printStackTrace();
    }
    
    try
    { 
      mMediaPlayer01.prepare();
    }
    catch (Exception e)
    { 
      // TODO Auto-generated catch block
      mTextView01.setText("prepare Exceeption:"+e.toString());
      e.printStackTrace(); 
    }
    mMediaPlayer01.start();
    bIsReleased = false;
   
    
    mMediaPlayer01.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
    {
      public void onCompletion(MediaPlayer arg0)
      {
        // TODO Auto-generated method stub
       
      }
    });
  }
  
  private boolean checkSDCard()
  {
    /* �P�_�O�Хd�O�_�s�b */
    if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
    {
      File f = new File(strVideoPath);
      if(f.exists())
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    else
    {
      return false;
    }
  }
  
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(Video.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(Video.this, str, Toast.LENGTH_SHORT).show();
    }
  }
  
  public void surfaceChanged(SurfaceHolder surfaceholder, int format, int w, int h)
  {
    // TODO Auto-generated method stub
    Log.i(TAG, "Surface Changed");
  }
  
  public void surfaceCreated(SurfaceHolder surfaceholder)
  {
    // TODO Auto-generated method stub
    Log.i(TAG, "Surface Changed");
  }
  
  public void surfaceDestroyed(SurfaceHolder surfaceholder)
  {
    // TODO Auto-generated method stub
    Log.i(TAG, "Surface Destroyed");
  }
 
}
