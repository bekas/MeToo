/**
 * 
 */
package com.metoo.srvlink.answers;

import org.w3c.dom.NodeList;

import com.metoo.xmlparser.PageParser;

/**
 * @author theurgist
 *
 */
public class CreateEventAnswer extends MetooServerAnswer {


	public CreateEventAnswer(String source, PageParser parser) {
		super(source, parser);

		if (error != null)
			return;

		if (!type.equals("create_event")) {
			error = "EventListAnswer: wrong answer type (type == " + error + ")";
		} else {

			NodeList nl;
			nl = parser.XPath("/metoo", doc.getNode());


		}
	}

}
