/**
 * 
 */
package com.metoo.srvlink.requests;


/**
 * Запрос регистрации нового пользователя
 * @author theurgist
 */
public class UserRegistrationRequest extends MetooServerRequest {

	/**
	 * Запрос регистрации нового пользователя
	 * @param login Имя пользователя
	 * @param password Пароль
	 */
	public UserRegistrationRequest(String login, String password) {
	    AddParam("type", "registrate");
	    AddParam("login", login);
	    AddParam("password", password);
	}

}
