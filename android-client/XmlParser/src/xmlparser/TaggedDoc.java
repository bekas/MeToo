package xmlparser;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;

//import org.ccil.cowan.tagsoup.Parser;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;


public class TaggedDoc {

	/*
	 * Распарсенная html-страница
	 */
	private Node rootNode;
	public Node getNode() {
		return rootNode;
	}
	
	public TaggedDoc() {
		rootNode = null;
	}
	public TaggedDoc(XmlDoc htmlpage) {
		ParseHtml(htmlpage);
	}

	/**
	 * Преобразование HTML-страницы в дерево тегов
	 */
	public boolean ParseHtml(XmlDoc htmlpage) {
		InputSource source = htmlpage.getSource();
		if (source == null) {
			setLastError("TaggedPage.ParseHtml(Page): Page is not loaded");
			return false;
		}
		
	    try {
			//XMLReader reader = new Parser();
		    //reader.setFeature(Parser.namespacesFeature, false);
		    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	
		    DOMResult result = new DOMResult();
		    SAXSource saxSrc = new SAXSource(source);
			transformer.transform(saxSrc, result);
			rootNode = result.getNode();
			return true;
		} catch (TransformerException e) {
			setLastError("TaggedPage.ParseHtml(Page): "+e.toString());
		}
//		 catch (SAXNotRecognizedException e) {
//			setLastError("TaggedPage.ParseHtml(Page): "+e.toString());
//		} catch (SAXNotSupportedException e) {
//			setLastError("TaggedPage.ParseHtml(Page): "+e.toString());
//		}

		return false;
	}
	
	/**
	 * Получение/установка сообщения о текущей ошибке
	 */
	private String lastError = null;
	private void setLastError(String Err) {
		lastError = Err;
	}
	public String getLastError() {
		String e = lastError;
		lastError = null;
		return e;
	}
}
