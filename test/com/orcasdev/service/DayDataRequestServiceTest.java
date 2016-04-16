package com.orcasdev.service;

import static org.junit.Assert.*;
import infra.ApplicationProperties;
import infra.GenY4mmdd;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.orcasdev.dao.DayDataListStore;
import com.orcasdev.equity.DayDataAdaptor;
import com.orcasdev.equity.DayDataList;
import com.orcasdev.equity.EquityList;
import com.orcasdev.equity.EquityType;

public class DayDataRequestServiceTest {
	
	static final String SP100 = "SP100";

	DayDataRequestService dDRService = null;
	ApplicationProperties ap ;
	String testDay = null ; //"20141003";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ap = ApplicationProperties.getApplicationProperties(null);
		dDRService = new DayDataRequestService();
		dDRService.initService(ap, SP100);
		GenY4mmdd yyyyMMdd = new GenY4mmdd();
		yyyyMMdd.setCurrGY4mmddDates();
		testDay = yyyyMMdd.getY4mmdd_noDash();		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateDayDataFile() {
		ApplContext parserContext = dDRService.getParserContext(SP100);
		//dDRService.createDayDataFile(parserContext,testDay,true);
		
	}
	
	@Test
	public void testGetHTMLParserAdaptor() {
		
		ArrayList <String> groupNames = dDRService.getGroupNames();
		assertTrue(groupNames.size()>0);
		
		ApplContext parserContext = dDRService.getParserContext(SP100);
		assertNotNull("ParserContext is Null", parserContext);

		Properties props = parserContext.getGroupProperties();
		assertNotNull(props.get("NumberLevelsRegEx"));
	}
	
	@Test
	public void testCreateDataSet() {
		
		ApplContext parserContext = dDRService.getParserContext(SP100);
		//String testFName = dDRService.getDayDataFileName(this.testDay, this.SP100.toLowerCase());

		dDRService.createDayDataSet(parserContext, testDay, true);
		
		DayDataList dayDataList = dDRService.getDayDataSet(SP100);
		
		assertTrue("GE is Not Null",dayDataList.getDayDataDTO("GE")!=null);
		
//		DayDataListStore ddLStore = new DayDataListStore(dayDataList,equityList,parserContext);
//		
//		int errNum = ddLStore.saveDayData();
//		String status = ddLStore.interpretSaveDDReturnVal(errNum) ;
//		System.out.println("DayDataStore Status: " + status);
//		assertTrue(errNum==0);	
		
		//assertTrue(" -- PUT TO DB NOW??? ---",false);
		//fail();
	}
	
	@Test
	public void testSetDataDate() {
	
		GenY4mmdd genY4Date = new GenY4mmdd();
		genY4Date.setCurrGY4mmddDates();
		String testDay = genY4Date.getY4mmdd_noDash();
//		stockDayData.setDateString(TEST_DAY);
		System.out.println("DayDataRequestTest Today is:" + testDay);
		assertNotNull(testDay);
		
	}
	
	@Test
	public void testLoadEquityList() {
		
		ApplContext parserContext = dDRService.getParserContext(SP100);
		EquityList eL = dDRService.loadEquityList(parserContext);
		
		ArrayList <String> keys = eL.getEquityListKeys();
		Collections.sort(keys);
		for (int i=0;i<keys.size();i++) {
			System.out.println((i+1) + ": " + keys.get(i));
		}
		
		
		assertEquals(100,eL.getSize());
		
	}	

}
