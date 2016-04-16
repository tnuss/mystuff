package com.orcasdev.dao;

import static org.junit.Assert.*;

import java.util.Properties;

import infra.ApplicationProperties;
import infra.GenY4mmdd;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.orcasdev.equity.DayDataAdaptor;
import com.orcasdev.equity.DayDataDTO;
import com.orcasdev.equity.DayDataList;
import com.orcasdev.equity.Equity;
import com.orcasdev.equity.EquityList;
import com.orcasdev.equity.EquityType;
import com.orcasdev.equity.StorageType;
import com.orcasdev.service.DayDataRequestService;
import com.orcasdev.service.ApplContext;

public class DayDataListStoreTest {

	static final String SP100 = "SP100";

	DayDataRequestService dDRService = null;
	ApplicationProperties ap ;
	String testDay = null ; //"20141003";

	private ApplContext parserContext;

	private DayDataList dayDataList;
	private EquityList equityList;
	
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
	public void testSaveDayData() {

		setUpCreateDataSet();
		
		assertTrue("Data List > 0: " + dayDataList.size(), dayDataList.size()>0);
		
		Equity testEquity = null;
		int size = equityList.getEquityListKeys().size();
		String key = equityList.getEquityListKeys().get(size-1);
		testEquity = equityList.getEquity(key);
		assertTrue("GetLastKey",testEquity!=null);

		DayDataAdaptor ddDTOAdapt = dayDataList.getDayDataDTO(key);
		assertTrue("LastKey - DayDataAdaptor",ddDTOAdapt!=null);
		
		key = "GE";
		testEquity = null;
		testEquity = equityList.getEquity(key);
		assertTrue("Get GE Key",testEquity!=null);

		ddDTOAdapt = dayDataList.getDayDataDTO(key);
		assertTrue("GE Key:DayDataAdaptor",ddDTOAdapt!=null);

		DayDataListStore ddLStore = 
			new DayDataListStore(dayDataList,equityList,parserContext);
		
		int errNum = ddLStore.saveDayData();
		String status = ddLStore.interpretSaveDDReturnVal(errNum) ;
		System.out.println("DayDataStore Status: " + status);
		assertTrue(errNum==0);
		
//		_______________N O T E____________________________________
//		     USING IJ can do: run 'connfirst.ij' to connect to db +++++++++++++++++
//		_______________N O T E____________________________________

	}

	public void setUpCreateDataSet() {
		
		parserContext = dDRService.getParserContext(SP100);
		Properties pContextProps = parserContext.getGroupProperties();
		
		String listName = pContextProps.getProperty("Name");
		String fullFName = parserContext.getGroupFullFName(); 
	
		equityList = new EquityList(listName,EquityType.STOCK,StorageType.FILE_XML);
		
		equityList.loadEquitiesFromFile(fullFName) ;

		// NOTE -- createDayDataSet creates an equitylist as well!!!!!
		dDRService.createDayDataSet(parserContext, testDay, true);
		dayDataList = dDRService.getDayDataSet(SP100);
		
	}
	
	
}
