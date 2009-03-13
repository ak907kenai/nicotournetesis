package encoder;

import java.util.Enumeration;
import java.util.Hashtable;

public class CategoryEncoder {

	private Hashtable<String, String> categories;
	
	public CategoryEncoder() {
		categories = new Hashtable<String, String>();
	}

	public String encode(String catName) {
		String encoded = SimpleEncoder.encoder(catName);
		categories.put(encoded, catName);
		return encoded;
	}

	
	/**
	 * Convert to nominal {value1, value2, value3}
	 * @return String
	 */
	public String nominal() {
		Enumeration keys = categories.keys();
		StringBuffer keysBuffer = new StringBuffer();
		keysBuffer.append("{");
	    while( keys.hasMoreElements() ) {
        	keysBuffer.append(keys.nextElement());
        	if (keys.hasMoreElements())
        		keysBuffer.append(",");
        }
	    keysBuffer.append("}");
	    return keysBuffer.toString();
	}
}
