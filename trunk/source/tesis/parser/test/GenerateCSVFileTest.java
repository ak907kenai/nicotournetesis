package tesis.parser.test;

import java.util.List;

import junit.framework.TestCase;
import tesis.parser.GenerateCSVFile;

/**
 * Test suite for GenerateCSVFile class
 * 
 * @author nicotourne
 * 
 */
public class GenerateCSVFileTest extends TestCase {

	// Test if the documents has been parsed
	public void testParsedDocuments() {

		List documents = GenerateCSVFile.getDocuments();

		if (documents == null)
			fail();

	}

	// Test if it can generate the csv file
	public void testGenerateCSVFile() {

		GenerateCSVFile.generateCSVFile();

	}

}
