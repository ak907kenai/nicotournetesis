package filter;

import tesis.parser.ConfigParser;
import encoder.TermsEncoder;

public class TermsFilter extends DataFilter {

	private TermsEncoder termsEncoder;
	
	
	public TermsFilter(TermsEncoder termsEncoder) {
		this.termsEncoder = termsEncoder;
	}

	@Override
	public boolean eval(String str) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	/**
	 * Filter the terms with <= ConfigParser.getMinTermsPresence() occurrences
	 * @param str
	 * @return filter str
	 */
	public String filter(String str) {
		
		StringBuffer filterSB = new StringBuffer();
		
		// Split the whole line
		String[] dataList = str.split(",");
		for (int i = 0; i+1 < dataList.length; i++) {
			
			String data = dataList[i];
			
			// Save all the string from one data field (i.e. toptags or anchortext or ...)
			StringBuffer dataSB = new StringBuffer();
			dataSB.append("\"");
			
			// Split to get each term
			data = data.replace("\"", "");
			String[] termList = data.split(" ");
			for(String term : termList) {
				Integer countPresence = (Integer) this.termsEncoder.getTerms().get(term);
				if (countPresence != null)
					// If the term is present < ConfigParser.getMinTermsPresence() times, remove it
					if (countPresence.intValue() >= ConfigParser.getMinTermsPresence()) 
						dataSB.append(term + " ");
			}
			dataSB.append("\"");
			
			// Append the filtered data into the final filter string (line)
			filterSB.append(dataSB.toString().trim() + ",");
		}
		
		// Append the class value
		filterSB.append(dataList[dataList.length-1]);
		
		return filterSB.toString();
		
	}

}
