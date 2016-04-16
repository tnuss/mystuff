package com.orcasdev.service;

import infra.ApplicationProperties;
import infra.GenY4mmdd;

import java.util.Properties;

import com.orcasdev.dao.DayDataListStore;
import com.orcasdev.equity.DayDataList;
import com.orcasdev.equity.EquityList;
import com.orcasdev.equity.EquityType;
import com.orcasdev.equity.StorageType;

public class RunScrapper {

	static final String SP100 = "SP100";

	private DayDataRequestService dDRService = null;
	private ApplicationProperties ap ;
	private String testDay = null ; //"20141003";

	private ApplContext parserContext;

	private DayDataList dayDataList;
	private EquityList equityList;
	private DayDataListStore ddLStore;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("RunScrapper");
		
		RunScrapper rS = new RunScrapper();
		rS.setUpCreateDataSet();
		// >> tn Setup and call DayDataRequestSevice()

	}

	public void setUpCreateDataSet() {
		
		ap = ApplicationProperties.getApplicationProperties(null);
		dDRService = new DayDataRequestService();
		dDRService.initService(ap, SP100);
		GenY4mmdd yyyyMMdd = new GenY4mmdd();
		yyyyMMdd.setCurrGY4mmddDates();
		testDay = yyyyMMdd.getY4mmdd_noDash();

		parserContext = dDRService.getParserContext(SP100);
		Properties pContextProps = parserContext.getGroupProperties();
		
		String listName = pContextProps.getProperty("Name");
		String fullFName = parserContext.getGroupFullFName(); 
	
		equityList = new EquityList(listName,EquityType.STOCK,StorageType.FILE_XML);
		
		equityList.loadEquitiesFromFile(fullFName) ;

		// NOTE -- createDayDataSet creates an equitylist as well!!!!!
		// -->>> true means to use test data naming conventions
		dDRService.createDayDataSet(parserContext, testDay, true);
		dayDataList = dDRService.getDayDataSet(SP100);

		ddLStore = new DayDataListStore(dayDataList,equityList,parserContext);
		
		int errNum = ddLStore.saveDayData();
		String status = ddLStore.interpretSaveDDReturnVal(errNum) ;

		System.out.println("DayDataStore Status: " + status);

	}

}
