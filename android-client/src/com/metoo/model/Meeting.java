package com.metoo.model;

import java.util.List;

public final class Meeting {

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
	
}
