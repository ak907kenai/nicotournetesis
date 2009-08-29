package encoder;

import java.util.Hashtable;

public class DataEncoder {

	// text + textCode
	private Hashtable<String, String> items;

	private int textCode;

	public DataEncoder() {
		items = new Hashtable<String, String>();
		textCode = 1;
	}

	public String encode(String text) {

		String textCodeFound;

		// If the query have not been added
		if (!items.containsKey(text)) {
			items.put(text, Integer.toString(textCode));
			textCodeFound = Integer.toString(textCode);
			textCode++;
		}
		// If the query have been added before, I must recover
		else {
			textCodeFound = (String) items.get(text);
		}

		return textCodeFound;

	}

}
