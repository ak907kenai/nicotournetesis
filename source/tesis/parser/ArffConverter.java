package tesis.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import tesis.utilities.StringUtilities;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import encoder.CategoryEncoder;
import encoder.DataEncoder;
import encoder.TermsEncoder;
import filter.CategoryDataFilter;
import filter.DataFilter;
import filter.TermsFilter;

public class ArffConverter extends DefaultHandler {

	private String category;

	private StringBuffer querySB;

	private StringBuffer anchorTextSB;

	private StringBuffer tagSB;

	private int docsCount;

	private int docsMax;

	protected static BufferedWriter writer;

	protected CategoryEncoder categoryEncoder;

	protected DataEncoder queryEncoder;

	protected DataEncoder anchorTextEncoder;

	protected DataEncoder tagEncoder;

	protected DataFilter filter;

	protected TermsFilter termsFilter;

	protected TermsEncoder termsEncoder;

	public ArffConverter() {
		super();
		filter = null;
		termsFilter = null;
		iniEncoders();
	}

	public void iniEncoders() {
		categoryEncoder = new CategoryEncoder();
		queryEncoder = new DataEncoder();
		anchorTextEncoder = new DataEncoder();
		tagEncoder = new DataEncoder();
		termsEncoder = new TermsEncoder();
	}

	/**
	 * Write ARFF attributes
	 * 
	 * @throws SAXException
	 */
	public void writeARFFHeader() throws SAXException {
		try {

			// Read data from xml file and save on a temp file
			this.writeTemporalData();

			// Create the arff writer
			writer = new BufferedWriter(new FileWriter(ConfigParser
					.getArffFile()));

			// Relation
			writer.append("@relation docs");
			writer.newLine();
			writer.newLine();

			// Attributes
			String arffAttributes[] = ConfigParser.getArffAttributes();
			for (int i = 0; i < arffAttributes.length; i++) {
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
				if (filter.eval(str)) {
					str = termsFilter.filter(str);
					writer.append(str);
					writer.newLine();

					instancesCount++;
				}
			}
			in.close();

			// Close the file
			writer.flush();
			writer.close();

			System.out.println("Instances: " + instancesCount);

			System.out.println("Categories: "
					+ categoryEncoder.getCategories().size());

			System.out.println("Documents: " + docsCount);

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
		System.out.println("Docs to process: " + docsMax);

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

			// Parse new document
			if (name.equals(ParserConstants.DOCUMENT)) {
				category = "";
				querySB = new StringBuffer();
				anchorTextSB = new StringBuffer();
				tagSB = new StringBuffer();
			}

			if (atts.getValue(0) != null) {

				// Category - Take only the first category from the doc (in case
				// that the doc has more than one category)
				if (qName.equals(ParserConstants.CATEGORY)
						&& ("".equals(category))) {
					String text = StringUtilities.removeSpecialChars(atts
							.getValue(0));

					if (this.isIncludeCat(text)) {
						category = categoryEncoder.encode(text);
					}
				}

				// Query (search)
				if (qName.equals(ParserConstants.SEARCH)) {
					String[] tokens = atts.getValue(0).split(" ");
					for (int i = 0; i < tokens.length; i++) {
						String text = this.filterInfo(tokens[i]);
						if (!"".equals(text)) {
							querySB.append(" " + text);
							termsEncoder.addTerm(text);							
						}
					}
					/*
					 * String text = this.filterInfo(atts.getValue(0));
					 * querySB.append(" " + text); termsEncoder.addTerm(text);
					 */
					// TODO Agregar ese texto al hashtable searchPresences
					// (texto+presencias)
				}

				// Anchor text
				if (qName.equals(ParserConstants.INLINK)) {
					String[] tokens = atts.getValue(0).split(" ");
					
					// If want synonyms, uncomment next lines
					for (int i = 0; i < tokens.length; i++) {
					
						// Filter each token
						String token = this.filterInfo(tokens[i]);
						
						if (false && !"".equals(token)) { 
						
							// Get synonym list for each token
							Set<String> tokensSyn = getSynonyms(token);
							
							// Process each synonym token
							for (String tokenSyn : tokensSyn) {

								// Apply filter again to each synonym
								tokenSyn = this.filterInfo(tokenSyn);
								
								if (!"".equals(tokenSyn)) {
									anchorTextSB.append(" " + tokenSyn);
									termsEncoder.addTerm(tokenSyn);
								}							
							}
						}
					}

					// If don't want get synonyms, uncomment next lines
					/*for (int i = 0; i < tokens.length; i++) {
						String text = this.filterInfo(tokens[i]);
						if (!"".equals(text)) {
							anchorTextSB.append(" " + text);
							termsEncoder.addTerm(text);
						}
					}*/
				}

				// Top tags
				if (qName.equals(ParserConstants.TOPTAG)) {
					String[] tokens = atts.getValue(0).split(" ");
					
					// If want synonyms, uncomment next lines
					for (int i = 0; i < tokens.length; i++) {
					
						// Filter each token
						String token = this.filterInfo(tokens[i]);
						
						if (false && !"".equals(token)) { 
						
							// Get synonym list for each token
							Set<String> tokensSyn = getSynonyms(token);
							
							// Process each synonym token
							for (String tokenSyn : tokensSyn) {

								// Apply filter again to each synonym
								tokenSyn = this.filterInfo(tokenSyn);
								
								if (!"".equals(tokenSyn)) {
									tagSB.append(" " + tokenSyn);
									termsEncoder.addTerm(tokenSyn);
								}							
							}
						}
					}
					
					// If don't want get synonyms, uncomment next lines					
					/*for (int i = 0; i < tokens.length; i++) {
						String text = this.filterInfo(tokens[i]);
						if (!"".equals(text)) {
							tagSB.append(" " + text);
							termsEncoder.addTerm(text);
						}
					}*/
				}

			}
		}
	}

