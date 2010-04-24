package tesis.utilities;

import pt.tumba.spell.SpellChecker;
import tesis.parser.ConfigParser;


/**
 * This class apply spell check to a word and returns the correct word
 * @author nicotourne
 *
 */
public class SpellCheck {

	private static SpellChecker instance = null;
	
	private SpellCheck() { }
	
	private static SpellChecker getInstance() {
		if (instance == null) {
			instance = new SpellChecker();
			try {
				instance.initialize(ConfigParser.getDataDirSpellCheck(), ConfigParser.getMisspellsSpellCheck(), ConfigParser.getJargonSpellCheck());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	/**
	 * Apply spell check to the param "word" and return the "correctWord"
	 * "word" can be "correctWord" if "word" is correct
	 * @param word
	 * @return correctWord
	 */
	public static String spellCheck(String word) {
		
		boolean useFrequencyMethod = true;

		SpellChecker _spellCheck = SpellCheck.getInstance(); 

		String correctWord = _spellCheck.findMostSimilar(word,useFrequencyMethod);
		
		// If return result is "3", it means that the word doesn't have errors
		if ((correctWord == null) || (correctWord.equals("3")))
			correctWord = word;
		
		return correctWord;
	}
	
}
