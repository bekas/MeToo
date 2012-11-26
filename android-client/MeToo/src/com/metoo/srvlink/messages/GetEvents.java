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
		AddParam("latitude", "55.9");
		AddParam("longitude", "37.8");
		AddParam("radius", "250");
	}

}
