/**
 * 
 */
package com.metoo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.metoo.xmlparser.INodeSerializer;
import com.metoo.xmlparser.PageParser;
import com.metoo.xmlparser.XmlDoc;

/**
 * @author Theurgist
 *
 */
public class EventList implements INodeSerializer {

	/**
	 * Инкапсулируемый список событий
	 */
	Map<Long, Event> events;
	/**
	 * 
	 */
	public EventList() {
		events = new LinkedHashMap<Long, Event>();
	}

	/**
	 * Добавляет событие в список, если события с таким же Id в нём ещё нет
	 * @return True, если элемент добавлен
	 */
	public boolean Add(Event event) {
		if (events.containsKey(event.Id))
			return false;
		
		events.put((long) event.Id, event);
		return true;
	}
	/**
	 * Добавляет событие в список, если события с таким же Id в нём ещё нет,
	 * либо заменяет существующее
	 * @return True, если элемент добавлен, False - если обновлён
	 */
	public boolean Modify(Event event) {
		Event prev = events.put((long) event.Id, event);
		return prev == null;
	}
	
	/**
	 * Конвертирует объект в обычный список
	 * @return Список с оригинальными объектами событий - их изменение отражается на оригинале
	 */
	public List<Event> ToList() {
		Collection<Event> coll = events.values();
		List<Event> output = new ArrayList<Event>();
		for (Event ev: coll) {
			output.add(ev);
		}
		return output;
	}
	
	/**
	 * Вливает в текущий список другой список (с обновлением)
	 * @param with откуда добавляем
	 * @return список добавленных элементов
	 */
	public List<Event> Merge(EventList with) {
		Collection<Event> coll = with.events.values();
		List<Event> added = new ArrayList<Event>();
		
		for (Event ev: coll) {
			if (Modify(ev))
				added.add(ev);
		}
		
		return added;
	}

	/**
	 * Получает на вход документ, содержащий тег "events""
	 * Сериализация аддитивная, не разрущающая: каждая новая сериализация
	 * будет добавлять новые данные к уже имеющимся
	 */
	public boolean serialize(Node node, PageParser parser) {
		XmlDoc page = new XmlDoc();

		String src = node.getTextContent().trim();
		System.out.println(src);
		
		List<Event> events = new ArrayList<Event>();
		
		NodeList nl = parser.XPath("/events", node);
		
		// Читаем список событий
		for(int i = 0; i < nl.getLength(); i++) {
			Event ev = new Event();
			ev.serialize(nl.item(i), parser);
			events.add(ev);
		}
		
		// Слияние с существующим
		for(Event ev: events) {
			Modify(ev);
		}

		return true;
	}

}
