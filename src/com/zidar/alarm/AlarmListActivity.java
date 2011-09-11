package com.zidar.alarm;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class AlarmListActivity extends ListActivity {

	static final String[] FAKE_ALARMS = new String[]{ "8:00", "10:00", "9:00", "1:00", "12:00", "10:00", "9:00", "1:00", "12:00", "10:00", "9:00", "1:00", "12:00"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		setListAdapter(new MyAdapter(this, R.layout.list_item, FAKE_ALARMS));
		
		Button add = (Button)findViewById(R.id.add_alarm);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				startActivity(new Intent(AlarmListActivity.this, AlarmEditActivity.class));
			}
		});
	}
	
	static class ViewHolder{
		
		private TextView v1;
		private Button b;
	}
	
	private class MyAdapter extends ArrayAdapter<String>{
		
		LayoutInflater vi = (LayoutInflater) AlarmListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		private final String[] alarms;
		
		public MyAdapter(final Context context, final int resId, final String[] alarms){
			super(context, resId, alarms);
			this.alarms = alarms;
		}
		@Override
		public View getView(final int position, final View convertView, final ViewGroup parent){
			
			View v = convertView;
			if (v == null){
				
				v = vi.inflate(R.layout.list_item, null);
				ViewHolder vh = new ViewHolder();
				vh.v1 = (TextView) v.findViewById(R.id.list_item_text);
			    vh.b = (Button) v.findViewById(R.id.list_item_button);
				v.setTag(vh);
			}
			String a = alarms[position];
			if (a != null){
				
				ViewHolder vh = (ViewHolder) v.getTag();
				vh.v1.setText("Some awesome decription text");
				vh.b.setText(a);
				
			}
			return v;
		}
		
	}
	
}

