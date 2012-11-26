package com.metoo.activities;

import com.metoo.R;
import com.metoo.common.AppSettings;
import com.metoo.ui.SettingsLayout;
import com.metoo.ui.base.BaseActivity;

import android.os.Bundle;
import android.view.Menu;

public class SettingsActivity extends BaseActivity {
	
	SettingsLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new SettingsLayout(this, null);
        SetLayoutAndActivate(layout);
    }
    
    @Override
	public void onBackPressed() {
    	layout.SaveSettings();
    	AppSettings.SaveOnDisk(services);
		super.onBackPressed();             
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.screen_settings, menu);
        return true;
    }
}
