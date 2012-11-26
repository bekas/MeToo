package com.metoo.gmap.overlay;

import com.google.android.maps.OverlayItem;
import com.metoo.common.AndroServices;

import android.graphics.drawable.Drawable;

/**
 * Слой встреч
 * @author Theurgist
 */
public class MeetingsMapLayer extends MapItemsLayer {
	AndroServices services;

	public MeetingsMapLayer(Drawable defaultMarker, AndroServices services) {
		super(defaultMarker);
		this.services = services;
	}
	
	 @Override
	 protected boolean onTap(int index) {
		 OverlayItem item = mOverlays.get(index);
		 services.ShowAlert(item.getTitle(), item.getSnippet());
		 return true;
	 }
}
