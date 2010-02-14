package encoder;

import java.util.Hashtable;


/**
 * Save all the terms (query, anchortext, tags) in the dataset and the count presence of each one
 * @author nicotourne
 *
 */
public class TermsEncoder {

	private Hashtable<String,Integer> terms;
	
	public TermsEncoder() {
		terms = new Hashtable<String,Integer>();
	}
	
	
	/**
	 * Add each term in text into the terms hash
	 * @param text
	 */
	public void addTerm(String term) {
		if (!terms.containsKey(term))
			this.terms.put(term, new Integer(1));
		else {
			int presenceCount = terms.get(term).intValue() + 1;
			this.terms.put(term, new Integer(presenceCount));
		}
		/*String[] tokens = text.split(" ");
		for(int i = 0; i < tokens.length; i++) {
			if (!terms.containsKey(tokens[i]))
				this.terms.put(tokens[i], new Integer(1));
			else {
				int presenceCount = terms.get(tokens[i]).intValue() + 1;
				this.terms.put(tokens[i], new Integer(presenceCount));
			}
		}*/
	}

	
	/**
	 * Returns the terms hashtable
	 * @return Hashtable terms
	 */
	public Hashtable getTerms() {
		return this.terms;
	}
	
}
