package com.metoo.srvlink.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.metoo.model.Event;

/**
 * Объект запроса серверу
 * @author Theurgist
 *
 */
public class ServerRequest {

	Map<String, String> args;
	private String _preamb;
	/**
	 * Флаг, является ли данный запрос get-запросом (если true), либо post-запросом (если false)
	 */
	private boolean isGetRequest;
	
	public ServerRequest() {
		args = new LinkedHashMap<String, String>();
		_preamb = "";
		isGetRequest = true;
	}

	/**
	 * Устанавливает преамбулу запроса - строка, предворяющая символ "?"
	 * @param preambula
	 */
	public void SetPreambula(String preambula) {
		_preamb = preambula;
	}
	/**
	 * Добавить параметр запроса (формат: "key=value")
	 * @param key Ключ параметра
	 * @param value Значение параметра, сериализованное в строку
	 * @return Предыдущее значение ключа, если было
	 */
	public String AddParam(String key, String value) {
		return args.put(key, value);
	}
	/**
	 * Очистить все параметры запроса
	 */
	public void ClearParams() {
		args.clear();
	}
	/**
	 * Построить на основе всех настроек запроса строку запроса, готовую к отправке на сервер
	 * @return Строка запроса
	 */
	public String CreateFormatedRequestString() {
		String result;
		
		if (isGetRequest) {
			// Конструируем get-запрос
			result = "/" +_preamb + "?";

			for (Map.Entry<String, String> pair : args.entrySet()) {
				result += pair.getKey() + "=" + pair.getValue() + "&";
			}
			// Отрезаем последний "&"
			if (args.size() > 0)
				result = result.substring(0, result.length() - 1);
		
		}
		else {
			//TODO Конструируем post-запрос
			result = "";
		}
		
		return result;
	}
	
}
