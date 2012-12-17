/**
 * 
 */
package com.metoo.srvlink.answers;

import org.w3c.dom.NodeList;

import com.metoo.model.Event;
import com.metoo.model.EventList;
import com.metoo.xmlparser.PageParser;

/**
 * @author theurgist
 *
 */
public class EventAnswer extends MetooServerAnswer {

	/**
	 * Полученная с сервера информация о событии
	 * @return Объект Event
	 */
	public final Event GetEvent() {return event;}
	protected Event event = null;

	/**
	 * @param source
	 * @param parser
	 */
	public EventAnswer(String source, PageParser parser) {
		super(source, parser);
		if (error != null)
			return;
		
		if (!type.equals("event_get")) {
			error = "EventAnswer: wrong answer type (type == " + error + ")";
		} else if (result != 100) {
			error = "EventAnswer: some error occured ( code: " + result + ")";
		} else {
			NodeList nl;
			nl = parser.XPath("/metoo/event", doc.getNode());
			event = new Event();
			event.serialize(nl.item(0), parser);
		}
	}

}
