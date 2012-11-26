package messages;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xmlparser.INodeSerializer;
import xmlparser.PageParser;


public final class Event implements INodeSerializer {

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
	
	

	@Override
	public boolean serialize(Node node, PageParser parser) {

		String src = node.getTextContent().trim();
		System.out.println(src);
		
		NodeList lst = parser.XPath("@id", node);
		System.out.println(lst.toString() + " LENGTH: " + lst.getLength());
		
		return true;
	}
	
}
