/**
 * 
 */
package com.metoo.ui;

import com.metoo.R;
import com.metoo.common.AndroServices;
import com.metoo.gmap.MapProvider;
import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.BaseLayout;

/**
 * Layout with GoogleMap widget and which provides interaction with it
 * @author Theurgist
 */
public class MapLayout extends BaseLayout {

	MapProvider mapProv;

	public MapLayout(BaseActivity parent, BaseLayout previous) {
		super(parent, previous);
		mapProv = new MapProvider(activity, activity.services.getContext());
	}

	/*
	 * IAsyncTaskNotifyer contracts
	 */
	public void onSuccess(String Result) { }
	public void onError(String Reason) { }
	public void onProgress(String Message) { }

	/*
	 * com.metoo.ui.base.BaseLayout contracts
	 */
	@Override
	public void Activate() {
        activity.setContentView(R.layout.screen_map);

	}
	public void Deactivate() {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.metoo.ui.base.BaseLayout#preloadRoutine()
	 */
	@Override
	protected Boolean preloadRoutine() {

		return true;
	}

}
