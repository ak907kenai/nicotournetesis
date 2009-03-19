package encoder;

import java.util.Enumeration;

import tesis.parser.ConfigParser;

public class FilterCategoryEncoder extends CategoryEncoder {

	public FilterCategoryEncoder() {
		super();
	}

	/**
	 * Convert to nominal {value1, value2, value3}
	 * 
	 * @return String
	 */
	public String nominal() {
		System.out.println("FilterCategoryEncoder");
		Enumeration keys = categories.keys();
		StringBuffer keysBuffer = new StringBuffer();
		keysBuffer.append("{");
		while (keys.hasMoreElements()) {
			String catId = (String) keys.nextElement();
			Integer catCount = categories.get(catId);
			if (catCount >= ConfigParser.getMinCatCount()) {
				keysBuffer.append(catId);
				if (keys.hasMoreElements())
					keysBuffer.append(",");
			}
		}
		keysBuffer.append("}");
		return keysBuffer.toString();
	}
}
