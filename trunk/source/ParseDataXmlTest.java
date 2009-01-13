import java.util.Iterator;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ParseDataXmlTest {

	public static void main(String[] args) {
		try {
			SAXBuilder builder = new SAXBuilder(false);
	
			// usar el parser Xerces y no queremos
			// que valide el documento
			Document doc = builder
					.build("data\\datos_small.xml");
	
			// construyo el arbol en memoria desde el fichero
			// que se lo pasaré por parametro.
			Element raiz = doc.getRootElement();

	        System.out.println("La liga es de tipo:"+
                    raiz.getAttributeValue("tipo"));			

	        // Obtengo los documentos
			List documents = raiz.getChildren("document");
			Iterator i = documents.iterator();

			int MAX = 100;
			int cursor = 1;
			
			while (i.hasNext() && cursor < MAX) {

				Element e = (Element) i.next();
				
				System.out.println(e.getAttribute("url").getValue());
				
				cursor++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
