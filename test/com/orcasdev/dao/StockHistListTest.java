package com.orcasdev.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.orcasdev.equity.DayDataDTO;
import com.orcasdev.equity.Stock;
//import com.orcasdev.service.DayDataRequestService;
import com.orcasdev.service.ApplContext;
import com.orcasdev.service.StockHistListService;

import infra.ApplicationProperties;

public class StockHistListTest {

    Logger logger = Logger.getLogger(this.getClass());

	static final String SP100 = "SP100";

	StockHistList stockHistList = null;
	ApplicationProperties ap ;
	private ApplContext stocksContext;
//	StockHistListService shlService ;
	Stock stock ;
	
	private Connection conn = null;
	String dbLocName ;
	
	@Before
	public void setUp() throws Exception {

		ap = ApplicationProperties.getApplicationProperties(null);
		stocksContext = new ApplContext(SP100,ap);

		stock = new Stock();
		stock.setSymbol("GE");

	}

	@Test
	public void testStockHistList() {
		stockHistList = new StockHistList(stocksContext);
		Assert.assertNotNull("StockHistList is null", stockHistList);
	}

	@Test
	public void testGetList() {

		stockHistList = new StockHistList(stocksContext);
		
		dbLocName = stocksContext.getAppProps().getProperties().getProperty("DBLocation");
		System.out.println("DB Locaation Name: " + dbLocName);
		ArrayList <DayDataDTO> al	= null;
		
		openDBConn(dbLocName);
		
		if (conn != null) {
			al = stockHistList.getList(conn, stock);
			closeConn();
		}
		
		assertNotNull(al);
		assertNotEquals(al.size()-1, 0);
		assertNotNull(al.get(al.size()-1));
		//fail("Not yet implemented");
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

}
