package tesis.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import tesis.parser.ConfigParser;
import tesis.parser.ParserConstants;

/**
 * Utilities for processing strings
 * @author nicotourne
 *
 */
public class StringUtilities {

	// TODO Use a Hashtable to store only strings?
	private static Hashtable<String, String> stopwords = null;
	
	
	/**
	 * If the text is null, "" or " ", convert into "?"
	 * 
	 * @param text
	 * @return
	 */
	public static String cleanText(String text) {
		if ((text == null) || ("".equals(text)) || (" ".equals(text)))
			return ParserConstants.NOINFO;
		return text;
	}
	
	
	/**
	 * Remove special chars
	 * 
	 * @param text
	 * @param charsToKeep
	 * @return
	 */
	public static String removeSpecialChars(String text) {
		text = text.toLowerCase();
		String charsToKeep = "/abcdefghijklmnopqrstuvwxyz ";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (charsToKeep.indexOf(ch) > -1) {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}
	
	
	/**
	 * Return the stem from a text
	 * 
	 * @param text
	 * @return stem
	 */
	public static String stem(String texts) {

		StringBuffer textsSB = new StringBuffer();

		// Get stem for each word
		String[] textsA = texts.split(" ");
		for (int i = 0; i < textsA.length; i++) {
			
			Stemmer s = new Stemmer();
			for (int k = 0; k < textsA[i].length(); k++)
				s.add(textsA[i].charAt(k));
			s.stem();
			
			textsSB.append(s.toString());
			textsSB.append(" ");
		}

		return textsSB.toString().trim();
	}
	
	
	/**
	 * Remove the stopwords from the text
	 * @param texts
	 * @return texts without stopwords
	 */
	public static String removeStopWords(String texts) {
	
		StringBuffer textsSB = new StringBuffer();

		// Get stem for each word
		String[] textsA = texts.split(" ");
		for (int i = 0; i < textsA.length; i++) {
			if (textsA[i].equals("home")) {
				if (!StringUtilities.isStopWord(textsA[i])) {
					System.out.println("home is not a stop-word");
				}
			}
			
			if (!StringUtilities.isStopWord(textsA[i])) {
				if (textsA[i].equals("home")) {
					System.out.println("adding home");
				}
				textsSB.append(textsA[i]);
				textsSB.append(" ");
			}
		}

		return textsSB.toString().trim();
		
	}
	
	
	/**
	 * Returns if the text is a stopwords
	 * @param text
	 * @return is a stopwords
	 */
	public static boolean isStopWord(String text) {
		
		// Load the stopwords list if it's not load yet
		if (StringUtilities.stopwords == null) {
			StringUtilities.stopwords = new Hashtable<String, String>();

			// Create reader for the stopwords list
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(ConfigParser
						.getStopWordsFile()));
				String str;
				while ((str = in.readLine()) != null) {
					StringUtilities.stopwords.put(str, str);
				}
				in.close();
		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		return StringUtilities.stopwords.contains(text);
		
	}

	/**
	 * Remove duplicated
	 * @param texts
	 * @return no-duplicated texts
	 */
	public static String removeDuplicated(String texts) {
		
		HashSet<String> elems = new HashSet<String>();
		String[] textsA = texts.split(" ");
		
		// Add the elements on a Set (no duplicated elements)
		for (int i = 0; i < textsA.length; i++) {
			elems.add(textsA[i]);
		}
		
		// Copy no-duplicated elements on a StringBuffer 
		StringBuffer sb = new StringBuffer();
		for(String s : elems) {
			sb.append(s);
			sb.append(" ");
		}
		
		return sb.toString().trim();
	}	
	

	/**
	 * Remove accents of a string
	 * @param text
	 * @return text without accents
	 */
	public static String removeAccents(String text) {
		
		text = text.toUpperCase();
		text = text.replaceAll( "[ÂÀÄÁÃ]", "A" );
		text = text.replaceAll( "[ÊÈËÉ]", "E" );
		text = text.replaceAll( "[ÎÌÏÍ]", "I" );
		text = text.replaceAll( "[ÔÒÖÓÕ]", "O" );
		text = text.replaceAll( "[ÛÙÜÚ]", "U" );
		text = text.replaceAll( "Ç", "C" );
		text = text.replaceAll( "Ñ", "N" );
		
		return text.toLowerCase(); 
	}
	
	
	
	public static void main(String[] args) {
	
		String original = "hello how are you playmobil along amount soccer";
		System.out.println(original);
		System.out.println(StringUtilities.removeStopWords(original));
	}


}
