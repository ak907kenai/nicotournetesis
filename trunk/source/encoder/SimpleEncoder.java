package encoder;

public class SimpleEncoder {

	public static String encoder(String text) {
		text = text.toLowerCase();
		int length = text.length();
		int indexOfA = indexOf("a", text); 
		int indexOfE = indexOf("e", text);
		int indexOfI = indexOf("i", text);
		int indexOfO = indexOf("o", text);
		int indexOfU = indexOf("u", text);
			
		StringBuffer encoded = new StringBuffer();
		encoded.append(length);
		encoded.append(indexOfA);
		encoded.append(indexOfE);
		encoded.append(indexOfI);
		encoded.append(indexOfO);
		encoded.append(indexOfU);
	
		return encoded.toString();
	}
	
	
	private static int indexOf(String letter, String text) {
		int index = text.indexOf(letter);
		if (index >= 0)
			return index;
		else
			return 0;
	}
}
