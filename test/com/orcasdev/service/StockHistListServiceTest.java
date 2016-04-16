package com.orcasdev.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

//import com.google.gson.JsonObject;
import com.orcasdev.equity.DayDataAdaptor;
import com.orcasdev.equity.DayDataDTO;
import com.orcasdev.equity.EquityList;
import com.orcasdev.equity.EquityType;
import com.orcasdev.equity.Stock;
import com.orcasdev.equity.StorageType;

import infra.ApplicationProperties;

public class StockHistListServiceTest {
	
	static final String SP100 = "SP100";

	StockHistListService sHLService ;
	ApplicationProperties ap;
	ApplContext testContext;
	String grpName = SP100;
	String stkSymbol = "GE";
	
	@Before
	public void setUp() throws Exception {
		ap = ApplicationProperties.getApplicationProperties(null);
		testContext = new ApplContext(grpName,ap);
		sHLService = new  StockHistListService(ap, grpName);
	}

	@Test
	public void testStockHistListService() {
	
		assertNotNull("Service is null",sHLService);
	}
	
	@Test
	public void testGetSetOfHistories () {
		
		String type = "SIMPLE";
		String params = "{\"SIZE\":10}";
		String symbol = "GE";
		
		 JsonObject jsonParams = Json.createObjectBuilder()
	                .add("SIZE",10)
	                .add("YYZ", "CharsTest")
	                .build();

		 System.out.println("Json Stuff: " + jsonParams.toString());

		 ArrayList <DayDataDTO> groupDDDA[] = sHLService.getSetOfHistories(type, symbol, params) ;
		
		assertNotNull(groupDDDA[0]);
		
	}
	@Test
	public void testGetOneHistList() {
		
		ArrayList <DayDataDTO> dddA = sHLService.getOneHistList(stkSymbol);
		assertNotNull("ArrayList is Null", dddA);

		Stock stk = new Stock();
		if (dddA.size()>1) {
			
			DayDataDTO ddD = dddA.get(0);
			DayDataDTO ddD1 = dddA.get(dddA.size()-1);
	        DayDataAdaptor ddDA1 = 
	        		new DayDataAdaptor(EquityType.STOCK, stk.getDfQuote());
			DayDataAdaptor ddDA2 = 
					new DayDataAdaptor(EquityType.STOCK, stk.getDfQuote()); 		
			ddDA1.setDayDataDTO(ddD);
			ddDA2.setDayDataDTO(ddD1);
			
			if (ddDA1.getDataTS().equals(ddDA2.getDataTS())) {
				fail("Timestamps equal");
			}
		}
		else {
			fail("Array To Small");
		}
	}
	
	@Test
	public void TestGetHistListSymbols() {
		
		ArrayList <String> sSymbols = sHLService.getHistListSymbols();

		boolean good = false;
		for (int i=0;i<sSymbols.size();i++) {
			System.out.println("Symbol: "+ sSymbols.get(i));
			if (sSymbols.get(i).equalsIgnoreCase(stkSymbol)) {
				good = true;
			}
		}
		
		assertTrue("Symbol Not found",good);
	}

}
