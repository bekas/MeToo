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
import com.metoo.gmap.MapProvider;
import com.metoo.gmap.MeetingsOverlay;

/**
 * Layout with GoogleMap widget and which provides interaction with it
 * @author Theurgist
 */
public class MapLayout {

	private MapView mapView;
	private MapProvider mapProv;
	private NavActivity activity;
	private MeetingsOverlay overlayMeetings;

	public MapLayout(NavActivity parent) {
		activity = parent;
	}


	public void Activate() {
        activity.setContentView(R.layout.screen_map);
        mapView = (MapView)activity.findViewById(R.id.mapview);
		mapProv = new MapProvider(activity, mapView);

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = activity.getApplicationContext().getResources().getDrawable(R.drawable.token_orange);
		  
		MeetingsOverlay itemizedoverlay = new MeetingsOverlay(drawable, mapView, activity);
		GeoPoint point = MapProvider.ConvertCoordinates(55.9, 37.8);
		OverlayItem overlayitem = new OverlayItem(point, "Где-то на севере", "Рядом Мытищи");
		
		GeoPoint point2 = MapProvider.ConvertCoordinates(55.1, 37.3);
		OverlayItem overlayitem2 = new OverlayItem(point2, "Где-то на юге", "Неподалёку Чехов");
		
		itemizedoverlay.addOverlay(overlayitem);
		itemizedoverlay.addOverlay(overlayitem2);
		
		mapOverlays.add(itemizedoverlay);
	}
	
	public void Deactivate() {
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
