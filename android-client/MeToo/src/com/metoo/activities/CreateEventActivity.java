package com.metoo.activities;

import com.metoo.R;
import com.metoo.R.layout;
import com.metoo.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CreateEventActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_create_event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.screen_create_event, menu);
		return true;
	}

}
