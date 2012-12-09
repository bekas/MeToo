package com.metoo.gmap.overlay;

import java.util.List;

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
	
	protected EventList evlist;

	public MeetingsMapLayer(Drawable defaultMarker, AndroServices services) {
		super(defaultMarker);
		this.services = services;
		evlist = new EventList();
	}
	
	public void MergeNewEvents(EventList incoming) {
		List<Event> added = evlist.Merge(incoming);
		for(Event ev: added) {
			addOverlay(new MeetingMapItem(ev));
		}
	}
	
	 @Override
	 protected boolean onTap(int index) {
		 OverlayItem item = mOverlays.get(index);
		 services.ShowAlert(item.getTitle(), item.getSnippet());
		 return true;
	 }
}
