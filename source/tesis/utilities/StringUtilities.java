package tesis.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.regex.Pattern;

import tesis.parser.ConfigParser;
import tesis.parser.ParserConstants;

/**
 * Utilities for processing strings
 * 
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
	 * 
	 * @param texts
	 * @return texts without stopwords
	 */
	public static String removeStopWords(String texts) {

		StringBuffer textsSB = new StringBuffer();

		// Get stem for each word
		String[] textsA = texts.split(" ");
		for (int i = 0; i < textsA.length; i++) {

			// Remove url. It's done here because of performance issue
			//if (textsA[i].contains("www.grandcanyonchamber.org/")) {
				//System.out.println("Contains url");
			//}

			String text_i = StringUtilities.removeURLs(textsA[i]);

			// Continue processing if it's not an url
			if ((text_i != null) && !("".equals(text_i))) {
				if (!StringUtilities.isStopWord(text_i)) {
					textsSB.append(text_i);
					textsSB.append(" ");
				}
			}

		}

		return textsSB.toString().trim();

	}

	/**
	 * Returns if the text is a stopwords
	 * 
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
	 * 
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
		for (String s : elems) {
			sb.append(s);
			sb.append(" ");
		}

		return sb.toString().trim();
	}

	/**
	 * Remove accents of a string
	 * 
	 * @param text
	 * @return text without accents
	 */
	public static String removeAccents(String text) {

		text = text.toUpperCase();
		text = text.replaceAll("[ÂÀÄÁÃ]", "A");
		text = text.replaceAll("[ÊÈËÉ]", "E");
		text = text.replaceAll("[ÎÌÏÍ]", "I");
		text = text.replaceAll("[ÔÒÖÓÕ]", "O");
		text = text.replaceAll("[ÛÙÜÚ]", "U");
		text = text.replaceAll("Ç", "C");
		text = text.replaceAll("Ñ", "N");
		text = text.replace(".", " ");
		
		// Si agrego el punto, reemplaza todo. Se ve que al . lo usa como comodin ?
		//text = text.replaceAll(".", " ");

		return text.toLowerCase();
	}

	/**
	 * Remove url and any html code from a text
	 * 
	 * @param text
	 * @return text-without-url
	 */
	public static String removeURLs(String text) {
		// String reg =
		// "^[(https?|ftp|file|http)://]*[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		String reg = "^[(https?|ftp|file|http)://]*[www.][-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern patt = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		return Pattern.matches(reg, text) ? "" : text;
	}

	public static void main(String[] args) {

		/*
		 * String original = "hello how are you playmobil along amount soccer";
		 * System.out.println(original);
		 * System.out.println(StringUtilities.removeStopWords(original));
		 */

		String text1a = "http://www.algo.com";
		System.out.println("1a)" + text1a);
		System.out.println("1a)" + StringUtilities.removeURLs(text1a));

		String text1b = "www.grandcanyonchamber.org";
		System.out.println("1b)" + text1b);
		System.out.println("1b)" + StringUtilities.removeURLs(text1b));

		String text2 = "http://www.algo.com/carpeta";
		System.out.println("2a)" + text2);
		System.out.println("2a)" + StringUtilities.removeURLs(text2));

		String text2b = "www.algo.com/carpeta";
		System.out.println("2b)" + text2b);
		System.out.println("2b)" + StringUtilities.removeURLs(text2b));

		String text3 = "http://www.algo.com/subfolder1.html";
		System.out.println("3a)" + text3);
		System.out.println("3a)" + StringUtilities.removeURLs(text3));

		String text3c = "www.algo.com/subfolder1.html";
		System.out.println("3b)" + text3c);
		System.out.println("3b)" + StringUtilities.removeURLs(text3c));

		String text4a = "http://algo.com";
		System.out.println("4a)" + text4a);
		System.out.println("4a)" + StringUtilities.removeURLs(text4a));

		String text4b = "algo.com";
		System.out.println("4b)" + text4b);
		System.out.println("4b)" + StringUtilities.removeURLs(text4b));

		String text0 = "amco";
		System.out.println("0)" + text0);
		System.out.println("0)" + StringUtilities.removeURLs(text0));

		String text5a = "http://www.grandcanyonchamber.org/";
		System.out.println("5a)" + text5a);
		System.out.println("5a)" + StringUtilities.removeURLs(text5a));
		
		System.out.println(StringUtilities.replaceHtmlCoding("Fundaci&amp;oacute;n"));
		System.out.println(StringUtilities.replaceHtmlCoding("Investigaci&amp;oacute;n"));
		System.out.println(StringUtilities.replaceHtmlCoding("C&amp;aacute;ncer"));
		
		
	}

	/**
	 * Replace the special html chars for simple ascii
	 * 
	 * @param info
	 * @return info-without-html
	 */
	public static String replaceHtmlCoding(String info) {

		info = info.toLowerCase();

		// char <space> and &
		info = info.replace("&nbsp;", " ");
		info = info.replace("&amp;", "&");

		// char a
		info = info.replace("&agrave;", "a");
		info = info.replace("&aacute;", "a");
		info = info.replace("&auml;", "a");

		// char e
		info = info.replace("&eacute;", "e");
		info = info.replace("&egrave;", "e");
		info = info.replace("&euml;", "e");

		// char i
		info = info.replace("&iacute;", "i");
		info = info.replace("&igrave;", "i");
		info = info.replace("&iuml;", "i");

		// char o
		info = info.replace("&oacute;", "o");
		info = info.replace("&ograve;", "o");
		info = info.replace("&ouml;", "o");

		// char u
		info = info.replace("&uacute;", "u");
		info = info.replace("&ugrave;", "u");
		info = info.replace("&uuml;", "u");

		// char c
		info = info.replace("&ccedil;", "c");

		return info;

	}

}
