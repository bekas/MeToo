package com.metoo.activities;

import com.google.android.maps.GeoPoint;
import com.metoo.R;
import com.metoo.ui.CreateEventLayout;
import com.metoo.ui.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class CreateEventActivity extends BaseActivity {
	
	CreateEventLayout layout;
	GeoPoint crd;
	

	/**
	 * Создание активити для создания события в определённой точке
	 * В Intent необходимо передать параметры 'lat' и 'lng'!!!
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		int latitude = intent.getIntExtra("lat", 0);
		int longitude = intent.getIntExtra("lng", 0);
        crd = new GeoPoint(latitude, longitude);

        layout = new CreateEventLayout(this, null, crd);
        SetLayoutAndActivate(layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.screen_create_event, menu);
		return true;
	}

}
