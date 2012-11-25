/**
 * 
 */
package com.metoo.ui;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.Menu;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.metoo.R;
import com.metoo.activities.NavActivity;
import com.metoo.common.AppSettings;
import com.metoo.gmap.MapProvider;
import com.metoo.gmap.overlay.MapItemsLayer;
import com.metoo.gmap.overlay.MeetingMapItem;
import com.metoo.gmap.overlay.MeetingsMapLayer;

/**
 * Layout with GoogleMap widget and which provides interaction with it
 * @author Theurgist
 */
public class MapLayout {

	private MapView mapView;
	private MapProvider mapProv;
	private NavActivity activity;
	private MeetingsMapLayer overlayMeetings;
	
	List<Overlay> mapOverlays;

	public MapLayout(NavActivity parent) {
		activity = parent;
	}

	public void Activate() {
        activity.setContentView(R.layout.screen_map);
        mapView = (MapView)activity.findViewById(R.id.mapview);
		mapProv = new MapProvider(activity, mapView);
		mapOverlays = mapView.getOverlays();
	}
	
	public void Deactivate() {
	}
	
	public void AddLayer(MapItemsLayer layer) {
		mapOverlays.add(layer);
		mapView.postInvalidate();
	}

    public boolean onCreateOptionsMenu(Menu menu) {
        activity.getMenuInflater().inflate(R.menu.activity_map, menu);
        return true;
    }

    public void onKeyDown(int keyCode, KeyEvent event) {
        MapController mc = mapView != null ? mapView.getController() : null; 
        switch (keyCode) 
        {
            case KeyEvent.KEYCODE_3:
            	if (mc != null)
            		mc.zoomIn();
                break;
            case KeyEvent.KEYCODE_1:
            	if (mc != null)
            		mc.zoomOut();
                break;
        }
    }  
}
