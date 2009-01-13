package tesis.parser;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Parses the config.xml class
 * 
 * @author nicotourne
 * 
 */
public class ConfigParser {

	public static String dataFile = null;

	public static String destFile = null;

	public static String csvColumns = null;
	
	public static int docsMax = 0;
	

	// Parse the config.xml file
	public static void parse() {

		SAXBuilder builder = new SAXBuilder(false);

		// usar el parser Xerces y no queremos
		// que valide el documento
		Document doc = null;
		try {
			doc = builder.build("conf\\config.xml");

			// construyo el arbol en memoria desde el fichero
			// que se lo pasaré por parametro.
			Element raiz = doc.getRootElement();

			// Set datafile path property
			setDataFile(raiz.getChild("datafile").getText());

			// Set destfile path property
			setDestFile(raiz.getChild("destfile").getText());

			// Set csvColumns property
			setCsvColumns(raiz.getChild("csvcolumns").getText());

			// Set docsMax property
			setDocsMax(Integer.parseInt(raiz.getChild("docsMax").getText()));
			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Returns the data file (.xml)
	public static String getDataFile() {
		if (dataFile == null) {
			parse();
		}
		return dataFile;
	}

	// Set data file (.xml)
	public static void setDataFile(String dataFile) {
		ConfigParser.dataFile = dataFile;
	}

	// Returns the dest file (.csv)
	public static String getDestFile() {
		if (destFile == null) {
			parse();
		}
		return destFile;
	}

	// Set dest file (.xml)
	public static void setDestFile(String destFile) {
		ConfigParser.destFile = destFile;
	}

	// Returns the csv columns
	public static String getCsvColumns() {
		if (csvColumns == null) {
			parse();
		}
		return csvColumns;
	}

	// Set the csv columns
	public static void setCsvColumns(String csvColumns) {
		ConfigParser.csvColumns = csvColumns;
	}

	// Returns the docs max
	public static int getDocsMax() {
		return docsMax;
	}

	// Returns the docs max
	public static void setDocsMax(int docsMax) {
		if (docsMax == 0) {
			parse();
		}
		ConfigParser.docsMax = docsMax;
	}

}
