/**
 * 
 */
package com.metoo.gmap;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.metoo.common.AppSettings;
import com.metoo.gmap.location.Coordinate;

/**
 * Функции и настройки отображения карты
 * @author Theurgist
 */
public class MapProvider {
	
	public MapProvider(MapView mapView) {
		
		mapView.setBuiltInZoomControls(true);
        
        //GeoProvider geoProv = new GeoProvider(appActivity.getApplicationContext());
		//geoProv.GetCurrentLocation();
		
        MapController mapController = mapView.getController();
        mapController.setCenter(ConvertCoordinates(AppSettings.GetLastLatitude(), AppSettings.GetLastLongitude()));
        mapController.setZoom(AppSettings.GetLastZoomLevel());
	}

	public static GeoPoint ConvertCoordinates(double latitude, double longitude) {
		return new GeoPoint((int)(latitude * 1E6), 
				  			(int)(longitude * 1E6));
	}
	public static GeoPoint ConvertCoordinates(Coordinate crd) {
		return new GeoPoint((int)(crd.Latitude * 1E6), 
				  			(int)(crd.Longitude * 1E6));
	}
}
