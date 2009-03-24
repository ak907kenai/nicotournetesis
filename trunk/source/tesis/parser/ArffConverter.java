package tesis.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import encoder.CategoryEncoder;
import encoder.DataEncoder;
import filter.CategoryDataFilter;
import filter.DataFilter;

public class ArffConverter extends DefaultHandler {

	private String category;

	private String query;
	
	private String anchorText;

	private int docsCount;

	private int docsMax;

	protected static BufferedWriter writer;

	protected CategoryEncoder categoryEncoder;

	protected DataEncoder queryEncoder;
	
	protected DataEncoder anchorTextEncoder;

	protected DataFilter filter;

	public ArffConverter() {
		super();
		filter = null;
		iniEncoders();
	}

	public void iniEncoders() {
		categoryEncoder = new CategoryEncoder();
		queryEncoder = new DataEncoder();
		anchorTextEncoder = new DataEncoder();
	}

	/**
	 * Write ARFF attributes
	 * 
	 * @throws SAXException
	 */
	public void writeARFFHeader() throws SAXException {
		try {

			// Read data from xml file
			this.writeTemporalData();

			// Create the arff writer
			writer = new BufferedWriter(new FileWriter(ConfigParser
					.getArffFile()));

			// Relation
			writer.append("@relation docs");
			writer.newLine();
			writer.newLine();

			// Attributes
			String arffAttributes[] = ConfigParser.getArffAttributes().split(
					";");
			for (int i = 0; i < arffAttributes.length; i++) {
				if (arffAttributes[i].equals("class"))
					writer.append("@attribute " + arffAttributes[i] + " "
							+ categoryEncoder.nominal());
				else
					writer.append("@attribute " + arffAttributes[i]);
				writer.newLine();
			}

			writer.newLine();
			writer.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write data in the ARFF file
	 * 
	 */
	public void writeARFFData() {

		try {

			writer.append("@data");
			writer.newLine();

			int instancesCount = 0;

			// Create reader for file to append from
			BufferedReader in = new BufferedReader(new FileReader(ConfigParser
					.getArffDataFile()));
			String str;
			while ((str = in.readLine()) != null) {
				if (filter != null) {
					if (filter.eval(str)) {
						writer.append(str);
						writer.newLine();

						instancesCount++;
					}
				}
			}
			in.close();

			// Close the file
			writer.flush();
			writer.close();

			System.out.println("Instances: " + instancesCount);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Read the xml and generate a temporal file with ARFF data
	 * 
	 * @throws SAXException
	 */
	public void writeTemporalData() throws SAXException, IOException {

		// Create the arff writer
		writer = new BufferedWriter(new FileWriter(ConfigParser
				.getArffDataFile()));

		docsCount = 1;
		docsMax = ConfigParser.getDocsMax();
		System.out.println(docsMax);

		// Create XML Parser and handler
		XMLReader xr = XMLReaderFactory.createXMLReader();
		xr.setContentHandler(this);
		xr.setErrorHandler(this);

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

		if (docsCount <= docsMax) {

			if (name.equals("document")) {
				category = "";
				query = "";
				anchorText = "";
			}

			if (atts.getValue(0) != null) {

				// Category
				if (qName.equals("category") && (category.equals(""))) {
					category = categoryEncoder.encode(this
							.removeSpecialChars(atts.getValue(0)));
				}

				// Query (search)
				if (qName.equals("search") && (query.equals(""))) {
					query = queryEncoder.encode(this.removeSpecialChars(atts
							.getValue(0)));
				}
				
				// Anchor text
				if (qName.equals("inlink") && (anchorText.equals(""))) {
				/*	anchorText = anchorTextEncoder.encode(this.removeSpecialChars(atts
							.getValue(0)));*/
					
					String text = this.removeSpecialChars(atts
							.getValue(0));
					anchorText = anchorTextEncoder.encode(text);
				}				

			}
		}
	}

	/**
	 * End element
	 */
	public void endElement(String uri, String name, String qName) {

		if (docsCount <= docsMax) {
			if (name.equals("document")) {
				docsCount++;
				try {
					if ((query == null) || (query.equals(""))
							|| (query.equals(" ")))
						query = "?";
					if ((anchorText == null) || (anchorText.equals(""))
							|| (anchorText.equals(" ")))
						anchorText = "?";					
					writer.append(query + "," + anchorText + "," + category);
					writer.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Remove special chars
	 * 
	 * @param text
	 * @param charsToKeep
	 * @return
	 */
	public String removeSpecialChars(String text) {
		text = text.toLowerCase();
		String charsToKeep = "abcdefghijklmnopqrstuvwxyz";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (charsToKeep.indexOf(ch) > -1) {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	/**
	 * Set a Data Filter instance
	 * 
	 * @param filter
	 */
	public void setDataFilter(CategoryDataFilter filter) {
		this.filter = filter;
	}

}
