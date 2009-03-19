package encoder;

import java.util.Enumeration;
import java.util.Hashtable;

public class CategoryEncoder {

	protected Hashtable<String, Integer> categories;
	
	public CategoryEncoder() {
		categories = new Hashtable<String, Integer>();
	}

	public String encode(String catName) {
		String encoded = SimpleEncoder.encoder(catName.replaceFirst("top/", ""));
		if (!categories.containsKey(encoded))
			categories.put(encoded, new Integer(1));
		else {
			int catCount = categories.get(encoded).intValue() + 1;
			categories.put(encoded, new Integer(catCount));
		}
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
	
	
	/**
	 * Implement of toString method
	 */
	public String toString() {
		Enumeration keys = categories.keys();
		StringBuffer keysBuffer = new StringBuffer();
	    while( keys.hasMoreElements() ) {
	    	String catId = (String)keys.nextElement();
	    	Integer catCount = categories.get(catId);
        	keysBuffer.append(catId + ": " + catCount);
        	if (keys.hasMoreElements())
        		keysBuffer.append("\n");
        }
	    return keysBuffer.toString();
	}
	
	
	/**
	 * Returns the categories hashtable
	 * @return Hashtable<String, Integer> categories
	 */
	public Hashtable<String, Integer> getCategories() {
		return this.categories;
	}
}
