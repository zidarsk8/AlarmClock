package com.zidar.alarm;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class AlarmListActivity extends ListActivity {

	static final String[] FAKE_ALARMS = new String[]{ "Fake alarm 8:00", "Fake alarm 10:00", "Fake alarm 9:00", "Fake alarm 1:00", "Fake alarm 12:00", "Fake alarm 10:00", "Fake alarm 9:00", "Fake alarm 1:00", "Fake alarm 12:00", "Fake alarm 10:00", "Fake alarm 9:00", "Fake alarm 1:00", "Fake alarm 12:00" };
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, FAKE_ALARMS));
		
		Button add = (Button)findViewById(R.id.add_alarm);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				startActivity(new Intent(AlarmListActivity.this, AlarmEditActivity.class));
			}
		});
	}
}
