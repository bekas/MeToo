package com.metoo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.test.ActivityUnitTestCase;

import com.metoo.activities.MainActivity;
import com.metoo.common.AndroServices;
import com.metoo.srvlink.XmlAnswer;

import junit.framework.TestCase;

// Should try ActivityInstrumentationTestCase2
public class MainTestClass extends ActivityUnitTestCase<MainActivity> {

	public MainTestClass(Class<MainActivity> activityClass) {
		super(activityClass);
	}
	
	public MainTestClass() {
        super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	public void testStartActivity() {
		MainActivity hActivity = startActivity(new Intent(Intent.ACTION_MAIN), null, null);
		AndroServices srv = new AndroServices(hActivity);
	}
	
	public void testXmlAnswerObject() {
		String testRightAnswer = 
		"<metoo>" +
		    "<type>auth</type>" +
		    "<result>8</result>" +
		    "<description>Des</description>" +
		    "<session_id>321</session_id>" +
		    "<request_id>234</request_id>" +
	    "</metoo>";

		String testWrongAnswer = 
		"<metoro>" +
		    "<type>auth</type>" +
		    "<result>qe?</result>" +
		    "<description>Des</description>" +
		    "<session_AJDI>321</session_id>" +
		    "<QWE>234</QWE>" +
		    "<wqde>" +
	    "</metoo>";
		
		String errorInfo;
		
		// Test with right xml
		XmlAnswer parser = new XmlAnswer();
		errorInfo = parser.ParseMessage(testRightAnswer);
		
		assertNull(errorInfo);
		assertEquals("234", parser.request_id);
		assertEquals("auth", parser.type);
		assertEquals((int)Integer.parseInt("321"), (int)parser.session_id);
		assertEquals((int)Integer.parseInt("8"), (int)parser.result);
		

		// Test with wrong xml
		errorInfo = parser.ParseMessage(testWrongAnswer);
		assertNotNull(errorInfo);
	}

}

