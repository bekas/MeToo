package com.metoo.model;

import java.util.List;

public class User {

	String Username;
	Integer BirthYear, BirthMonth, BirthDay;
	Integer Gender;
	List<Integer> Interested;
	
	List<Integer> IsGoingIds, IsGoingMaybeIds;
	List<Meeting> IsGoing, IsGoingMaybe;

}
