import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ParseLigaXmlTest {

  public static void main(String[] args) {
     try {
        SAXBuilder builder=new SAXBuilder(false); 
        //usar el parser Xerces y no queremos 
        //que valide el documento
        Document doc=builder.build("data\\liga.xml");
        //construyo el arbol en memoria desde el fichero
        // que se lo pasaré por parametro.
        Element raiz=doc.getRootElement();
        //cojo el elemento raiz
        System.out.println("La liga es de tipo:"+
                    raiz.getAttributeValue("tipo"));
        //todos los hijos que tengan como nombre plantilla
        List equipos=raiz.getChildren("equipo");
        System.out.println("Formada por:"+equipos.size()+" equipos");
        Iterator i = equipos.iterator();
        while (i.hasNext()){
            Element e= (Element)i.next();
            //primer hijo que tenga como nombre club
            Element club =e.getChild("club"); 
            List plantilla=e.getChildren("plantilla"); 
            System.out.println
                          (club.getText()+":"+"valoracion="+
                           club.getAttributeValue("valoracion")+","+
                           "ciudad="+club.getAttributeValue("ciudad")+","+
                           "formada por:"+plantilla.size()+"jugadores");
             if (e.getChildren("conextranjeros").size()==0)
              System.out.println("No tiene extranjeros");
             else  System.out.println("Tiene extranjeros");

        }
        // Dejamos de mano del lector el sacar el nombre 
        //de los arbitros, animate!!
     }catch (Exception e){
        e.printStackTrace();
     }
  }
}