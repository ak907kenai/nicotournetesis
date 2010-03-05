package tesis.parser;

import java.util.Date;


public class Convert2ArffMain {

	public static void main(String args[]) throws Exception {

		
		long startTime = System.currentTimeMillis();
		
		ArffConverter handler = new ArffConverterImpl();

		// Write arff data
		// handler.readData(handler);

		// Write arff attributes
		handler.writeARFFHeader();

		handler.writeARFFData();
		//handler.printTerms();
		
		long endTime = System.currentTimeMillis();
		long dif = endTime - startTime;
		
		Date difMin = new Date(dif);
		System.out.println("Total time: " + difMin.getHours() + ":" + difMin.getMinutes() + ":" + difMin.getSeconds());

		System.out.println("Finished!");

	}
}
