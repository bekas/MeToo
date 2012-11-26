import java.util.List;

import messages.Event;
import xmlparser.INodeSerializer;
import xmlparser.PageParser;
import xmlparser.TaggedDoc;
import xmlparser.XmlDoc;
import xmlparser.XmlDoc.PageException;


/**
 * 
 */

/**
 * Для тестирования парсера
 * @author Theurgist
 */
public class TestingApp {

	/**
	 * 
	 */
	public TestingApp() {
	}

	public static void main(String[] args) {

		XmlDoc page = new XmlDoc();
		try {
			page.LoadFromFile("res/testevents.xml", true);
			TaggedDoc tagged = new TaggedDoc(page);
			PageParser pageParser = new PageParser();
			
			List<Event> events = pageParser.ReadFromPage(tagged, Event.class, "/metoo/events");
			
			
		} catch (PageException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	

	public static void Pr(String s) {
		System.out.print(s);
	}
	public static void PrLn(String s) {
		System.out.println(s);
	}
	public static void PrObjList(Object[] Objs) {
		if (Objs.length == 0)
			PrLn("Object list is empty.");
		else {
			PrLn("Object list length: "+Objs.length);
			for (Object i:Objs)
				PrLn("Class: "+i.getClass().toString()+" -> "+i.toString().trim());
		}
	}
	public static void PrStringList(List<String[]> strlist) {
		if (strlist == null)
			PrLn("PrStringList(): Got null'ed object");
		else if (strlist.size() == 0)
			PrLn("PrStringList: String list is empty.");
		else 
			for(String[] sarr:strlist) {
				for(String s:sarr)
					Pr(s+' ');
				PrLn(";");
		}
		
	}

}
