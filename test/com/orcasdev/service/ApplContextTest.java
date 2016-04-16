package com.orcasdev.service;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import infra.ApplicationProperties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.orcasdev.equity.EquityListTest;

public class ApplContextTest {

//  <td id="dt1_AAPL_last" align="right" class="ds_last qb_shad" nowrap="nowrap">96.13</td>
//  <td id="dt1_AAPL_change" align="right" class="ds_change qb_shad" nowrap="nowrap"><span class="qb_up">+0.53</span></td>
//  <td id="dt1_AAPL_pctchange" align="right" class="ds_pctchange qb_shad" nowrap="nowrap"><span class="qb_up">+0.55%</span></td>
//  <td id="dt1_AAPL_high" align="right" class="ds_high qb_shad" nowrap="nowrap">96.62</td>
//  <td id="dt1_AAPL_low" align="right" class="ds_low qb_shad" nowrap="nowrap">94.81</td>
//  <td id="dt1_AAPL_volume" align="right" class="ds_volume qb_shad" nowrap="nowrap">48,511,298</td>
//  <td id="dt1_AAPL_displaytime" align="right" class="ds_displaytime qb_shad" nowrap="nowrap">08/01/14</td>
	
//	String dataDateString ;
//	double last = 0;
//	double hi = 0;
//	double low = 0;
//	double change = 0;
//	int volume = 0;
//	DecimalFormat df = null;
//	EquityType dataType = null;

	static final String SP100 = "SP100";
	static final String FILENAME_COMP_STRING = "q-sp100-20140801.html";
	static final String TEST_DAY = "20140801";
	

	Logger logger = Logger.getLogger(ApplContextTest.class.getClass());
	ApplicationProperties ap = null;
	ApplContext parserContext ;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
 
		ap = ApplicationProperties.getApplicationProperties(null);
		parserContext = new ApplContext(SP100,ap);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateGroupProperties() {
		
		String groupName = SP100;
		ApplContext parserContext = new ApplContext(groupName,ap);
		assertNotNull("ParserContext Not Created", parserContext);
		
		Properties contextProps = parserContext.getGroupProperties();
		assertNotNull("ParserContext Properties is Null", contextProps);
		
		assertTrue(groupName.equals(contextProps.getProperty("Name")));
		
	}
	
	@Test
	public void testGetDayDataFileName()
	{
		parserContext.setTimeDependences(TEST_DAY,true);

		// testFName is fully qualified..ie. with directories
		String testFName = parserContext.getDayDataFileName();
		System.out.println(testFName);
		assertTrue(testFName.indexOf(FILENAME_COMP_STRING)!=-1);
		//fail("not implemented");
	}
	
	@Test
	public void testGetEquityDayDataFileName() {
		
		// isBackup false
		String fileName = parserContext.getTestDir(false) + "/GE_daydata.xml";
		
		// isBackup false, isTest=true
		String equityFileName = parserContext.getEquityDayDataFileName("GE", false, true);
		System.out.println("Test EquityFileName: " + fileName + ":" + equityFileName);
		assertTrue(fileName.equals(equityFileName));

		// isBackup true
		fileName = parserContext.getTestDir(true) + "/GE_daydata_bk.xml";
		equityFileName = parserContext.getEquityDayDataFileName("GE", true, true);
		System.out.println("Test EquityFileName: " + fileName + ":" + equityFileName);
		assertTrue(fileName.equals(equityFileName));
	
		// backup false
		fileName = parserContext.getDataDir(false) + "/GE_daydata.xml";
		equityFileName = parserContext.getEquityDayDataFileName("GE", false, false);
		System.out.println("Test EquityFileName: " + fileName + ":" + equityFileName);
		assertTrue(fileName.equals(equityFileName));
		// backup true
		fileName = parserContext.getDataDir(true) + "/GE_daydata_bk.xml";
		equityFileName = parserContext.getEquityDayDataFileName("GE", true, false);
		System.out.println("Test EquityFileName: " + fileName + ":" + equityFileName);
		assertTrue(fileName.equals(equityFileName));

	}

	@Test
	public void testIsTimeToGetData() {
		parserContext.setTimeDependences(TEST_DAY,true);
		int startHour = 17;
		int endHour = 23;
		
		
	}

	@Test
	public void testSetRunBetweenTimes() {
		
		parserContext.setTimeDependences(TEST_DAY,true);
		int startHour = 17;
		int endHour = 23;
		int otherHour = 10;
		boolean bf = false;
		
		Calendar dateTime = Calendar.getInstance();
		dateTime.set(Calendar.HOUR_OF_DAY, startHour);
		dateTime.set(Calendar.DAY_OF_WEEK, 2);
		bf = parserContext.isTimeToGetData(dateTime);
		assertTrue("Hour is " + startHour,bf);

		dateTime.set(Calendar.HOUR_OF_DAY, endHour);
		dateTime.set(Calendar.DAY_OF_WEEK, 2);
		bf = parserContext.isTimeToGetData(dateTime);
		assertTrue("Hour is " + endHour,bf);

		dateTime.set(Calendar.HOUR_OF_DAY, endHour);
		dateTime.set(Calendar.DAY_OF_WEEK, 1);
		bf = parserContext.isTimeToGetData(dateTime);
		assertFalse("Hour is " + endHour,bf);
		
		dateTime.set(Calendar.HOUR_OF_DAY, endHour);
		dateTime.set(Calendar.DAY_OF_WEEK, 7);
		bf = parserContext.isTimeToGetData(dateTime);
		assertFalse("Hour is " + endHour,bf);
		
		dateTime.set(Calendar.HOUR_OF_DAY, otherHour);
		dateTime.set(Calendar.DAY_OF_WEEK, 6);
		bf = parserContext.isTimeToGetData(dateTime);
		assertFalse("Hour is " + otherHour,bf);
		
	}

}
