package tesis.parser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * Generates a csv file from the data file (.xml)
 * 
 * @author nicotourne
 * 
 */
public class GenerateCSVFile {

	// List of documents
	public static List documents = null;

	// Return parsed document's list
	public static List getDocuments() {
		if (documents == null)
			parseDataFile();
		return documents;
	}

	// Set parsed document's list
	public static void setDocuments(List documents) {
		GenerateCSVFile.documents = documents;
	}

	// Parse data file (.xml)
	public static void parseDataFile() {

		try {
			SAXBuilder builder = new SAXBuilder(false);

			Document doc = builder.build(ConfigParser.getDataFile());
			Element raiz = doc.getRootElement();

			// Get the documents
			setDocuments(raiz.getChildren("document"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Generates the CSV file
	public static void generateCSVFile() {

		// Parses the data file (.xml)
		parseDataFile();

		// Get the parsed docs
		List documents = GenerateCSVFile.getDocuments();
		Iterator docs = documents.iterator();

		int MAX = ConfigParser.getDocsMax();
		int cursor = 1;

		// Get csv columns
		String csvColumns = ConfigParser.getCsvColumns();
		String columns[] = csvColumns.split(",");

		try {
			FileWriter writer = new FileWriter(ConfigParser.getDestFile());

			// Build the first row of the csv file (the titles of each column)
			for (int i = 0; i < columns.length; i++) {
				writer.append(columns[i]);
				if (i + 1 < columns.length)
					writer.append(',');
				else
					writer.append('\n');
			}

			// Iterate into the docs
			while (docs.hasNext() && cursor < MAX) {
				Element e = (Element) docs.next();

				// For each column
				for (int i = 0; i < columns.length; i++) {
					String column = columns[i];
					String columnValue = null;
					// If only one column
					if (!column.contains("/")) {
						System.out.println(column + " = "
								+ e.getAttribute(column).getValue());
						columnValue = e.getAttribute(column).getValue();
					} else {
						// Get the subcolumn values
						String subcolumns[] = column.split("/");
						List subcolumn = e.getChildren(subcolumns[0]);
						Iterator i2 = subcolumn.iterator();
						while (i2.hasNext()) {
							Element e2 = (Element) i2.next();
							System.out.println(e2.getAttribute(subcolumns[1])
									.getValue());
							columnValue += e2.getAttribute(subcolumns[1]).getValue() + " ";
						}

					}
					
					if (columnValue == null)
						writer.append(" ");
					else {
						columnValue = columnValue.replaceAll(",", "");
						writer.append(columnValue);
					}
					
					if (i + 1 < columns.length)
						writer.append(',');
					else
						writer.append('\n');
				}
				
				cursor++;
			}

			writer.flush();
			writer.close();
			

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
