package com.metoo.activities;

import com.google.android.maps.MapActivity;
import com.metoo.common.AppLog;
import com.metoo.ui.MapLayout;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

public class NavActivity extends MapActivity 
{
	private MapLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.I("NavActivity started");
        
        layout = new MapLayout(this);
        layout.Activate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return layout.onCreateOptionsMenu(menu);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	layout.onKeyDown(keyCode, event);
    	return super.onKeyDown(keyCode, event);
    }  
}
