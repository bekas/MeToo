package com.metoo.ui;

import com.metoo.R;
import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.BaseLayout;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public final class SplashLayout extends BaseLayout
{
	private BaseLayout homeScreen;
	private int _minDelay;
	private int _maxDelay;

	ProgressBar prbar;
	TextView lblWarn;

	public SplashLayout(BaseActivity parent, BaseLayout homeScreenLayout, int minDelay, int maxDelay) {
		super(parent, null);
		 homeScreen = homeScreenLayout;
		 _minDelay = minDelay;
		 _maxDelay = maxDelay;
	}


	@Override
	protected Boolean preloadRoutine() {
		return true;
	}
	
	
	@Override
	public void Activate() {
	    activity.setContentView(R.layout.screen_splash);

	    // Make UI aliases
		prbar = (ProgressBar)activity.findViewById(R.id.prbarSplash);
		lblWarn = (TextView)activity.findViewById(R.id.lblLoaderWarn);
		
	    // Launch next layout loading in separate process
	    activity.RequestSwitch(homeScreen, _minDelay, _maxDelay);
	}

	@Override
	public void Deactivate() {
	}


	public void onSuccess(String Result) { }
	public void onError(String Reason) { }
	public void onProgress(String Message) {
		prbar.setVisibility(View.VISIBLE);
		lblWarn.setText(Message);
	}

	@Override
	public void NextLoadingLayoutOnError(String Reason) {
		onError(Reason);
	}
	@Override
	public void NextLoadingLayoutOnProgress(String Message) {
		onProgress(Message);
	}
}
