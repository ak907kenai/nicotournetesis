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

	public static String arffFile = null;

	public static String arffDataFile = null;

	public static String csvColumns = null;

	public static String arffAttributes = null;

	public static int docsMax = 0;

	public static int minCatCount = 0;

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

			// Set arfffile path property
			setArffFile(raiz.getChild("arfffile").getText());

			// Set arff data file property
			setArffDataFile(raiz.getChild("arffdatafile").getText());

			// Set arff attributes property
			setArffAttributes(raiz.getChild("arffattributes").getText());

			// Set docsMax property
			setDocsMax(Integer.parseInt(raiz.getChild("docsMax").getText()));

			// Set minCatCount property
			setMinCatCount(Integer.parseInt(raiz.getChild("mincatcount")
					.getText()));

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

	// Returns arff file
	public static String getArffFile() {
		if (arffFile == null) {
			parse();
		}
		return arffFile;
	}

	// Set arff file
	public static void setArffFile(String arffFile) {
		ConfigParser.arffFile = arffFile;
	}

	// Returns arff data file
	public static String getArffDataFile() {
		if (arffDataFile == null) {
			parse();
		}
		return arffDataFile;
	}

	// Set arff data file
	public static void setArffDataFile(String arffDataFile) {
		ConfigParser.arffDataFile = arffDataFile;
	}

	// Returns arff attributes
	public static String getArffAttributes() {
		if (arffAttributes == null) {
			parse();
		}
		return arffAttributes;
	}

	// Set arff file
	public static void setArffAttributes(String arffAttributes) {
		ConfigParser.arffAttributes = arffAttributes;
	}

	// Returns the docs max
	public static int getDocsMax() {
		if (docsMax == 0) {
			parse();
		}
		return docsMax;
	}

	// Set doc max
	public static void setDocsMax(int docsMax) {
		ConfigParser.docsMax = docsMax;
	}

	// Returns min cat count
	public static int getMinCatCount() {
		if (minCatCount == 0) {
			parse();
		}
		return minCatCount;
	}

	// Set min cat count
	public static void setMinCatCount(int minCatCount) {
		ConfigParser.minCatCount = minCatCount;
	}
}
