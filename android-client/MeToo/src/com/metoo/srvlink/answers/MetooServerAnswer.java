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
public class MetooServerAnswer extends ServerAnswer {

	/**
	 * Используется этим классом и его потомками для десериализации
	 */
	protected TaggedDoc doc;
	
	/**
	 * Параметр ответа: номер запроса, на который отправлен этот ответ
	 */
	public final Integer request_id;
	public final String type;
	
	/**
	 * Конструктор, определяющий наполнение данных 
	 * @param source
	 * @param parser
	 */
	public MetooServerAnswer(String source, PageParser parser) {
		super(source);

		if (!preparse()) {
			error = "MetooServerAnswer: Can't preparse XML answer!";
			request_id = null;
			type = null;
		}
		else {
			NodeList nl;
			nl = parser.XPath("/metoo/request_if", doc.getNode());
			if ((nl != null) && (nl.getLength() > 0))
				request_id = Integer.parseInt(nl.item(0).getTextContent());
			else request_id = null;

			nl = parser.XPath("/metoo/type", doc.getNode());
			if ((nl != null) && (nl.getLength() > 0))
				type = nl.item(0).getTextContent();
			else type = null;
		}
		
	}
	
	private boolean preparse() {
		XmlDoc page = new XmlDoc();
		page.LoadFromString(source, true);
		doc = new TaggedDoc();
		return doc.ParseXML(page);
	}

}
