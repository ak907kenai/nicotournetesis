package tesis.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class Convert2ArffUsingArffSaver extends DefaultHandler {

	private Instances data;

	private double[] vals;

	private String arffdata;

	private int docsCount;

	private int docsCountFree;

	private static BufferedWriter writer;
	
	private ArffSaver saver;

	public static void main(String args[]) throws Exception {

		Convert2ArffUsingArffSaver handler = new Convert2ArffUsingArffSaver();

		// Create the arff writer
		// writer = new BufferedWriter(new
		// FileWriter(ConfigParser.getArffFile()));

		// Write arff attributes
		handler.writeARFFHeader();

		handler.iniVals();

		handler.writeARFFData(handler);

		System.out.println(handler.getData());

		System.out.println("Finished!");

	}

	public Convert2ArffUsingArffSaver() {
		super();
		arffdata = "";
		docsCount = 0;
		docsCountFree = 0;
	}

	public void iniVals() {
		vals = new double[data.numAttributes()];
	}

	/**
	 * Write ARFF attributes
	 * 
	 * @throws IOException
	 */
	public void writeARFFHeader() throws IOException {

		// 1. set up attributes
		FastVector atts = new FastVector();

		String arffAttributes[] = ConfigParser.getArffAttributes().split(",");
		for (int i = 0; i < arffAttributes.length; i++) {
			String attribute[] = arffAttributes[i].split(" ");

			if (attribute[1].equals("string")) {
				atts.addElement(new Attribute(attribute[0], (FastVector) null));
			}

			else if (attribute[1].equals("numeric")) {
				atts.addElement(new Attribute(attribute[0]));
			}
		}

		data = new Instances("webdata", atts, 0);

		/*saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(ConfigParser.getArffFile()));
		saver.writeBatch();*/
	}

	/**
	 * Write ARFF data
	 * 
	 * @param handler
	 * @throws SAXException
	 * @throws IOException
	 */
	public void writeARFFData(Convert2ArffUsingArffSaver handler)
			throws SAXException, IOException {

		saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(ConfigParser.getArffFile()));
		//saver.writeBatch();
		
		// Create XML Parser and handler
		XMLReader xr = XMLReaderFactory.createXMLReader();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);

		// Parse the xmlfile
		String xmlfile = ConfigParser.getDataFile();
		FileReader r = new FileReader(xmlfile);
		xr.parse(new InputSource(r));
	}

	/**
	 * Start document
	 */
	public void startElement(String uri, String name, String qName,
			Attributes atts) {

		if (atts.getValue(0) != null) {

			if (qName.equals("document")) {
				vals[0] = data.attribute(0).addStringValue(atts.getValue(0));
				// arffdata += atts.getValue(0) + ",";
				// System.out.println(atts.getValue(0));
			}

			if (qName.equals("category")) {
				vals[1] = data.attribute(1).addStringValue(atts.getValue(0));
				// arffdata += atts.getValue(0) + ",";
				// System.out.println(atts.getValue(0));
			}

			if (qName.equals("search")) {
				vals[2] = data.attribute(2).addStringValue(atts.getValue(0));
				// arffdata += atts.getValue(0) + ",";
				// System.out.println(atts.getValue(0));
			}
		}
	}

	/**
	 * End element
	 */
	public void endElement(String uri, String name, String qName) {

		if (name.equals("document")) {
			//data.add(new Instance(1.0, vals));
			
			try {
				saver.writeIncremental(new Instance(1.0, vals));
				//saver.writeBatch();
			} catch (IOException e) {
				e.printStackTrace();
			}			
			
			docsCount++;
			docsCountFree++;
			System.out.println(docsCount);

			if (docsCountFree == 1000) {
				
				
				/*try {
					ArffSaver saver = new ArffSaver();
					saver.setFile(new File(ConfigParser.getArffFile()));
					saver.setInstances(data);
					saver.writeBatch();
				} catch (IOException e) {
					e.printStackTrace();
				}*/				
				
				data = null;

				// 1. set up attributes
				FastVector atts = new FastVector();

				String arffAttributes[] = ConfigParser.getArffAttributes()
						.split(",");
				for (int i = 0; i < arffAttributes.length; i++) {
					String attribute[] = arffAttributes[i].split(" ");

					if (attribute[1].equals("string")) {
						atts.addElement(new Attribute(attribute[0],
								(FastVector) null));
					}

					else if (attribute[1].equals("numeric")) {
						atts.addElement(new Attribute(attribute[0]));
					}
				}


				data = new Instances("webdata", atts, 0);

				// freeMemory();
				docsCountFree = 0;
			}

			
			vals = new double[data.numAttributes()];

		}

		if (name.equals("document2")) {
			// Create empty instance with three attribute values
			Instance inst = new Instance(data.numAttributes());

			String arffAttributes[] = ConfigParser.getArffAttributes().split(
					",");
			for (int i = 0; i < arffAttributes.length; i++) {
				String attribute[] = arffAttributes[i].split(" ");

			}
			inst.setValue(1, "first");
			inst.setValue(2, "second");
			inst.setValue(3, "third");
			// Set instance's values for the attributes "length", "weight", and
			// "position"
			// inst.setValue(, 5.3);
			// inst.setValue(weight, 300);
			// inst.setValue(position, "first");

			// Set instance's dataset to be the dataset "race"
			// inst.setDataset("webdata");

			// Print the instance
			System.out.println("The instance: " + inst);

			/*
			 * docsCount++; System.out.println(docsCount); // Instances dataSet =
			 * ... try { writer.append(arffdata); //writer.append("\n"); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } arffdata = "";
			 */

		}

	}

	public void freeMemory() {
		String myString = new String("Free Me");
		// Do stuff with myString
		myString = null;
		Runtime rt = Runtime.getRuntime();
		rt.gc();
	}

	public Instances getData() {
		return data;
	}
}
