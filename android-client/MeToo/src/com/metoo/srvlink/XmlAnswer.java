/**
 * 
 */
package com.metoo.srvlink;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.metoo.model.Event;

/**
 * @author Theurgist
 *
 */
public class XmlAnswer {
	Document doc;
	Node message;
	
	public String type = null;
	public String request_id = null;
	public Integer session_id = 0;
	public Integer result = 0;
	
	/**
	 * Parses XML-formated message
	 * @param xml
	 * @return Error string, if any
	 */
	public String ParseMessage(String xml) {
		String result = ParseChunk(xml);
		readBasicMessageTags();
		
		return result;
	}

	/**
	 * Parses XML-formated string into some DOM model
	 * @param xml
	 * @return Error string, if any
	 */
	public String ParseChunk(String xml) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			
	        is.setCharacterStream(new StringReader(xml));
	        doc = db.parse(is); 
	
		} catch (ParserConfigurationException e) {
			return "XML parse error: " + e.getMessage();
		} catch (SAXException e) {
			return "Wrong XML file structure: " + e.getMessage();
		} catch (IOException e) {
			return "I/O exeption: " + e.getMessage();
		}
		
	
		// Everything went Ok
		return null;
	}
	
	public NodeList GetElementsByTagName(String tag) {
		return doc.getElementsByTagName(tag);
	}
	
	/**
	 * Reads last element with given tag, if any
	 * @param outResult output string variable
	 * @param tag tag to search
	 * @return amount of found elements
	 */
	public int GetLastElementByTag(String outResult, String tag) {
		NodeList nodes = doc.getElementsByTagName(tag);
		int count = nodes.getLength();
		if (count < 1)
			return count;
		
		outResult = nodes.item(count-1).getTextContent();
		return count;
	}
	
	
	protected void readBasicMessageTags() {
		try {
			type = doc.getElementsByTagName("type").item(0).getTextContent();
		} catch (Exception ex) { }
		try {
			session_id = Integer.parseInt(doc.getElementsByTagName("session_id").item(0).getTextContent());
		} catch (Exception ex) { }
		try {
			request_id = doc.getElementsByTagName("request_id").item(0).getTextContent();
		} catch (Exception ex) { }
		try {
			result = Integer.parseInt(doc.getElementsByTagName("result").item(0).getTextContent());
		} catch (Exception ex) { }
	}
	
	public List<Event> readEvents() {
		readBasicMessageTags();
		if (type != "events")
			return null;
		
		List<Event> res = new ArrayList<Event>();
		try {
			// Получаем количество ивентов
			NodeList events = doc.getElementsByTagName("event");
			
			//foreach
		} catch (Exception ex) { }
		
		return res;
	}
}
