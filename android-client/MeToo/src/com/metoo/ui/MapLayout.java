/**
 * 
 */
package com.metoo.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.metoo.R;
import com.metoo.activities.CreateEventActivity;
import com.metoo.activities.NavActivity;
import com.metoo.activities.SettingsActivity;
import com.metoo.common.androidutils.AndroidAppLog;
import com.metoo.gmap.MapProvider;
import com.metoo.gmap.overlay.MapItemsLayer;
import com.metoo.gmap.overlay.MeetingsMapLayer;
import com.metoo.ui.views.IOnLongPressListener;
import com.metoo.ui.views.MapViewEx;
import com.metoo.ui.views.IMapViewPanListener;

/**
 * Layout with GoogleMap widget and which provides interaction with it
 * @author Theurgist
 */
public class MapLayout {

	public MapViewEx mapView;
	private MapProvider mapProv;
	private NavActivity activity;
	
	List<Overlay> mapOverlays;

	public MapLayout(NavActivity parent) {
		activity = parent;
	}

	public void Activate(IMapViewPanListener hook) {
        activity.setContentView(R.layout.screen_map);
        mapView = (MapViewEx)activity.findViewById(R.id.mapview);
        mapView.setMapViewPanListener(hook);
		mapProv = new MapProvider(mapView);
		mapOverlays = mapView.getOverlays();
		
		mapView.setLongPressListener(new IOnLongPressListener() {
			@Override
			public void onLongpress(GeoPoint longpressLocation) {
            	Intent myIntent = new Intent(activity, CreateEventActivity.class);
            	myIntent.putExtra("lat", longpressLocation.getLatitudeE6());
            	myIntent.putExtra("lng", longpressLocation.getLongitudeE6());
            	
           	 	activity.startActivity(myIntent);
            	AndroidAppLog.W("CreateEventActivity launched");
			}
		});
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
