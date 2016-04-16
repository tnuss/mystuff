package com.orcasdev.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import infra.ApplicationProperties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.orcasdev.equity.Equity;
import com.orcasdev.equity.EquityList;
import com.orcasdev.equity.EquityListTest;
import com.orcasdev.equity.EquityType;
import com.orcasdev.equity.StorageType;

public class EquityHTMLParserTest {

	EquityHTMLParser eHtmlParser = null;
	
    Logger logger = Logger.getLogger(EquityListTest.class.getClass());
	ApplicationProperties ap = null;
	String fullEquityFileName = null;
	String equityURL = null;
	
	String fullQuotesFileName ;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	
		eHtmlParser = new EquityHTMLParser();
		logger.info("Equity List HTML TEST SETUP");
		ap = ApplicationProperties.getApplicationProperties(null);
		String rDir = ap.getResourcesDir();
		String absDir = ap.getApplAbsolutePath();
		String fileSep = ap.getApplFileSeparator() ;
		String dataDir = ap.getDataDir(false);
		String testDir = ap.getTestDataDir(false);
		
		fullEquityFileName = absDir + fileSep + dataDir + fileSep + "SP100-0709.xml";
		equityURL = "http://www.google.com";

		fullQuotesFileName = absDir + fileSep + testDir + fileSep + "q-sp100-20141003.html";
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void setEquityListTest() {

		EquityList eL= new EquityList("SP100",EquityType.STOCK,StorageType.FILE_XML);
		eL.loadEquitiesFromFile(fullEquityFileName) ;

		String symbol ="GOOG";
		Equity stock =  eL.getEquity(symbol);
		assertTrue("Google Inc".equalsIgnoreCase(stock.getName()));
		
		eHtmlParser.setEquityList(eL);
		EquityList el2 = eHtmlParser.getEquityList();
		assertTrue("Google Inc".equalsIgnoreCase(el2.getEquity(symbol).getName()));

	}

	@Test
	public void setEquityListDataTest() {
		
		StringBuffer sb = setUpDataForTest();
		assertNotNull(sb);

	}


	@Test
	public void getMatchFromDataTest() {

		StringBuffer sb = setUpDataForTest();
		assertNotNull(sb);
		
		String prefix = "dt1_";
		String suffix = "_last";
		String keySymbol = "GOOG";
		String searchFor = prefix + keySymbol + suffix;
		String regex1st = "(" + searchFor + ".*<\\/td>)" ;	
		//dt1_GOOG_last.*&lt;\/td&gt;
		//String regex1st = searchFor + ".*&lt;\\/td&gt;" ;	
		//String regex1st = "(" + searchFor + ".*&lt;\\/td&gt;){1}" ;	
		//String regex2nd = ">.+<\\/td>";
		//String regex2nd = "&gt;<\\/span>.+&lt;\\/td&gt;";
		//&gt;<\/span>.*&lt;\/td&gt;
//		String regex2nd = "&gt;<\\/span>.+&lt;\\/td&gt;";
//		String stDelimit = "span>";
//		String endDelimit = "<span";
		//    >\d+,?\.?\d+<
		String regex2nd = ">\\d+,?\\.?\\d+<";
		String stDelimit = ">";
		String endDelimit = "<";
		String testLastValue = "544.40";
		
		ArrayList <StringBuffer> match = eHtmlParser.getDataMatchFromRegEx(regex1st) ;
		assertNotNull(match);	
		String sValue;
		
		for (StringBuffer stringBuffer : match) {
			
			assertTrue(stringBuffer.toString().indexOf(searchFor)>=0);
			logger.info("RegEx1:RegEx2: " + regex1st + ": " + regex2nd);
			sValue = eHtmlParser.getStringValue(stringBuffer, regex2nd, stDelimit, endDelimit);
			logger.info("sValue for " + searchFor + ": " + sValue);
			assertTrue("Vals don't match:", testLastValue.equals(sValue));
		}

	}
	
	public StringBuffer setUpDataForTest() {
		
		EquityList eL= new EquityList("SP100",EquityType.STOCK,StorageType.FILE_XML);
		eL.loadEquitiesFromFile(fullEquityFileName) ;
		eHtmlParser.setEquityList(eL);

		eHtmlParser.setParceFile(fullQuotesFileName);
		
		StringBuffer sb = eHtmlParser.readFileDataInto();
		
		return sb;
	}
	
}
