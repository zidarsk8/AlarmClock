package com.zidar.alarm;

import com.zidar.alarm.R;
import java.util.LinkedList;

public class Statistics {
	private int mBufferSize;
	private int mCurPos;
	private float[] mStats;
	private LinkedList<Float> mList;
	
	
	public Statistics() {
		mStats = new float[mBufferSize];
		mCurPos = 0;
		mList = new LinkedList<Float>();
		mList.addLast(1.3f);
		mList.addLast(2.3f);
		mList.addLast(3.3f);
		mList.addLast(4.3f);
		mList.addLast(5.3f);
		mList.removeFirst();
		
	}
	
	public float[] getmStats() {
		return mStats;
	}
	
	public void addStats(int x, int y, int z){
		mStats[mCurPos] = 3;
	}
	
}
