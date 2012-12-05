package com.metoo.srvlink.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Объект запроса серверу
 * @author Theurgist
 *
 */
public class ServerRequest {
	private List<StringPair> _args;
	private String _preamb;
	/**
	 * Флаг, является ли данный запрос get-запросом (если true), либо post-запросом (если false)
	 */
	private boolean isGetRequest;
	
	public ServerRequest() {
		_args = new ArrayList<StringPair>();
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
	 */
	public void AddParam(String key, String value) {
		_args.add(new StringPair(key, value));
	}
	/**
	 * Очистить все параметры запроса
	 */
	public void ClearParams() {
		_args.clear();
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
			for(StringPair t : _args) {
				result += t._key + "=" + t._value + "&";
			}
			if (_args.size() > 0)
				result = result.substring(0, result.length() - 1);
		
		}
		else {
			//TODO Конструируем post-запрос
			result = "";
		}
		
		return result;
	}
	
}

/**
 * Строковая пара "ключ-значение"
 * @author theurgist
 */
class StringPair {
	public String _key, _value;
	public StringPair(String key, String value) {
		_key = key;
		_value = value;
	}
}
