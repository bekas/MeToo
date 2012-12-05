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
	public final String type;
	/**
	 * Параметр ответа: номер запроса, на который отправлен этот ответ
	 */
	public final Integer request_id;
	/**
	 * Параметр ответа: ID сессии пользователя
	 */
	public final Integer session_id;
	/**
	 * Параметр ответа: результат выполнения запроса
	 */
	public final Integer result;
	
	/**
	 * Конструктор, определяющий наполнение данных 
	 * @param source
	 * @param parser
	 */
	public MetooServerAnswer(String source, PageParser parser) {
		super(source);

		if (!initParsedDocument()) {
			error = "MetooServerAnswer: Can't preparse XML answer";
			type = null;
			request_id = null;
			session_id = null;
			result = null;
		}
		else {
			NodeList nl;

			nl = parser.XPath("/metoo/type", doc.getNode());
			if ((nl != null) && (nl.getLength() > 0))
				type = nl.item(0).getTextContent();
			else {
				type = null;
				error = "MetooServerAnswer: Not valid MeToo server answer data";
			}
			
			nl = parser.XPath("/metoo/request_id", doc.getNode());
			if ((nl != null) && (nl.getLength() > 0))
				request_id = Integer.parseInt(nl.item(0).getTextContent());
			else request_id = null;
			
			nl = parser.XPath("/metoo/session_id", doc.getNode());
			if ((nl != null) && (nl.getLength() > 0))
				session_id = Integer.parseInt(nl.item(0).getTextContent());
			else session_id = null;
			
			nl = parser.XPath("/metoo/result", doc.getNode());
			if ((nl != null) && (nl.getLength() > 0))
				result = Integer.parseInt(nl.item(0).getTextContent());
			else result = null;
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