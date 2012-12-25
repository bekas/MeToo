/**
 * 
 */
package com.metoo.srvlink.requests;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author theurgist
 *
 */
public class CreateEventRequest extends MetooServerRequest {

	/**
	 * Задаются необходимые параметры
	 * @param eventName Название события
	 * @param eventDesrc Описаник события
	 * @param latitude Широта
	 * @param longitude Долгота
	 */
	public CreateEventRequest(
			String eventName,
			String eventDesrc,
			double latitude,
			double longitude) {
		AddParam("type", "event_create");
		try {
			AddParam("name", URLEncoder.encode(ReplaceSpacesWithUnderlines(eventName), "UTF-8"));
			//URLEncoder.encode(«группа», «UTF-8»);
			AddParam("description", URLEncoder.encode(ReplaceSpacesWithUnderlines(eventDesrc), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		AddParam("latitude", ReplaceCommasWithDots(String.format("%.8f", latitude)));
		AddParam("longitude", ReplaceCommasWithDots(String.format("%.8f", longitude)));
		//AddParam("latitude", "55,4");
		//AddParam("longitude", "33,5");
	}

}
