/**
 * 
 */
package com.metoo.srvlink.answers;

import org.w3c.dom.NodeList;

import com.metoo.model.EventList;
import com.metoo.xmlparser.PageParser;

/**
 * Ответ от сервера на запрос логина
 * @author theurgist
 *
 */
public class LoginAnswer extends MetooServerAnswer {

	/**
	 * Конструктор, определяющий наполнение данных 
	 * @param source
	 * @param parser
	 */
	public LoginAnswer(String source, PageParser parser) {
		super(source, parser);
		if (error != null)
			return;
		
		if (type != "auth") {
			error = "LoginAnswer: wrong answer type (type == " + error + ")";
		} else {
			// Больше нечего пока вычленять...
		}
	}

}