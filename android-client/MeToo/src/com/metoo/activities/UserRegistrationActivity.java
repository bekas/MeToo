package com.metoo.activities;

import com.metoo.R;
import com.metoo.ui.UserRegistrationLayout;
import com.metoo.ui.base.BaseActivity;

import android.os.Bundle;
import android.view.Menu;

public class UserRegistrationActivity extends BaseActivity {
	
	UserRegistrationLayout layout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		layout = new UserRegistrationLayout(this, null); 
		layout.Activate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_user_registration, menu);
		return true;
	}

}
