package infra;

import java.io.*;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xerces.jaxp.DocumentBuilderImpl;
import org.apache.xerces.parsers.DOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLParser {

    /**
     * @param args
     */
    public static void main(String[] args) {
        parseXml2("D:\\javastuff\\jdom\\samples\\catalog.xml");
        try {
	        Document doc = getXmlDoc("D:\\javastuff\\jdom\\samples\\catalog.xml");
        } catch (Exception e) {
	       
	        e.printStackTrace();
        }
    
        
    }

    public static void parseXml2(String xmlFile) {
        DOMParser parser = new DOMParser();

        try {
        	File f = new File(xmlFile); 
        	InputStream inStream = new FileInputStream(f);
        	
        	BufferedInputStream bInputStream = new BufferedInputStream(inStream);        
        	
        	InputSource iS = new InputSource(bInputStream);
        	//iS.setByteStream()
            //parser.parse(new InputSource(new URL(URL).openStream()));
            parser.parse(iS);
            Document doc = parser.getDocument();


            NodeList nodeList = doc.getElementsByTagName("composition");
            for (int i = 0; i < nodeList.getLength(); i++) {
                System.out.print("Composition "+(i+1));
                Node n = nodeList.item(i);
                NamedNodeMap m = n.getAttributes();
                System.out.print(" Name: "+m.getNamedItem("composer").getTextContent());
                //System.out.print(" Type: "+m.getNamedItem("Type").getTextContent());
                Node actualNode = n.getFirstChild();
                if (actualNode != null) {
                    System.out.println(" "+actualNode.getNodeValue());
                } else {
                    System.out.println(" ");                    
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

      
//    public Document getXmlDoc(String xmlName) throws EpiException {
    public static Document getXmlDoc(String xmlName)
     throws Exception {

        Document doc = null;
        //String xmlPath = "D:\\javastuff\\jdom\\samples\\catalog.xml";
    	String xmlFile = xmlName;

    	try {

        	File f = new File(xmlFile); 
        	InputStream aXml = new FileInputStream(f);
    		
            //InputStream aXml = getClass().getResourceAsStream(xmlFile);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactoryImpl.newInstance();

            DocumentBuilderImpl documentBuilder = (DocumentBuilderImpl) documentBuilderFactory.newDocumentBuilder();

            doc = documentBuilder.parse(aXml);
            return doc;

        } catch (Exception e) {
             // logger.error("Failed to get XML doc: " + xmlName, e);
        	throw new Exception(e);
            //throw new EpiException(e);
        }

  }
  

    
    
}
