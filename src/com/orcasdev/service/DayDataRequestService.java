package com.orcasdev.service;

import static org.junit.Assert.assertTrue;
import infra.ApplicationProperties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.orcasdev.comm.HTTPPageStringBuffer;
import com.orcasdev.dao.DayDataListStore;
import com.orcasdev.equity.DayDataList;
import com.orcasdev.equity.EquityList;
import com.orcasdev.equity.EquityType;
import com.orcasdev.equity.Stock;
import com.orcasdev.equity.StorageType;

public class DayDataRequestService {

	ApplicationProperties ap ;
    Logger logger = Logger.getLogger(DayDataRequestService.class.getClass());

	ArrayList<String> groupNames = new ArrayList<String>();
	HashMap <String,ApplContext> parserContexts = new HashMap<String, ApplContext>();
	private String rDir;
	private String absDir;
	private String fileSep;
	private String dataDir;
	private String testDir;
	private DayDataList dayDataList = null;
	private String dayDataURL ;
	
	public DayDataRequestService () {
	}
	
	/**
	 * 
	 * @param ap2 : application properties struct
	 * @param name : equity group name ex: SP100
	 */
	public void initService(ApplicationProperties ap2, String name) {

		if (ap2==null)
			ap = ApplicationProperties.getApplicationProperties(null);	
		else
			ap = ap2;
		
		if (name==null) 
			loadGroupNames();
		else
			groupNames.add(name);
	
		// >>> tn setup array of html parser adaptors??
		
		for (String name1 : groupNames) {
			parserContexts.put(name1, getParserContext(name));
		}

		rDir = ap.getResourcesDir();
		absDir = ap.getApplAbsolutePath();
		fileSep = ap.getApplFileSeparator();
		dataDir = ap.getDataDir(false);
		testDir = ap.getTestDataDir(false);

	}

	private void createDayDataFile(ApplContext parserContext, String yyyymmdd, boolean isTest) {

		HTTPPageStringBuffer httpBuffer = new HTTPPageStringBuffer();
		String httpUrl = parserContext.groupProps.getProperty("DayDataURL");
		httpBuffer.setURL(httpUrl);
		
		StringBuffer sBuffer = null;
		if (httpBuffer.openURLConn())
			if (httpBuffer.readURLHTTPContent())
				sBuffer = httpBuffer.getURLStringBuffer();
		
		if (sBuffer!=null) {
			parserContext.setTimeDependences(yyyymmdd, isTest);
			FileIOService fIOService = new FileIOService(parserContext);
			fIOService.writeDayDataFile(sBuffer, isTest);
		}
		else
		{
			logger.info("Couldn't retrieve URL Data Buffer: " + httpUrl);
		}
		//parserContext.getDayDataFileName();
	}
	
	public void createDayDataSet(ApplContext parserContext, String yyyymmdd, boolean isTest) {
		
		
		    // get GroupName equity list
		EquityList eL = loadEquityList(parserContext);
		
		   // get file/string file to be parsed
		parserContext.setTimeDependences(yyyymmdd, isTest);
		String dDataFName = parserContext.getDayDataFileName();
		eL.setListName(parserContext.getGroupName());
		
		//  create parsing file
		createDayDataFile(parserContext,yyyymmdd,isTest);

		generateDayDataList(eL, parserContext, dDataFName);
		if (dayDataList==null) {
			logger.info("Day Data for " + dDataFName + "is null");
			return;
		}
		
//		DayDataListStore ddLStore = new DayDataListStore(dayDataList,eL,parserContext);
//		int errNum = ddLStore.saveDayData();
//		String status = ddLStore.interpretSaveDDReturnVal(errNum) ;
//		System.out.println("DayDataStore Status: " + status);
//		assertTrue(errNum==0);
				
	}

	/**
	 * @param eL
	 * @param parserContext
	 * @param dDataFName
	 */
	private void generateDayDataList(EquityList eL,
			ApplContext parserContext, String dDataFName) {

		DayDataGenerator dDataGenerator = new DayDataGenerator(eL,dDataFName);
		dDataGenerator.setParserContextEnvironment(parserContext);
		dDataGenerator.generateStockList();		
		dayDataList = dDataGenerator.getDayDataList();
	
	}
	
	public EquityList loadEquityList(ApplContext parserContext) {
		//String fileEListName = "SP100-0709.xml";

		Properties pContextProps = parserContext.getGroupProperties();

		
		String listName = pContextProps.getProperty("Name");
//		String fName = pContextProps.getProperty("EquityListFileName");
//		String fullFName = ap.getApplAbsolutePath() + ap.getApplFileSeparator() + 
//				         ap.getDataDir(false) + ap.getApplFileSeparator() + fName;
		String fullFName = parserContext.getGroupFullFName(); 
	
		EquityList eL= new EquityList(listName,EquityType.STOCK,StorageType.FILE_XML);
		
		eL.loadEquitiesFromFile(fullFName) ;
		
		return eL;

	}
	
	/**
	 *  Not Implemented
	 *    will read list of group names
	 */
	private void loadGroupNames() {
		// TODO Auto-generated method stub
		
	}

	/**
	 *   Not Implemented
	 * @param string
	 * @return
	 */
	public Properties getProperties(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> getGroupNames() {

		return groupNames;
	}

	public ApplContext getParserContext(String grpName) {
		ApplContext pContext = new ApplContext(grpName,ap);
		return pContext;
	}

	public DayDataList getDayDataSet(String sp100) {
		// TODO Auto-generated method stub
		return dayDataList;
	}

	
}
