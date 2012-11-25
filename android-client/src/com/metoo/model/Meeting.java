package com.metoo.model;

import java.util.List;

public final class Meeting {

	String Name;
	User Owner;
	double Latitude, Longitude;
	String Information;
	Integer MainCategory;
	List<Integer> TagsIds;
	
	List<User> Administrators;
	
	
	Integer AreGoingAmount, AreGoingMaybeAmount;
	List<Integer> UsersAreGoing, UsersAreGoingMaybe;
	List<User> UsersAreGoingIds, UsersAreGoingMaybeIds; 
	
}
