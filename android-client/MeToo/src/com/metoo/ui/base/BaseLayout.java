package com.metoo.ui.base;

import android.view.KeyEvent;

import com.metoo.common.androidutils.IAsyncTaskNotifyer;

public abstract class BaseLayout implements IAsyncTaskNotifyer<String, String, String>
{	
	protected BaseActivity activity = null;
	protected BaseLayout _previous = null;
	
	protected boolean _isPreloaded = false;
	public boolean IsPreloaded() {return _isPreloaded;}
	
	// Is called to launch rendering sequence
	public abstract void Activate();
	// Is called to stop any rendering routines
	public abstract void Deactivate();
	// Contains all preloading logic
	protected abstract Boolean preloadRoutine();
	
	public BaseLayout(BaseActivity parent, BaseLayout previous) {
		activity = parent;
		_previous = previous;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	        if (_previous != null) {
	        	activity.switcher.SwitchImmediately(_previous);
		        return true;
	        }
	    }
	    return activity.onKeyDown(keyCode, event, true);
	}
	
	public void NextLoadingLayoutOnProgress(String Message) { }
	public void NextLoadingLayoutOnError(String Reason) { }
}
