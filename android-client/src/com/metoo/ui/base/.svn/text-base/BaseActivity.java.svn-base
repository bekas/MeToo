/**
 * 
 */
package com.metoo.ui.base;

import com.google.android.maps.MapActivity;
import com.metoo.common.AndroServices;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * Main activity of this project should be inherited from this
 * @author Theurgist
 */
public class BaseActivity extends MapActivity {
	protected BaseLayout currentLayout;
	public AndroServices services;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        services = new AndroServices(getApplicationContext());
	}
	
	/**
	 * Sets current layout, which 
	 * @param layout
	 */
    public void SetCurrentLayout(BaseLayout layout) {
    	currentLayout = layout;
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

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
