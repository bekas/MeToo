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

	public final String login;
	public final String passwd;
	/**
	 * Конструктор, определяющий наполнение данных 
	 * @param source
	 * @param parser
	 */
	public LoginAnswer(String source, PageParser parser) {
		super(source, parser);
		
//		if (type != "events") {
//			if (error == null)
//				error = "EventListAnswer: wrong answer type (type == " + error + ")";
//			events = null;
//		} else {
//
//			NodeList nl;
//			nl = parser.XPath("/metoo", doc.getNode());
//			if (nl != null) {
//				events = new EventList();
//				for(int i = 0; i < nl.getLength(); i++) {
//					events.serialize(nl.item(i), parser);
//				}
//			}
//			else events = null;
//		}
	}

}