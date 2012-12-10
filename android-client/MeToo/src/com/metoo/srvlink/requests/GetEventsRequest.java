/**
 * 
 */
package com.metoo.srvlink.requests;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;


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
		AddParam("latitude", ReplaceCommasWithDots(String.format("%.8f", lat)));
		AddParam("longitude", ReplaceCommasWithDots(String.format("%.8f", lng)));
		AddParam("radius", ReplaceCommasWithDots(String.format("%.10f", radius)));
		//AddParam("latitude", "55.4");
		//AddParam("longitude", "33.5");
	}

}
