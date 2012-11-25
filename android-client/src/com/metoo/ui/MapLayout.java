/**
 * 
 */
package com.metoo.ui;

import java.util.List;

import android.view.KeyEvent;
import android.view.Menu;

import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.metoo.R;
import com.metoo.activities.NavActivity;
import com.metoo.gmap.MapProvider;
import com.metoo.gmap.overlay.MapItemsLayer;
import com.metoo.gmap.overlay.MeetingsMapLayer;
import com.metoo.ui.views.MapViewEx;
import com.metoo.ui.views.MapViewListener;

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

	public void Activate(MapViewListener hook) {
        activity.setContentView(R.layout.screen_map);
        mapView = (MapViewEx)activity.findViewById(R.id.mapview);
        mapView.setMapViewListener(hook);
		mapProv = new MapProvider(mapView);
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
