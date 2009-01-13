package tesis.parser.test;

import junit.framework.TestCase;
import tesis.parser.ConfigParser;

/**
 * Test suite for ConfigParserTest class
 * 
 * @author nicotourne
 * 
 */
public class ConfigParserTest extends TestCase {

	// Run test
	public static void test() {

		ConfigParser.parse();

		if (ConfigParser.getDataFile() == null) {
			fail("dataFile property is null");
		}

		if (ConfigParser.getDestFile() == null) {
			fail("dataFile property is null");
		}

		if (ConfigParser.getCsvColumns() == null) {
			fail("csvColumns property is null");
		}
		
		if (ConfigParser.getDocsMax() == 0) {
			fail("docsMax property is 0");
		}

	}

}
