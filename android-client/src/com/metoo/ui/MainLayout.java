/**
 * 
 */
package com.metoo.ui;

import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.BaseLayout;

/**
 * Main application startup layout
 * @author Theurgist
 *
 */
public class MainLayout extends BaseLayout {

	public MainLayout(BaseActivity parent, BaseLayout previous) {
		super(parent, previous);
	}

	public void onSuccess(String Result) { }
	public void onError(String Reason) { }
	public void onProgress(String Message) { }

	/**
	 * @see com.metoo.ui.base.BaseLayout#Activate()
	 */
	@Override
	public void Activate() {
	}

	/**
	 * @see com.metoo.ui.base.BaseLayout#Deactivate()
	 */
	@Override
	public void Deactivate() {
	}

	/**
	 * @see com.metoo.ui.base.BaseLayout#preloadRoutine()
	 */
	@Override
	protected Boolean preloadRoutine() {
		return true;
	}

}
