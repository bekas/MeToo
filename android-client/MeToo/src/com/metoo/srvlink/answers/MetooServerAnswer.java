/**
 * 
 */
package com.metoo.srvlink.answers;

import org.w3c.dom.NodeList;

import com.metoo.srvlink.base.ServerAnswer;
import com.metoo.xmlparser.PageParser;
import com.metoo.xmlparser.TaggedDoc;
import com.metoo.xmlparser.XmlDoc;

/**
 * Специфичный для приложения тип ответа от сервера
 * @author theurgist
 *
 */
public abstract class MetooServerAnswer extends ServerAnswer {

	/**
	 * Используется этим классом и его потомками для десериализации
	 */
	protected TaggedDoc doc;

	/**
	 * Параметр ответа: тип запроса
	 */
	public final String GetType() {return type;}
	protected String type = null;
	
	/**
	 * Параметр ответа: номер запроса, на который отправлен этот ответ
	 */
	public final Integer GetRequestId() {return request_id;}
	protected Integer request_id = null;
	/**
	 * Параметр ответа: ID сессии пользователя
	 */
	public final Integer GetSessionId() {return session_id;}
	protected Integer session_id = null;
	/**
	 * Параметр ответа: результат выполнения запроса
	 */
	public final Integer GetRequestResult() {return result;}
	protected Integer result = null;
	
	/**
	 * Конструктор, определяющий наполнение данных 
	 * @param source
	 * @param parser
	 */
	public MetooServerAnswer(String source, PageParser parser) {
		super(source);

		if (!initParsedDocument()) {
			error = "MetooServerAnswer: Can't preparse XML answer";
		}
		else {
			NodeList nl;

			nl = parser.XPath("/metoo/type", doc.getNode());
			if ((nl != null) && (nl.getLength() > 0))
				type = nl.item(0).getTextContent();
			else {
				error = "MetooServerAnswer: empty 'type' field";
				return;
			}
			
			nl = parser.XPath("/metoo/request_id", doc.getNode());
			if ((nl != null) && (nl.getLength() > 0))
				request_id = Integer.parseInt(nl.item(0).getTextContent());
			else {
				error = "MetooServerAnswer: empty 'request_id' field";
				return;
			}
			
			nl = parser.XPath("/metoo/session_id", doc.getNode());
			if ((nl != null) && (nl.getLength() > 0))
				session_id = Integer.parseInt(nl.item(0).getTextContent());
			else {
				error = "MetooServerAnswer: empty 'session_id' field";
				return;
			}
			
			nl = parser.XPath("/metoo/result", doc.getNode());
			if ((nl != null) && (nl.getLength() > 0))
				result = Integer.parseInt(nl.item(0).getTextContent());
			else {
				error = "MetooServerAnswer: empty 'result' field";
				return;
			}
		}
		
	}
	
	/**
	 * Подготовка сообщения к разбору
	 * @return
	 */
	private boolean initParsedDocument() {
		XmlDoc page = new XmlDoc();
		page.LoadFromString(source, true);
		doc = new TaggedDoc();
		return doc.ParseXML(page);
	}

}