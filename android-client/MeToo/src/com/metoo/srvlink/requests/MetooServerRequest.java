package com.metoo.srvlink.requests;

import com.metoo.srvlink.base.ServerRequest;

/**
 * Тип запроса для проекта MeToo
 * @author Theurgist
 */
public class MetooServerRequest extends ServerRequest {
	/**
	 * Счётчик запросов
	 */
	protected static Integer req_counter = 0;
	
	/**
	 * При создании автоматически в список параметров добавляется номер запроса
	 */
	public MetooServerRequest() {
		AddParam("request_id", (++req_counter).toString());
	}
}
