package com.zidar.accelerometer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class AccelerometerMonitorActivity extends Activity implements  AccelerometerListener	{
	private static Context CONTEXT;
	private int mBufferSize = 50;
	private int mCurPos = 0;
	private boolean mSaving = false;
	private StringBuffer mStatsBuffer;
	//private ImageView mGraphImage;
	
	boolean mExternalStorageAvailable = false;
	boolean mExternalStorageWriteable = false;
	
	private long filesize = 0;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		CONTEXT = this;
		mStatsBuffer = new StringBuffer();
		init();
	}
	
	private void init(){
		//mGraphImage = (ImageView) findViewById(R.id.imageView1);
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
	}
	
	protected void onResume() {
		super.onResume();
		if (AccelerometerManager.isSupported()) {
			AccelerometerManager.startListening(this);
		}
	}
	
	protected void onDestroy() {
		super.onDestroy();
		if (AccelerometerManager.isListening()) {
			AccelerometerManager.stopListening();
		}
	}
	
	public static Context getContext() {
		return CONTEXT;
	}
	
	/**
	 * onShake callback
	 */
	public void onShake(float force) {
		Toast.makeText(this, "Phone shaked : " + force, 1000).show();
	}
	
	/**
	 * onAccelerationChanged callback
	 */
	public void onAccelerationChanged(float x, float y, float z) {
		mCurPos++;
		mStatsBuffer.append("t: "+System.currentTimeMillis()+
				" x: "+String.valueOf(x)+
				" y: "+String.valueOf(y)+
				" z: "+String.valueOf(z)+
				" p: "+mCurPos);
		
		mStatsBuffer.append("\n");
		
		((TextView) findViewById(R.id.pos)).setText(String.valueOf(filesize));
		((TextView) findViewById(R.id.x)).setText(String.valueOf(x));
		((TextView) findViewById(R.id.y)).setText(String.valueOf(y));
		((TextView) findViewById(R.id.z)).setText(String.valueOf(z));
		
		if (mCurPos == mBufferSize ){
			writeBuffer(mStatsBuffer.toString());
			mStatsBuffer = new StringBuffer();
			mCurPos = 0;
		}
	}
	
	private synchronized void writeBuffer(final String stats){
		if (mSaving) return;
		mSaving = true;
		final String filename = "stats.txt";
		new Thread(){
			public void run() {
				try {
					File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
					if (!path.exists()){
						path.mkdirs();
					}
				    if (path != null && path.exists()) {
						Log.v("writing file", path.getPath()+"/"+filename);
				        File file = new File(path, filename);
				        filesize = file.length();
				        Log.v("filesize", ""+file.length());
				        BufferedWriter fw = new BufferedWriter(new FileWriter(file,true));
				        fw.write(stats);
				        fw.close();
				    } else {
						Log.v("cant write ", "oh noes");
				    }
				} catch (Exception e) {
					Log.e("thread ", e.toString());
				} finally {
					mSaving = false;
				}
			};
		}.start();
	}
}




