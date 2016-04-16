package com.orcasdev.equity;

import infra.ApplicationProperties;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import pwd.FilePwdDateDAO;

public class EquityList {

	String lFileName = null;
	StorageType storageType = null;
	EquityType equityType = null ;
	String listName = null;
	HashMap <String,Equity> equityList = new HashMap<String,Equity>(); 	
	
    // logger here --- root class has to setup logger...
    Logger logger = Logger.getLogger(this.getClass());

	public EquityList(String listName, EquityType equityType, StorageType storeType) {
	        this.equityType = equityType;
	        this.storageType = storeType;
	        this.listName = listName;
	}

	public void setListName(String lName){
		listName = lName;
	}
	
	public int getSize() {
		return equityList.size();
	}
	
	/**
	 * 
	 * @returns arraylist of keys from object's hashmap
	 */
		
	public ArrayList<String> getEquityListKeys(){
		
		HashMap <String,Equity> hmEL = getEquityListHashMap();
		ArrayList <String> aELNames = new ArrayList<String>();
		
		
		//hmEL.keySet().addAll(aELNames);
		aELNames.addAll(hmEL.keySet());
		return aELNames;
		
	}

	/**
	 * 
	 * @returns a cloned hashmap of the object's hashmap
	 */
		
	HashMap<String,Equity> getEquityListHashMap(){
		HashMap <String,Equity> hmEL = (HashMap<String,Equity>) equityList.clone();
		return hmEL;
	}
	
	public void loadEquitiesFromFile(String fileName) {
		lFileName = fileName;
		logger.info("Load equities filename: " + fileName);
		
        DOMParser parser = new DOMParser();

        Document doc = null;
        
        try {
	    	
        	//File f = new File("C:\\amyapps\\eclipse-ws\\kepler-ws\\Work0603\\resources\\SP100-0709.xml"); 
        	File f = new File(fileName); 
	    	InputStream inStream = new FileInputStream(f);
	    	
	    	BufferedInputStream bInputStream = new BufferedInputStream(inStream);        
	    	
	    	InputSource iS = new InputSource(bInputStream);
	        parser.parse(iS);
	        doc = parser.getDocument();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        NodeList nodeList = doc.getElementsByTagName(equityType.toString());

// >> tn builder factory??  if (this.equityType == EquityType.STOCK);
        
        StockBuilder sb = new StockBuilder();
       
        for (int i = 0; i < nodeList.getLength(); i++) {
           // System.out.print("Node "+(i+1));
            Node n = nodeList.item(i);

           // System.out.println("\nCurrent Element :" + n.getNodeName());
            
    		if (n.getNodeType() == Node.ELEMENT_NODE) {
     
    			Element eElement = (Element) n;

    			String tmpSymbol = eElement.getElementsByTagName("Symbol").item(0).getTextContent();
    			sb.initNewStock();
    			sb.setClassVar("Name", eElement.getElementsByTagName("CompanyName").item(0).getTextContent());
    			sb.setClassVar("Symbol",tmpSymbol );
    			sb.setClassVar("Sector", eElement.getElementsByTagName("Sector").item(0).getTextContent());
    			if (sb.getStock()!=null)
    				equityList.put(tmpSymbol, sb.getStock());
    			
    			logger.debug("Attribute Symbol: " + eElement.getAttribute("SYMBOL"));
    			logger.debug("Name: " 
    			    +  eElement.getElementsByTagName("CompanyName").item(0).getTextContent());
    			logger.debug("Symbol: " 
    			    + eElement.getElementsByTagName("Symbol").item(0).getTextContent());
    		}            
        }
	}
	
	public String setEquityFileName(Properties pContextProps) {

		String listName = pContextProps.getProperty("Name");
		String fName = pContextProps.getProperty("EquityListFileName");
		String fullFName = "FRED";
//		String fullFName = ap.getApplAbsolutePath() + ap.getApplFileSeparator() + 
	//			         ap.getDataDir(false) + ap.getApplFileSeparator() + fName;

		return fullFName;
	}
	

	public Equity getEquity(String symbol) { 
	
		if (equityList.containsKey(symbol))
			return equityList.get(symbol);
		else	
		    return null;
	}

}  // eoc
