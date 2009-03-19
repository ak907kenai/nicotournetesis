package filter;

import java.util.Hashtable;

import tesis.parser.ConfigParser;

public class CategoryDataFilter extends DataFilter {

	Hashtable<String, Integer> categories;

	public CategoryDataFilter(Hashtable<String, Integer> categories) {
		this.categories = categories;
	}

	/**
	 * Returns true if the instance (str) belongs to a category that have >=
	 * minCatCount instances
	 */
	public boolean eval(String str) {
		String strData[] = str.split(",");
		// TODO Obtener el id categoria de str y fijarse en categories si esa
		// categoria tiene mas de X instancias
		Integer catCount = categories.get(strData[strData.length - 1]);
		if ((catCount != null)
				&& (catCount.intValue() >= ConfigParser.getMinCatCount())) {
			System.out.println(catCount.intValue());
			return true;
		} else
			return false;
	}
}
