package com.metoo.xmlparser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class PageParser {	
	private XPathFactory xpathFac;
	private XPath xpathObj;

	public PageParser() {
		xpathFac = XPathFactory.newInstance();
		xpathObj = xpathFac.newXPath();
	}
	
	/*
	 * Комплексные функции - конкретные задачи
	 */
	

	public <T extends INodeSerializer> List<T> ReadFromPage(TaggedDoc page, Class<T> dataClass, String xpath, boolean print) throws InstantiationException, IllegalAccessException {
		List<T> L = ReadFromPage(page, dataClass, xpath);
		if (!print)
			return L;

		if (L.size() == 0)
			System.out.println("Object list is empty.");
		else {
			System.out.println("Object list length: "+L.size());
			int i = 0;
			for (Object o:L)
				System.out.println(++i+o.toString().trim());
		}
		
		return L;
	}

	public <T extends INodeSerializer> List<T> ReadFromPage(TaggedDoc page, Class<T> dataClass, String xpath) throws InstantiationException, IllegalAccessException {
		Node rootNode = page.getNode();
		if (rootNode == null) {
			setLastError("ReadFromPage(): файл не распарсен!");
	    	return null;
		}

		if (!ClassChecker.CheckInterface(dataClass, INodeSerializer.class)) {
			setLastError("ReadFromPage(): тип данных не поддерживает интерфейс сериализации");
	    	return null;
		}
		
		NodeList nodes = XPath(xpath, rootNode);
		int nodesLength = nodes.getLength();
		if (nodesLength == 0) {
			setLastError("ReadFromPage(): по указанному XPath не найдено результатов.");
			return null;
		}

		ArrayList<T> ser_list = new ArrayList<T>();
		ser_list.ensureCapacity(nodesLength);
		
		Node N = nodes.item(0);
		for(int i = 0; i < nodesLength; i++) {
			N = nodes.item(i);
			T T = (T) dataClass.newInstance();
			T.serialize(N, this);
			ser_list.add(T);
		}
	
		return (List<T>) ser_list;
	}
	
	

	/**
	 * Применить xpath-выражение к узлу
	 */
	public NodeList XPath(String xpath_expr, Node node) {
	    NodeList nodes;
		try {
			nodes = (NodeList) xpathObj.evaluate(xpath_expr, node, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			setLastError("Exception in XPath(..)! "+e.toString());
			return null;
		}
	    System.out.println("XPath on "+xpath_expr+" extracted "+nodes.getLength()+" nodes");
		return nodes;
	}
	

	/**
	 * Получение/установка сообщения о текущей ошибке
	 */
	private String lastError = null; // Предыдущая ошибка
	private void setLastError(String Err) {
		lastError = Err;
	}
	public String getLastError() {
		String e = lastError;
		lastError = null;
		return e;
	}
}

/*
// Если не сможем подключиться к БД - то и некуда будет сливать распарсенные данные
SQLiteDB sqldb = new SQLiteDB(bAndroidVersion);
if (!sqldb.connect(pathToDB)) {
	setLastError("ReadGroupList(): не могу соединиться с БД: "+sqldb.getLastError());
	return null;
}
// Уничтожаем таблицу
sqldb.sqlDropTable(EU.TableNames.GROUPS);

String[] fields = EU.TableFields.GROUPS_FIELDS.clone();

// Создаём таблицу заново
for (int i=0; i<fields.length; i++)
	fields[i] += " "+EU.TableFields.GROUP_FIELDS_PROPS[i];

if (!sqldb.sqlCreateTable(EU.TableNames.GROUPS, fields)) {
	setLastError("ReadGroupList(): Не могу создать таблицу "+EU.TableNames.GROUPS);
	return null;
}

// Записываем в таблицу данные
List<SQLObject> gl_casted = (List)group_list;
sqldb.sqlInsertObjList(EU.TableNames.GROUPS, gl_casted, true);

sqldb.disconnect();
*/

/*	public ArrayList<HyperLink> ReadMenu() {
		if (rootNode == null)
	    	return null;

		Object[] nodes = XPath(XPathes.MENU);
		if (nodes.length == 0)
			return null;
		
		ArrayList<HyperLink> links = new ArrayList<HyperLink>();
		for (Object o:nodes) {
			TagNode i = (TagNode) o;
			String Name = i.getText().toString().trim();
			TagNode[] a_hrefs = i.getElementsHavingAttribute("href", true);
			String URI = null;
			if (a_hrefs.length != 0)
				 URI = a_hrefs[0].getAttributeByName("href");
			HyperLink t = new HyperLink(Name,URI);
			links.add(t);
		}
		
		return links;
	}
	*/