package com.orcasdev.equity;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DayDataListTest {

	DayDataList dDList = null;
	String listName = "SP100-DayData";
	StorageType storageType = StorageType.FILE_XML;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		dDList = new DayDataList("SP100-DayData", storageType);
		dDList.setListKey("XXXX");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddToList() {
		
		DayDataAdaptor dataDTO = new DayDataAdaptor(EquityType.STOCK, new Stock().getDfQuote());
		double last = 2.46;
		double hi = 3.02;
		double low = 1.97;
		int volume = 3010123;
		double change = -0.82;
		String key = "20140901";
		
		dataDTO.setDateString(key);
		dataDTO.setDayDataQuote(hi, low, last, change, volume);
		
		dDList.add(key, dataDTO);
		assertTrue(dDList.size()==1);
		
		dataDTO = dDList.getDayDataDTO(key);
		
		assertNotNull(dataDTO);
		
		System.out.println(dataDTO.toString());
		
	}
	
	@Test
	public void testGetListKey() {
		
		String key = "ZKEYZ";
		dDList.setListKey(key);
		assertTrue(key.equals(dDList.getListKey()));

	}

	
}  // eoc
