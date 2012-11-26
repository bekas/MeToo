/**
 * 
 */
package com.metoo.gmap.location;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * @author Theurgist
 *
 */


public class GeoProvider {
    private List<String> providers;
    private LocationManager mgr;
	
	public GeoProvider(Context context)
	{
		mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		providers = mgr.getAllProviders();
	}

	public Coordinate GetCurrentLocation() 
	{
	    Location currLoc = null;
	    
	    for (int i = providers.size() - 1; i >= 0; i--)
	    {
	    	currLoc = mgr.getLastKnownLocation(providers.get(i));
	    	if (currLoc != null) break;
	    }
	
		if (currLoc != null)
			return new Coordinate(currLoc.getLatitude(), currLoc.getLongitude());
		else
			return new Coordinate();
	}    
}
