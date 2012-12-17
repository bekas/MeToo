package com.metoo.activities;

import com.metoo.R;
import com.metoo.activities.NavActivity.MapDataReceiver;
import com.metoo.common.MetooServices;
import com.metoo.common.androidutils.IAsyncTaskNotifyer;
import com.metoo.model.Event;
import com.metoo.srvlink.answers.EventListAnswer;
import com.metoo.srvlink.requests.MetooServerRequest;
import com.metoo.ui.ShowEventLayout;
import com.metoo.ui.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class ShowEventActivity extends BaseActivity {
	
	ShowEventLayout layout;
	

	/**
	 * Создание активити для показа события
	 * В Intent необходимо передать параметр 'eventId'!!!
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		int eventId = intent.getIntExtra("eventId", 0);
		
        layout = new ShowEventLayout(eventId, this, null);
        SetLayoutAndActivate(layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.screen_show_event, menu);
		return true;
	}

	
}
