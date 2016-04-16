package com.orcasdev.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.orcasdev.dao.CheckDerby;
import com.orcasdev.dao.StockHistList;
import com.orcasdev.equity.DayDataDTO;
import com.orcasdev.equity.EquityList;
import com.orcasdev.equity.EquityType;
import com.orcasdev.equity.Stock;
import com.orcasdev.equity.StorageType;

import infra.ApplicationProperties;

public class StockHistListService {
	
    Logger logger = Logger.getLogger(this.getClass());

	static final String SP100 = "SP100";

	StockHistList stockHistList = null;
	ApplicationProperties ap ;
	private ApplContext stocksContext;
//	StockHistListService shlService ;
	Stock stock ;
	
	private Connection conn = null;
	String dbLocName ;

	private EquityList eL;

	public StockHistListService(ApplicationProperties apIn, String group) {
		
		if (apIn==null)
			ap = ApplicationProperties.getApplicationProperties(null);
		else
			ap = apIn;
		
		if (group==null)
			group = SP100;
		
		stocksContext = new ApplContext(group,ap);
	}
	
	
	public ArrayList <DayDataDTO> getOneHistList(String symbol) {

		stock = new Stock();
		stock.setSymbol(symbol);
		stockHistList = new StockHistList(stocksContext);
		
		dbLocName = stocksContext.getAppProps().getProperties().getProperty("DBLocation");
		System.out.println("DB Locaation Name: " + dbLocName);
		ArrayList <DayDataDTO> al	= null;
		
		openDBConn(dbLocName);
		
		if (conn != null) {
			al = stockHistList.getList(conn, stock);
			closeConn();
		}
		
		return al;	
	}
	
	private void closeConn() {
		conn = CheckDerby.doDerbyEmbeddedConnection(dbLocName,"shutdown");
	}
	
	private void openDBConn(String dbName) {

		conn = null;
		CheckDerby chkDerby = new CheckDerby();
        // Create (if needed) and connect to the database.
              // The driver is loaded automatically.
        conn = CheckDerby.doDerbyEmbeddedConnection(dbName,"open"); 
        
        if (conn!=null)
        	logger.info("Connected to database " + dbName);
       	else
        	logger.info("Not connected to database " + dbName);
        
	}
	
	
	public ArrayList <String> getHistListSymbols() {
		// returning symbols via group
		Properties pContextProps = stocksContext.getGroupProperties();
		
		String listName = pContextProps.getProperty("Name");
		String fullFName = stocksContext.getGroupFullFName(); 
	 
		eL = new EquityList(listName,EquityType.STOCK,StorageType.FILE_XML);
		
		eL.loadEquitiesFromFile(fullFName) ;
		
		// CREATE ARRAYLIST of SYMBOLS
		ArrayList <String> alSymbols = eL.getEquityListKeys();

		return alSymbols;
	}

	public String jsonHistListSymbols() {
		// returning symbols via group
		Properties pContextProps = stocksContext.getGroupProperties();
		
		String listName = pContextProps.getProperty("Name");
		String fullFName = stocksContext.getGroupFullFName(); 
	 
		eL = new EquityList(listName,EquityType.STOCK,StorageType.FILE_XML);
		
		eL.loadEquitiesFromFile(fullFName) ;
		
		// CREATE ARRAYLIST of SYMBOLS
		ArrayList <String> alSymbols = eL.getEquityListKeys();

		String jsonStr = "{\"Symbols\": [ ";
		
		for (int i=0;i<alSymbols.size();i++) {
			
			jsonStr += "\"" + alSymbols.get(i) + "\"";

			if (i<alSymbols.size()-1) {
			   jsonStr += ", ";
			}
		}
		jsonStr += "]}";
		
		return jsonStr;
	}
	
	/**
	 * Get DayDataDTO array for Stock
	 * @param SIMPLE (straight data) 
	 * @param stock symbol ---->>> Should this be TEMPORARY FOR ONE OR MORE????
	 * @param In JSON, ArraySize
	 * @return
	 */
	public ArrayList<DayDataDTO>[] getSetOfHistories(String type, String symbol, String params) {

		@SuppressWarnings("unchecked")
		ArrayList<DayDataDTO> ddDTOA[] = new ArrayList[10] ;
		
		ddDTOA[0] = getOneHistList(symbol);
		System.out.println("Length of Array: " + ddDTOA.length + "/" + ddDTOA[1].size());
				
//		return ddDTOA; // this return is good
		return null;
	}

}
