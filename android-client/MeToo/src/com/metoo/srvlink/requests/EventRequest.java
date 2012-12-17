/**
 * 
 */
package com.metoo.srvlink.requests;

/**
 * Запрос информации о событии
 * @author theurgist
 *
 */
public class EventRequest extends MetooServerRequest {

	/**
	 * Задаются необходимые параметры
	 * @param eventId Id события
	 */
	public EventRequest(Integer eventId) {
	    AddParam("type", "event_get");
	    AddParam("event_id", eventId.toString());
	}

}
