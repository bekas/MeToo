package com.metoo.srvlink;

/**
 * Тип запроса для проекта MeToo
 * @author Theurgist
 *
 */
public class MetooGetRequest extends GetRequest {
	public MetooGetRequest() {
		AddParam("request_id", (++req_counter).toString());
	}	
}
