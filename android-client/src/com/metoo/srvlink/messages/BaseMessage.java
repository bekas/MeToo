package com.metoo.srvlink.messages;

import com.metoo.srvlink.XmlAnswer;

public abstract class BaseMessage {
	abstract boolean Parse(XmlAnswer answer);
}

class TaggedVar {
	String tag;
	
	Object target;
	
	TaggedVar() {
		//TaggedVar.class.;
	}
}