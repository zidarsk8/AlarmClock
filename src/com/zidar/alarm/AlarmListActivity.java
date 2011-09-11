package com.zidar.alarm;

import com.zidar.alarm.R;

import android.app.ListActivity;
import android.os.Bundle;

public class AlarmListActivity extends ListActivity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
	}
}
