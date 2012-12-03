package com.metoo.gmap.overlay;

import java.util.List;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.metoo.common.AndroServices;
import com.metoo.model.Event;
import com.metoo.model.EventList;

import android.graphics.drawable.Drawable;

/**
 * Слой встреч
 * @author Theurgist
 */
public class MeetingsMapLayer extends MapItemsLayer {
	AndroServices services;
	MapView mapView;
	
	protected EventList evlist;

	public MeetingsMapLayer(Drawable defaultMarker, AndroServices services, MapView mapView) {
		super(defaultMarker);
		this.services = services;
		this.mapView = mapView;
		evlist = new EventList();
	}
	
	public void MergeNewEvents(EventList incoming) {
		List<Event> added = evlist.Merge(incoming);
		for(Event ev: added) {
			addOverlay(new MeetingMapItem(ev));
		}
		mapView.postInvalidate();
	}
	
	 @Override
	 protected boolean onTap(int index) {
		 OverlayItem item = mOverlays.get(index);
		 services.ShowAlert(item.getTitle(), item.getSnippet());
		 return true;
	 }
}
