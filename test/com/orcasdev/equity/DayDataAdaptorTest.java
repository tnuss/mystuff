package com.orcasdev.equity;

import static org.junit.Assert.*;
import infra.GenY4mmdd;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tryout.SimpleDTO;

import com.google.gson.Gson;

public class DayDataAdaptorTest {

	Stock stock = null;
	DayDataAdaptor stockDayData = null ;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		//stock = new Stock();
		//stockDayData = new DayDataAdaptor(stock.getDfQuote());
		stockDayData = new DayDataAdaptor(EquityType.STOCK,new Stock().getDfQuote());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetLastByString() {
	
		double last = 2.46;
		String strLast = "2.46";
		stockDayData.setLast(strLast);
		
		assertTrue(2.46==stockDayData.getLast());
	}
	
	@Test
	public void testGetDayQuotes() {
	
		double last = 2.46;
		double hi = 3.02;
		double low = 1.97;
		int volume = 3010123;
		double change = -0.82;
		
		stockDayData.setDayDataQuote(hi,low,last,change, volume);
		
		assertEquals("hi:",hi,stockDayData.getHi(), 0.01);
		assertEquals("low:",low,stockDayData.getLow(), 0.01);
		assertEquals("last:",last,stockDayData.getLast(), 0.01);
		assertEquals("volume:",volume,stockDayData.getVolume());
		assertEquals("change:",change,stockDayData.getChange(), 0.01);
		
	}
	
	@Test
	public void testGetDayQuotesStrings() {
		
		double last = 2.46;
		double hi = 3.02;
		double low = 1.97;
		int volume = 3010123;
		double change = -0.82;
		
		stockDayData.setDayDataQuote(hi,low,last,change, volume);

		assertEquals("hi:","3.02", stockDayData.getHiString());
		assertEquals("low:","1.97",stockDayData.getLowString());
		assertEquals("last:","2.46",stockDayData.getLastString());
		assertEquals("change:","-0.82",stockDayData.getChangeString());
		
	}
	
	@Test
	public void testGetDate() {
		
		GenY4mmdd genY4Date = new GenY4mmdd();
		genY4Date.setCurrGY4mmddDates();
		String testDay = genY4Date.getY4mmdd_noDash();
		stockDayData.setDateString(testDay);
		assertNotNull(stockDayData);
		System.out.println("Test Current DATE:"+testDay);
		
		assertTrue("Dates Should Equal:" + testDay, stockDayData.getDateString().equals(testDay));

	}

	@Test
	public void getJSONStringDTOTest() {
		
		double last = 2.46;
		double hi = 3.02;
		double low = 1.97;
		double change = -0.82;
		int volume = 3010123;

		GenY4mmdd genY4Date = new GenY4mmdd();
		genY4Date.setCurrGY4mmddDates();
		String testDay = genY4Date.getY4mmdd_noDash();
		
		stockDayData.setDateString(testDay);
		stockDayData.setDayDataQuote(hi,low,last,change,volume);

		System.out.println(stockDayData.toString());

		String gsonDTOString = stockDayData.getGSONStringDTO();
		System.out.println("GSON stockDayData: " + gsonDTOString);

		stockDayData.setDTOFromGSONString(gsonDTOString);
		String gsonDTOString2 = stockDayData.getGSONStringDTO();
		
		assertTrue(gsonDTOString.equals(gsonDTOString2));
		
	}
	
	@Test
	public void testToString() {
	
		double last = 2.46;
		double hi = 3.02;
		double low = 1.97;
		double change = -0.82;
		int volume = 3010123;

		GenY4mmdd genY4Date = new GenY4mmdd();
		genY4Date.setCurrGY4mmddDates();
		String testDay = genY4Date.getY4mmdd_noDash();
		stockDayData.setDateString(testDay);

		stockDayData.setDayDataQuote(hi,low,last,change,volume);

		assertNotNull(stockDayData.toString());
		System.out.println(stockDayData.toString());
		
	}
	
}
