/**
 * 
 */
package com.metoo.srvlink.base;

/**
 * Базовый класс для ответов, присылаемых сервером на Get-запросы
 * @author theurgist
 */
public abstract class ServerAnswer {
	
	protected final String source;
	protected String error;

	/**
	 * Объект целиком определяется строкой ответа
	 * Конструктор должен вычлененять все данных из ответа,
	 * во внутренние переменные-члены
	 * @param source Ответ сервера на некоторый запрос
	 */
	public ServerAnswer(String source) {
		this.source = source;
		error = null;
	}
	
	/**
	 * Получить исходную строку запроса
	 * @return Строка запроса, полученная при создании объекта
	 */
	public String GetRawAnswerText() {
		return source;
	}
	
	/**
	 * Возвращает описание ошибки, возникшей при десериализации ответа
	 * @return null, если десериализовано успешно
	 */
	public String GetError() {
		return error;
	}
}
