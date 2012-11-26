package com.metoo.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;

import xmlparser.IMessageSerializer;


public final class Event implements IMessageSerializer {

	public String Name;
	public User Owner;
	public double Latitude, Longitude;
	public String Information;
	public Integer MainCategory;
	public List<Integer> TagsIds;
	
	public List<User> Administrators;
	
	
	public Integer AreGoingAmount, AreGoingMaybeAmount;
	public List<Integer> UsersAreGoing, UsersAreGoingMaybe;
	public List<User> UsersAreGoingIds, UsersAreGoingMaybeIds;
	
	
	
	public boolean serialize(Node node) {

		String src = node.getTextContent().trim();
		System.out.println(src);
		
		return true;
	}
	
}
