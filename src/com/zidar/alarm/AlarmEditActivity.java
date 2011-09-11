package com.zidar.alarm;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class AlarmEditActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		
		// Show the timepicker in users time format:
		TimePicker tp = (TimePicker)findViewById(R.id.alarm_time);
		tp.setIs24HourView(DateFormat.is24HourFormat(this));


	}
}
