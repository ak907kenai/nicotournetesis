package tesis.parser;

import filter.CategoryDataFilter;

public class Convert2ArffMain {

	
	public static void main(String args[]) throws Exception {

		ArffConverter handler = new CategoryFilterArffConverter();

		// Write arff data
		//handler.readData(handler);

		// Write arff attributes
		handler.writeARFFHeader();
		
		handler.writeARFFData();
		
		System.out.println("Finished!");

	}
}
