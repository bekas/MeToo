/**
 * 
 */
package com.metoo.srvlink.answers;

import com.metoo.xmlparser.PageParser;

/**
 * Ответ от сервера на запрос регистрации пользователя
 * @author theurgist
 *
 */
public class UserRegistrationAnswer extends MetooServerAnswer {

	/**
	 * Конструктор, определяющий наполнение данных 
	 * @param source
	 * @param parser
	 */
	public UserRegistrationAnswer(String source, PageParser parser) {
		super(source, parser);
		if (error != null)
			return;
		
		if (!type.equals("registrate")) {
			error = "UserRegistrationAnswer: wrong answer type (type == " + error + ")";
		} else {
			// Больше нечего пока вычленять...
		}
	}

}
