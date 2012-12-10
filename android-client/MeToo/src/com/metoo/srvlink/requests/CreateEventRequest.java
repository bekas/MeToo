/**
 * 
 */
package com.metoo.srvlink.requests;

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
		AddParam("name", eventName);
		AddParam("description", eventDesrc);
		AddParam("latitude", ReplaceCommasWithDots(String.format("%.8f", latitude)));
		AddParam("longitude", ReplaceCommasWithDots(String.format("%.8f", longitude)));
		//AddParam("latitude", "55,4");
		//AddParam("longitude", "33,5");
	}

}
