/**
 * 
 */
package com.metoo.srvlink.requests;

import com.metoo.srvlink.XmlAnswer;

/**
 * Запрос аутентификации на сервере
 * @author theurgist
 */
public class LoginRequest extends MetooServerRequest {

	/**
	 * Задаются необходимые параметры
	 * @param login Имя пользователя
	 * @param password Пароль
	 */
	public LoginRequest(String login, String password) {
	    AddParam("type", "auth");
	    AddParam("login", login);
	    AddParam("password", password);
	}

}
