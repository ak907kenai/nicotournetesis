import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.net.BIFReader;
 
public class WekaTest {
  public static void main(String[] args) throws Exception {
   BayesNet network = new BayesNet();
   BIFReader reader = new BIFReader();    
   network = reader.processFile("D:\\Facultad\\Tesis\\nicotournetesis_svn\\datos\\datos.xml");
  }
 }