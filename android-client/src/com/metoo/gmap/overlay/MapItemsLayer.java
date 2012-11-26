/**
 * 
 */
package com.metoo.gmap.overlay;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

/**
 * @author Theurgist
 *
 */
public abstract class MapItemsLayer extends ItemizedOverlay<BaseMapItem> {
	
	 private ArrayList<BaseMapItem> mOverlays = new ArrayList<BaseMapItem>();

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