	/**
	 * Returns a list of synonyms for each term
	 * @param String term
	 * @return Set<String> collection of synonyms for the param term (included the original term)
	 */
	private Set<String> getSynonyms(String term) {

		// List to return the list of synonyms
		Set<String> termSyns = new HashSet<String>();
		termSyns.add(term);
		
		// Get the synsets containing the term
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		Synset[] synsets = database.getSynsets(term, SynsetType.NOUN);

		// Display the word forms and definitions for synsets retrieved
		if (synsets.length > 0) {
			for (int i = 0; i < synsets.length; i++) {
				String[] wordForms = synsets[i].getWordForms();
				for (int j = 0; j < wordForms.length; j++) {
					termSyns.add(wordForms[j]);
				}
			}
		}
		
		return termSyns;
	}

	/**
	 * Returns true if cat belongs to one of the remove categories
	 * 
	 * @param catName
	 * @return
	 */
	private boolean isIncludeCat(String catName) {
		catName = catName.replaceFirst("top/", "");
		for (String includeCatName : ConfigParser.getIncludeCats()) {
			includeCatName = includeCatName.replaceFirst("top/", "");
			if (catName.startsWith(includeCatName))
				return true;
		}
		return false;
	}

	/**
	 * Filter the info before adding to the arff file
	 * 
	 * @param info
	 * @return filterInfo
	 */
	private String filterInfo(String info) {
		info = StringUtilities.replaceHtmlCoding(info);
		info = StringUtilities.removeAccents(info);
		info = StringUtilities.removeSpecialChars(info);
		
		// TODO try applying and not applying stems
		info = StringUtilities.stem(info);
		info = StringUtilities.removeStopWords(info);

		// TODO try applying and not applying spellcheck
		info = StringUtilities.spellCheck(info);
		
		info = (info.length() >= ConfigParser.getMinLengthTerm()) ? info : "";  
		
		return info;
	}

	/**
	 * End element
	 */
	public void endElement(String uri, String name, String qName) {

		if (docsCount <= docsMax) {
			if (name.equals(ParserConstants.DOCUMENT)) {

				// Add the doc only if it has query, anchor-text and tags
				if (isCompleteDoc()) {
					try {

						docsCount++;

						// Remove search duplicated terms
						if (ConfigParser.parseAttribute(ParserConstants.SEARCH)) {
							writer
									.append('"' + StringUtilities
											.removeDuplicated(querySB
													.toString().trim()) + '"' + ',');
						}

						// Remove anchor-text duplicated terms
						if (ConfigParser.parseAttribute(ParserConstants.INLINK)) {
							writer.append('"' + StringUtilities
									.removeDuplicated(anchorTextSB.toString()
											.trim()) + '"' + ',');
						}

						// Remove tags duplicated terms
						if (ConfigParser.parseAttribute(ParserConstants.TOPTAG)) {
							writer
									.append('"' + StringUtilities
											.removeDuplicated(tagSB.toString()
													.trim()) + '"' + ',');
						}

						writer.append(category);

						writer.newLine();

						if (docsCount % 1000 == 0)
							System.out.println("Processed doc #" + docsCount);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * Returns true if the doc has query, anchor-text and tags
	 * 
	 * @return completeDoc
	 */
	private boolean isCompleteDoc() {

		return (isCompleteInfo(this.category) && isCompleteInfo(this.querySB)
				&& isCompleteInfo(this.anchorTextSB) && isCompleteInfo(this.tagSB));
	}

	/**
	 * Returns true if the StringBuffer info is != null and != ""
	 * 
	 * @param info
	 * @return completeInfo
	 */
	private boolean isCompleteInfo(StringBuffer info) {
		if ((info != null) && !("".equals(info.toString().trim()))
				&& !(" ".equals(info.toString())))
			return true;
		return false;
	}

	/**
	 * Returns true if the String info is != null and != ""
	 * 
	 * @param info
	 * @return completeInfo
	 */
	private boolean isCompleteInfo(String info) {
		return ((info != null) && !("".equals(info.trim())) && !(" "
				.equals(info)));
	}

	/**
	 * Set a Data Filter instance
	 * 
	 * @param filter
	 */
	public void setDataFilter(CategoryDataFilter filter) {
		this.filter = filter;
	}

	/**
	 * Set a Terms Filter instance
	 * 
	 * @param termsFilter
	 */
	public void setTermsFilter(TermsFilter termsFilter) {
		this.termsFilter = termsFilter;
	}

	/**
	 * Print the collection of terms-presenceCount
	 * 
	 */
	public void printTerms() {
		Enumeration k = termsEncoder.getTerms().keys();
		while (k.hasMoreElements()) {
			String key = (String) k.nextElement();
			System.out.println(key + " : "
					+ ((Integer) termsEncoder.getTerms().get(key)).intValue());
		}
	}

}
