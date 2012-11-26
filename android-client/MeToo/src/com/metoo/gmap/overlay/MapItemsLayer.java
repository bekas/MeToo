/**
 * 
 */
package com.metoo.gmap.overlay;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;

/**
 * Слой оверлеев, подключаемый к MapView
 * @author Theurgist
 */
public abstract class MapItemsLayer extends ItemizedOverlay<BaseMapItem> {
	
	protected ArrayList<BaseMapItem> mOverlays = new ArrayList<BaseMapItem>();

	public MapItemsLayer(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}
	
	public void addOverlay(BaseMapItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected BaseMapItem createItem(int i) {
		 return mOverlays.get(i);
	}

	@Override
	public int size() {
		 return mOverlays.size();
	}

}
