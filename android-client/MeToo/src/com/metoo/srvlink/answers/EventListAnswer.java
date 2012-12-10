package com.metoo.srvlink.answers;

import org.w3c.dom.NodeList;

import com.metoo.model.EventList;
import com.metoo.xmlparser.PageParser;

/**
 * Ответ на запрос получения событий
 * @author theurgist
 *
 */
public class EventListAnswer extends MetooServerAnswer {

	/**
	 * Список событий, полученный от сервера
	 */
	public final EventList GetEvents() {return events;}
	protected EventList events = null;
	
	public EventListAnswer(String source, PageParser parser) {
		super(source, parser);
		if (error != null)
			return;
		
		if (!type.equals("events")) {
			error = "EventListAnswer: wrong answer type (type == " + error + ")";
		} else {

			NodeList nl;
			nl = parser.XPath("/metoo", doc.getNode());
			if (nl != null) {
				events = new EventList();
				for(int i = 0; i < nl.getLength(); i++) {
					events.serialize(nl.item(i), parser);
				}
			}
		}
	}

}