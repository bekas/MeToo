package com.metoo.model;

import java.util.List;


public class Event { //implements INodeSerializer {

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
	
	
//	public boolean serialize(Node node, PageParser parser) {
//
//		String src = node.getTextContent().trim();
//		System.out.println(src);
//		
//		return true;
//	}
	
}
