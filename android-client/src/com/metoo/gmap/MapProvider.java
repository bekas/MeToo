/**
 * 
 */
package com.metoo.gmap;

import android.app.Activity;
import android.content.Context;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.metoo.R;
import com.metoo.gmap.location.Coordinate;
import com.metoo.gmap.location.GeoProvider;

/**
 * @author Theurgist
 *
 */
public class MapProvider {

	private Activity appActivity;
	private Context ctx;
	
	public MapProvider(Activity activity, Context context)
	{
		appActivity = activity;
		ctx = context;
		
        MapView mapView = (MapView) appActivity.findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        GeoProvider geoProv = new GeoProvider(ctx);
        Coordinate coordinates = geoProv.GetCurrentLocation();

        MapController mapController = mapView.getController();
        GeoPoint point = new GeoPoint((int)(coordinates.Latitude * 1E6), 
        							  (int)(coordinates.Longitude * 1E6));
        mapController.setCenter(point);
	}
}
