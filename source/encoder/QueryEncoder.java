package encoder;

import java.util.Hashtable;

public class QueryEncoder {

	// Query + queryCode
	private Hashtable<String, String> queries;

	private int queryCode;

	public QueryEncoder() {
		queries = new Hashtable<String, String>();
		queryCode = 1;
	}

	public String encode(String query) {

		String queryCodeFound;

		// If the query have not been added
		if (!queries.containsKey(query)) {
			queries.put(query, Integer.toString(queryCode));
			queryCodeFound = Integer.toString(queryCode);
			queryCode++;
		}
		// If the query have been added before, I must recover
		else {
			queryCodeFound = (String) queries.get(query);
		}

		return queryCodeFound;

	}

	/**
	 * Implement of toString method
	 */
	/*
	 * public String toString() { Enumeration keys = queries.keys();
	 * StringBuffer keysBuffer = new StringBuffer(); while(
	 * keys.hasMoreElements() ) { String catId = (String)keys.nextElement();
	 * Integer catCount = queries.get(catId); keysBuffer.append(catId + ": " +
	 * catCount); if (keys.hasMoreElements()) keysBuffer.append("\n"); } return
	 * keysBuffer.toString(); }
	 */
}
