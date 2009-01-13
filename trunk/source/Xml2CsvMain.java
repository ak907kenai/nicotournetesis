import java.io.File;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class Xml2CsvMain {

	public static void main(String[] args) throws Exception {
	TransformerFactory tfactory = TransformerFactory.newInstance();
	Transformer t = null;
	//t = tfactory.newTransformer(
	/*new StreamSource(new File(props.getString("dial.csv.xsl")))); 
	StringWriter sw = new StringWriter();
	StreamResult result = new StreamResult(sw);
	t.transform(new DOMSource(document), result);
	StringBuffer csv = sw.getBuffer();*/
	}
}
