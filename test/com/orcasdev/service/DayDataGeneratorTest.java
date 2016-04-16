package com.orcasdev.service;

import static org.junit.Assert.*;

import java.util.Properties;

import infra.ApplicationProperties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.orcasdev.equity.DayDataAdaptor;
import com.orcasdev.equity.DayDataList;
import com.orcasdev.equity.EquityList;
import com.orcasdev.equity.EquityType;
import com.orcasdev.equity.StorageType;

public class DayDataGeneratorTest {

	Logger logger = Logger.getLogger(this.getClass());

	ApplContext pContext ;
	String grpName = "SP100";
	ApplicationProperties ap = null;
	DayDataGenerator thisDDGen ;
	EquityList eL;
	String fullELFileName ;
	String fileDayDataName ;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		ap = ApplicationProperties.getApplicationProperties(null);
		pContext = new ApplContext(grpName,ap);
		String rDir = ap.getResourcesDir();
		String absDir = ap.getApplAbsolutePath();
		String fileSep = ap.getApplFileSeparator() ;
		String dDir = ap.getDataDir(false);
		String eLFileName = pContext.groupProps.getProperty("EquityListFileName");
		
		fullELFileName = absDir + fileSep + dDir + fileSep + eLFileName;
		eL= new EquityList("SP100",EquityType.STOCK,StorageType.FILE_XML);
		eL.loadEquitiesFromFile(fullELFileName);
		
		String yyyymmdd = "20141003";
		Boolean isTest = true;
		pContext.setTimeDependences(yyyymmdd, isTest);
		fileDayDataName = pContext.getDayDataFileName();

		thisDDGen = new DayDataGenerator(eL, fileDayDataName) ;
		thisDDGen.setParserContextEnvironment(pContext);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDayDataGenerator() {
		
		assertNotNull(thisDDGen);
		
	}

	@Test
	public void testSetParserContextEnvironment() {
		Properties props = thisDDGen.getGeneratorProps();
		assertTrue(props.size()>0);
	}

	@Test
	public void testGenerateStockList() {
		
		thisDDGen.generateStockList();
		DayDataList ddList = thisDDGen.getDayDataList();		
		assertNotNull(ddList);
		
		DayDataAdaptor ddDTO = ddList.getDayDataDTO("GE") ;
		assertNotNull(ddDTO);
	
		assertTrue(ddDTO.getLast() >= 0);
		
	}

}
