/**
 * 
 */
package com.metoo.srvlink.requests;


/**
 * Запрос на получение событий в определённом радиусе от некоторой точки
 * @author Theurgist
 */
public class GetEventsRequest extends MetooServerRequest{

	/**
	 * Задаются необходимые параметры
	 * @param lat Широта
	 * @param lng Долгота
	 * @param radius Радиус (полусторона квадрата)
	 */
	public GetEventsRequest(double lat, double lng, double radius) {
		AddParam("type", "events");
		AddParam("latitude", String.format("%.8f", lat));
		AddParam("longitude", String.format("%.8f", lng));
		AddParam("radius", String.format("%.10f", radius));
	}

}
