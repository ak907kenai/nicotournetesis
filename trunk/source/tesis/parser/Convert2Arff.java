package tesis.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import encoder.CategoryEncoder;

public class Convert2Arff extends DefaultHandler {

	private static String arffLine = "";

	private static String category;
	
	private static String search;
	
	private static String url;

	private static int docsCount;

	private static int docsMax;

	private static BufferedWriter writer;
	
	private CategoryEncoder categoryEncoder;

	public Convert2Arff() {
		super();
		categoryEncoder = new CategoryEncoder();
	}

	public static void main(String args[]) throws Exception {

		Convert2Arff handler = new Convert2Arff();

		// Write arff data
		handler.writeARFFData(handler);
		
		// Write arff attributes
		handler.writeARFFHeader();
		
		System.out.println("Finished!");

	}

	/**
	 * Write ARFF attributes
	 */
	public void writeARFFHeader() {
		try {
			
			// Create the arff writer
			writer = new BufferedWriter(new FileWriter(ConfigParser.getArffFile()));
			
			// Relation
			writer.append("@relation docs");
			writer.newLine();
			writer.newLine();

			// Attributes
			String arffAttributes[] = ConfigParser.getArffAttributes().split(
					";");
			for (int i = 0; i < arffAttributes.length; i++) {
				if (arffAttributes[i].equals("class"))
					writer.append("@attribute " + arffAttributes[i] + " " + categoryEncoder.nominal());
				else
					writer.append("@attribute " + arffAttributes[i]);
				writer.newLine();
			}
			
			writer.newLine();
			writer.newLine();
			
			
			// Create reader for file to append from
			BufferedReader in = new BufferedReader(new FileReader(ConfigParser.getArffDataFile()));
			String str;
			while ((str = in.readLine()) != null) {
				writer.append(str);
				writer.newLine();
			}
			in.close();
			
			
			// Close the file
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write ARFF data
	 * 
	 * @param handler
	 * @throws SAXException
	 * @throws IOException
	 */
	public void writeARFFData(Convert2Arff handler) throws SAXException,
			IOException {

		// Create the arff writer
		System.out.println(ConfigParser.getArffDataFile());
		writer = new BufferedWriter(new FileWriter(ConfigParser.getArffDataFile()));
		
		writer.append("@data");
		writer.newLine();

		// Create XML Parser and handler
		XMLReader xr = XMLReaderFactory.createXMLReader();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);

		// Parse the xmlfile
		String xmlfile = ConfigParser.getDataFile();
		FileReader r = new FileReader(xmlfile);
		xr.parse(new InputSource(r));
		
		// Close the file
		writer.flush();
		writer.close();
		
	}

	/**
	 * Start document
	 */
	public void startElement(String uri, String name, String qName,
			Attributes atts) {

		if (name.equals("document")) {
			category = "";
			search = "";
			url = "";
		}

		if (atts.getValue(0) != null) {

			if (qName.equals("document") && (url.equals(""))) {
				System.out.println(atts.getValue(0));
				url = atts.getValue(0);
			}

			if (qName.equals("category") && (category.equals(""))) {
				System.out.println(atts.getValue(0));
				//category += this.removeSpaces(atts.getValue(0));
				category = categoryEncoder.encode(this.removeSpaces(atts.getValue(0)));
			}

			if (qName.equals("search") && (search.equals(""))) {
				System.out.println(atts.getValue(0));
				search = "'" + this.removeSpaces(atts.getValue(0));
			}

		}
	}

	
	/**
	 * End element
	 */
	public void endElement(String uri, String name, String qName) {
		if (name.equals("document")) {
			docsCount++;
			try {
				writer.append(docsCount + "," + 1 + "," + category);
				writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			arffLine = "";
		}
	}


	
	/**
	 * Remove spaces from a string
	 * @param s
	 * @return String without spaces
	 */
	public String removeSpaces(String s) {
		StringTokenizer st = new StringTokenizer(s, " ", false);
		String t = "";
		while (st.hasMoreElements())
			t += st.nextElement();
		return t;
	}

}
