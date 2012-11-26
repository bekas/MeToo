/**
 * 
 */
package com.metoo.ui.base;

import com.metoo.common.AndroServices;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * Main activity of this project should be inherited from this
 * @author Theurgist
 */
public class BaseActivity extends Activity {
	protected BaseLayout currentLayout;
	protected LayoutManager switcher;
	public AndroServices services;


	// Переопределение базовых функций цикла жизни приложения
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        services = new AndroServices(this);
        switcher = new LayoutManager(this, null);
	}
	
	@Override
	public void onRestart	() {
		super.onRestart();
	}
	@Override
	public void onStart		() {
		super.onStart();
	}
	@Override
	public void onResume	() {
		super.onResume();
	}
	@Override
	public void onPause		() {
		super.onPause();
	}
	@Override
	public void onStop		() {
		super.onStop();
	}
	@Override
	public void onDestroy	() {
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	/**
	 * Sets current layout, which 
	 * @param layout
	 */
    public void SetLayoutAndActivate(BaseLayout layout) {
    	currentLayout = layout;
    	currentLayout.Activate();
    }
    public void RequestSwitch(BaseLayout layout, int minDelay, int maxDelay) {
    	switcher.RequestSwitch(layout, minDelay, maxDelay);
    }
    public void RequestSwitch(BaseLayout layout, int minDelay) {
    	RequestSwitch(layout, minDelay, -1);
    }
    public void RequestSwitch(BaseLayout layout) {
    	RequestSwitch(layout, 0);
    }

    /**
     * This override transfers event to current layout 
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
    	return currentLayout != null 
    			? currentLayout.onKeyDown(keyCode, event)
    			: onKeyDown(keyCode, event, true);
    }

    /**
     * Pushes onKeyDown event to the ground
     * @param keyCode
     * @param event
     * @param proceedToBase - indicates that this event is already processed by current layout
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event, boolean proceedToBase) {
    	return super.onKeyDown(keyCode, event);
    }

}
