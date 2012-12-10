package com.metoo.model;

import java.util.List;

import org.w3c.dom.Node;

import com.metoo.xmlparser.INodeSerializer;
import com.metoo.xmlparser.PageParser;



public final class Event implements INodeSerializer {

	public int Id;
	public String Name;
	public User Owner;
	public double Latitude, Longitude;
	public String Date;
	public String Description;
	public Integer MainCategory;
	public List<Integer> TagsIds;
	
	public List<User> Administrators;
	
	
	public Integer AreGoingAmount, AreGoingMaybeAmount;
	public List<Integer> UsersAreGoing, UsersAreGoingMaybe;
	public List<User> UsersAreGoingIds, UsersAreGoingMaybeIds;
	
	
	public boolean serialize(Node node, PageParser parser) {

		String src = node.getTextContent().trim();
		System.out.println(src);
		

		Id = Integer.parseInt(parser.XPath("id", node).item(0).getTextContent());
		//Date = parser.XPath("date", node).item(0).getTextContent();
		Latitude = Double.parseDouble(parser.XPath("latitude", node).item(0).getTextContent());
		Longitude = Double.parseDouble(parser.XPath("longitude", node).item(0).getTextContent());
		Description = parser.XPath("description", node).item(0).getTextContent();
		Name = parser.XPath("name", node).item(0).getTextContent();
		
		return true;
	}
	
}
