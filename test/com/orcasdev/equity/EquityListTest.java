package com.orcasdev.equity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import infra.ApplicationProperties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pwd.FilePwdDateDAO;

public class EquityListTest {
	
    Logger logger = Logger.getLogger(EquityListTest.class.getClass());
	ApplicationProperties ap = null;
	String fullEquityFileName = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		logger.info("EQUITY LIST TEST SETUP");
		ap = ApplicationProperties.getApplicationProperties(null);
		String rDir = ap.getResourcesDir();
		String absDir = ap.getApplAbsolutePath();
		String fileSep = ap.getApplFileSeparator() ;
		String dDir = ap.getDataDir(false);
		
		fullEquityFileName = absDir + fileSep + dDir + fileSep + "SP100-0709.xml";
		//System.out.println("");
	    //System.out.println("Resources Dir:" + absDir + fileSep + rDir);
		//System.out.println("Data Dir:" + absDir + fileSep + dDir);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEquityStockList() {

		//System.out.println("EquityType: " + EquityType.STOCK.toString());
		
		EquityList eL= new EquityList("SP100",EquityType.STOCK,StorageType.FILE_XML);

		//String fileEListName = "SP100-0709.xml";
		
		eL.loadEquitiesFromFile(fullEquityFileName) ;

		String symbol ="GOOG";
		Equity stock =  eL.getEquity(symbol);
		
		assertTrue("Google Inc".equalsIgnoreCase(stock.getName()));
		
	}

	@Test
	public void testGetEquityListHashMap() {

		EquityList eL= new EquityList("SP100",EquityType.STOCK,StorageType.FILE_XML);

		//String fileEListName = "SP100-0709.xml";
		
		eL.loadEquitiesFromFile(fullEquityFileName) ;
			
		HashMap<String, Equity> hmEL = eL.getEquityListHashMap();
		assertTrue(hmEL.size()==100);
		
		
		ArrayList <String> aELNames = eL.getEquityListKeys();
		assertTrue(aELNames.size()==100);
		
		boolean bf = false;
		for (String string : aELNames) {
			if (string.equalsIgnoreCase("GOOG"))
			   bf = true;
		}
		
		assertTrue("Did Not Find Key Name in Array:", bf);
		
		
	}
}
