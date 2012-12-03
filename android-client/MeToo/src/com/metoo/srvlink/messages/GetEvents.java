/**
 * 
 */
package com.metoo.srvlink.messages;

import com.metoo.srvlink.MetooGetRequest;

/**
 * @author Theurgist
 *
 */
public class GetEvents extends MetooGetRequest{

	/**
	 * 
	 */
	public GetEvents(double lat, double lng, double radius) {
		AddParam("type", "events");
		AddParam("latitude", String.format("%.8f", lat));
		AddParam("longitude", String.format("%.8f", lng));
		AddParam("radius", String.format("%.10f", radius));
	}

}
