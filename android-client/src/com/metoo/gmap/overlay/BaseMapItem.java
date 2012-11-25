/**
 * 
 */
package com.metoo.gmap.overlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

/**
 * Элемент-оверлей
 * @author Theurgist
 */
public class BaseMapItem extends OverlayItem {

	public BaseMapItem(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
	}
}
