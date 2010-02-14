package encoder;

import java.util.Enumeration;
import java.util.Hashtable;

import tesis.parser.ConfigParser;

public class CategoryEncoder {

	protected Hashtable<String, Integer> categories;

	public CategoryEncoder() {
		categories = new Hashtable<String, Integer>();
	}

	public String encode(String catName) {

		// Remove the "top/" string from the category because all the categories
		// start with this
		catName = catName.replaceFirst("top/", "");

		// Get the first maxCatLevels levels from the category
		catName = this.getCatNameByLevel(catName);

		// Create a code for the category
		String encoded = SimpleEncoder.encoder(catName);
		
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
	 * 
	 * @return String
	 */
	public String nominal() {
		Enumeration keys = categories.keys();
		StringBuffer keysBuffer = new StringBuffer();
		keysBuffer.append("{");
		while (keys.hasMoreElements()) {
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
		while (keys.hasMoreElements()) {
			String catId = (String) keys.nextElement();
			Integer catCount = categories.get(catId);
			keysBuffer.append(catId + ": " + catCount);
			if (keys.hasMoreElements())
				keysBuffer.append("\n");
		}
		return keysBuffer.toString();
	}

	/**
	 * Returns the categories hashtable
	 * 
	 * @return Hashtable<String, Integer> categories
	 */
	public Hashtable<String, Integer> getCategories() {
		return this.categories;
	}
	
	
	/**
	 * Get the first N levels from the category
	 * @param catName
	 * @return catName
	 */
	public String getCatNameByLevel(String catName) {

		String[] cats = catName.split("/");
		if (cats.length > ConfigParser.getMaxCatLevels()) {
			StringBuffer catsSB = new StringBuffer();
			for(int i = 0; i < ConfigParser.getMaxCatLevels(); i++) {
				catsSB.append(cats[i]);
				catsSB.append("/");
			}
			catName = catsSB.toString().substring(0, catsSB.length()-1);
		}
		return catName;
		
	}

	public static void main(String[] args) {
		int maxCatLevels = 3;
		
		String catName = "shopping/tools/vehicle repair/ford/escord";
		String[] cats = catName.split("/");
		if (cats.length > maxCatLevels) {
			StringBuffer catsSB = new StringBuffer();
			for(int i = 0; i < maxCatLevels; i++) {
				catsSB.append(cats[i]);
				catsSB.append("/");
			}
			catName = catsSB.toString().substring(0, catsSB.length()-1);
		}
		System.out.println(catName);
	}
}
