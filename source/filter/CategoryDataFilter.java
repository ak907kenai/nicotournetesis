package filter;

import java.util.Hashtable;

import tesis.parser.ConfigParser;

public class CategoryDataFilter extends DataFilter {

	private Hashtable<String, Integer> categories;

	public CategoryDataFilter(Hashtable<String, Integer> categories) {
		this.categories = categories;
	}

	/**
	 * Returns true if the instance (str) belongs to a category that have >=
	 * minCatCount instances
	 */
	public boolean eval(String str) {
		String strData[] = str.split(",");

		// Returns false if the document belongs to a removed category
		/*String catCode = strData[strData.length - 1];
		for (String removeCatCode : ConfigParser.getRemoveCats()) {
			if (removeCatCode.trim().equals(catCode.trim()))
				return false;
		}*/

		// Get the cat id from str and check if the category in categories has more than ConfigParser.getMinCatCount() instances
		Integer catCount = categories.get(strData[strData.length - 1]);
		if ((catCount != null)
				&& (catCount.intValue() >= ConfigParser.getMinCatCount())) {
			
			// TODO Chequear si cada uno de los textos de query/anchortext/tags se repite mas de N veces
			
			return true;
		} else
			return false;
	}
}
