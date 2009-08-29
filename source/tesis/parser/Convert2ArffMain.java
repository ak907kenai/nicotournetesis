package tesis.parser;

public class Convert2ArffMain {

	public static void main(String args[]) throws Exception {

		ArffConverter handler = new ArffConverterImpl();

		// Write arff data
		// handler.readData(handler);

		// Write arff attributes
		handler.writeARFFHeader();

		handler.writeARFFData();

		System.out.println("Finished!");

	}
}
