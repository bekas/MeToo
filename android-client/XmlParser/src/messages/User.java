package messages;

import java.util.List;

public class User {

	String Username;
	Integer BirthYear, BirthMonth, BirthDay;
	Integer Gender;
	List<Integer> Interested;
	
	List<Integer> IsGoingIds, IsGoingMaybeIds;
	List<Event> IsGoing, IsGoingMaybe;

}
