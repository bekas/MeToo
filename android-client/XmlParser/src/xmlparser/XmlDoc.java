/**
 * 
 */
package xmlparser;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.InputSource;


/**
 * @author Theurgist
 *
 */
public class XmlDoc {
	protected String path;
	protected InputSource source;

	
	
	public class PageException extends Exception {
		private String msg;
		
		public PageException(String reason) {
			msg = reason;
		}
		@Override
		public String toString() {
			return("PageException: "+msg);
		}
	}

	public InputSource getSource() {
		return source;
	}
	
	public boolean LoadFromFile(String filePath, boolean unicode) throws PageException {

		if (filePath == null) {
			return false;
		}
		path = filePath;
		
	    InputStream fileinput;
	    ByteArrayInputStream meminput;

		try {
			fileinput = new FileInputStream(filePath);
			int filesize = fileinput.available();
			byte[] filecontent = new byte[filesize];
			fileinput.read(filecontent);
			meminput = new ByteArrayInputStream(filecontent);
		} catch (FileNotFoundException e) {
			throw new PageException(e.toString());
		} catch (IOException e) {
			throw new PageException(e.toString());
		}
		
		InputSource is = new InputSource(meminput);
		is.setEncoding(unicode?"utf-8":"CP1251");
	
		source = is;
		return true;
	}

}
