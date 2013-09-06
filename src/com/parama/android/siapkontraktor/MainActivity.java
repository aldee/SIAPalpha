package com.parama.android.siapkontraktor;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	
	ArrayList<HashMap<String, String>> dataList;
	HashMap<String, String> map;
	public static DefaultHttpClient httpClient;
	
	ListView lv;
	Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.listView1);
		dataList = new ArrayList<HashMap<String, String>>();
		map = new HashMap<String, String>();
		
		map.put("1", "Daftar Proyek");
		dataList.add(map);
		
		String[] from = new String[] {
			"1"
		};
		
		int[] to = new int[] {
			R.id.moduleText
		};
		
		ListAdapter adapter = new SimpleAdapter(getApplicationContext(), dataList, R.layout.module, from, to);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0) {
					i = new Intent(getApplicationContext(), ProjectListActivity.class);
					startActivity(i);
				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
