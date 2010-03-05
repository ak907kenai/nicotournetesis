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

	public static String[] arffAttributes = null;
	// public static String arffAttributes = null;

	public static int docsMax = 0;

	public static int maxCatLevels = 0;

	public static int minCatCount = 0;

	public static int minTermsPresence = 0;
	
	public static String stopWordsFile = null;
	
	public static String[] includeCats = null;
	
	public static int minLengthTerm = 0;
	

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
			setDocsMax(Integer.parseInt(raiz.getChild("docsmax").getText()));

			// Set minCatCount property
			setMinCatCount(Integer.parseInt(raiz.getChild("mincatcount")
					.getText()));

			// Set maxCatLevels property
			setMaxCatLevels(Integer.parseInt(raiz.getChild("maxcatlevels")
					.getText()));

			// Set minTermsPresence property
			setMinTermsPresence(Integer.parseInt(raiz.getChild("mintermspresence")
					.getText()));
			
			// Set stopWordsFile property
			setStopWordsFile(raiz.getChild("stopwordsfile").getText());
			
			// Set removeCats property
			setIncludeCats(raiz.getChild("includecats").getText());
			
			// Set minLengthTerm property
			setMinLengthTerm(Integer.parseInt(raiz.getChild("minlengthterm").getText()));


		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	// Returns the data file (.xml)
	public static String getDataFile() {
		if (ConfigParser.dataFile == null)
			parse();
		return ConfigParser.dataFile;
	}

	// Set data file (.xml)
	public static void setDataFile(String dataFile) {
		ConfigParser.dataFile = dataFile;
	}

	// Returns the dest file (.csv)
	public static String getDestFile() {
		if (ConfigParser.destFile == null)
			parse();
		return ConfigParser.destFile;
	}

	// Set dest file (.xml)
	public static void setDestFile(String destFile) {
		ConfigParser.destFile = destFile;
	}

	// Returns the csv columns
	public static String getCsvColumns() {
		if (ConfigParser.csvColumns == null)
			parse();
		return ConfigParser.csvColumns;
	}

	// Set the csv columns
	public static void setCsvColumns(String csvColumns) {
		ConfigParser.csvColumns = csvColumns;
	}

	// Returns arff file
	public static String getArffFile() {
		if (ConfigParser.arffFile == null)
			parse();
		return ConfigParser.arffFile;
	}

	// Set arff file
	public static void setArffFile(String arffFile) {
		ConfigParser.arffFile = arffFile;
	}

	// Returns arff data file
	public static String getArffDataFile() {
		if (ConfigParser.arffDataFile == null)
			parse();
		return ConfigParser.arffDataFile;
	}

	// Set arff data file
	public static void setArffDataFile(String arffDataFile) {
		ConfigParser.arffDataFile = arffDataFile;
	}

	// Returns arff attributes
	public static String[] getArffAttributes() {
		if (ConfigParser.arffAttributes == null)
			parse();
		return ConfigParser.arffAttributes;
	}

	// Set arff file
	public static void setArffAttributes(String arffAttributes) {
		ConfigParser.arffAttributes = arffAttributes.split(";");
	}

	// Returns the docs max
	public static int getDocsMax() {
		if (ConfigParser.docsMax == 0)
			parse();
		return ConfigParser.docsMax;
	}

	public static void setDocsMax(int docsMax) {
		ConfigParser.docsMax = docsMax;
	}

	public static int getMinCatCount() {
		if (ConfigParser.minCatCount == 0)
			parse();
		return ConfigParser.minCatCount;
	}

	public static void setMinCatCount(int minCatCount) {
		ConfigParser.minCatCount = minCatCount;
	}

	public static int getMaxCatLevels() {
		if (ConfigParser.maxCatLevels == 0)
			parse();
		return ConfigParser.maxCatLevels;
	}

	public static void setMaxCatLevels(int maxCatLevels) {
		ConfigParser.maxCatLevels = maxCatLevels;
	}

	public static int getMinTermsPresence() {
		if (ConfigParser.minTermsPresence == 0)
			parse();
		return ConfigParser.minTermsPresence;
	}

	public static void setMinTermsPresence(int minTermsPresence) {
		ConfigParser.minTermsPresence = minTermsPresence;
	}
	
	public static String getStopWordsFile() {
		if (ConfigParser.stopWordsFile == null)
			parse();
		return ConfigParser.stopWordsFile;
	}

	public static void setStopWordsFile(String stopWordsFile) {
		ConfigParser.stopWordsFile = stopWordsFile;
	}
	
	// Set include cats
	public static void setIncludeCats(String includeCats) {
		ConfigParser.includeCats = includeCats.split(";");
	}

	// Returns include cats
	public static String[] getIncludeCats() {
		if (ConfigParser.includeCats == null)
			parse();
		return ConfigParser.includeCats;
	}

	// Set min length term
	public static void setMinLengthTerm(int minLengthTerm) {
		ConfigParser.minLengthTerm = minLengthTerm;
	}

	// Returns min length term
	public static int getMinLengthTerm() {
		if (ConfigParser.minLengthTerm == 0)
			parse();
		return ConfigParser.minLengthTerm;
	}
	
	

	// Returns if parse the attribute passed by param
	public static boolean parseAttribute(String attribute) {
		String[] attrs = ConfigParser.getArffAttributes();
		for (int i = 0; i < attrs.length; i++) {
			if (attrs[i].startsWith(attribute))
				return true;
		}
		return false;
	}

}
