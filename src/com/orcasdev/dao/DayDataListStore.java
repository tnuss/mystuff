package com.orcasdev.dao;

import infra.ApplicationProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.orcasdev.equity.DayDataAdaptor;
import com.orcasdev.equity.DayDataDTO;
import com.orcasdev.equity.DayDataList;
import com.orcasdev.equity.EquityList;
import com.orcasdev.equity.Stock;
import com.orcasdev.equity.StorageType;
import com.orcasdev.service.DayDataRequestService;
import com.orcasdev.service.ApplContext;

public class DayDataListStore {
	
    Logger logger = Logger.getLogger(DayDataRequestService.class.getClass());

    EquityList equityList = null;
	DayDataList dDataList = null;
	ApplContext pContext = null;
	String dbLocName = null;

	//private DayDataList saveThisDataList;

	private Connection conn = null;

	private final int EQUITYLISTBAD =3;
	private final int SAVEDAYDATAFAIL = 2;
	private final int DBCONNECTFAIL = 1;
	private final int DBACCESSGOOD = 0;
	
	public DayDataListStore (DayDataList dDataList, EquityList el, ApplContext pContext){
		equityList = el;
		this.dDataList = dDataList;
		this.pContext = pContext;
    	//String dbName="C:/amyapps/derby-10.11.1.1/DerbyTutor/firstdb";
		dbLocName = pContext.getAppProps().getProperties().getProperty("DBLocation");
}
	
	public String interpretSaveDDReturnVal(int errnum) {
		
		String returnString = "Save Day Data Unknown Error Num: " + errnum;
		
		switch (errnum) {
		
			case 0:
				returnString = "GOOD";
				break;
			case DBCONNECTFAIL:
				returnString = "Connection To DB Failed.";
				break;
			case SAVEDAYDATAFAIL:
				returnString = "Day Data Save Failure.";
				break;
			case EQUITYLISTBAD:
				returnString = "Equity List Not Right." ;
				break;
				
		}
		
		return returnString;
	}
	public int saveDayData() {
		
		int eNum = 0;
				
		// go thru equitylist 1 stock at time
		// then move stock's data from dDataList to another new DayDataList
		// and call save routine ... DayDataStore(Stock,DayDataList)
		// DayDataStore can handle 1 to many days of daydata
		//
		boolean dataFound = false;

		ArrayList <String> keys = equityList.getEquityListKeys();
		int daySaveBadCount = 0;
		
		if (keys==null || keys.size()==0) {
			return EQUITYLISTBAD ;
		}
						
		openDBConn(dbLocName);
		if (conn!=null) {
			
			for (String strKey : keys) {
				
				if (!addByKey(strKey)) {
					dataFound = false;
					daySaveBadCount ++;
				};	
			}
			
			if (!dataFound && daySaveBadCount==keys.size())
				eNum = SAVEDAYDATAFAIL;

			closeConn(); 

		}
		else { 
			eNum = DBCONNECTFAIL ;
		}
		
		return eNum;
	}


	private boolean addByKey(String strKey) {
		
		boolean bf = false;
		
		//		saveThisDataList = new DayDataList(strKey,dDataList.getStorageType());
		//		saveThisDataList.add(strKey, dDataList.getDayDataDTO(strKey));
		
		DayDataAdaptor ddA = dDataList.getDayDataDTO(strKey);
		
		if (ddA.getDataStatus()==null)
			bf = insertRow(strKey,ddA);
		else
			if (ddA.isNoDataError())
				setNoDataForEquity(strKey) ;				
		
		return bf;
	}

	private void setNoDataForEquity(String strKey) {
		
		equityList.getEquity(strKey).setNoDataFound(true);
		
		logger.info("*** NO DATA FOR EQUITY: " + equityList.getEquity(strKey).toString()); 
		
	}

	private boolean insertRow(String strVal,DayDataAdaptor ddA) {
		
		boolean bf = true;

		//ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements
        PreparedStatement psInsert;
        PreparedStatement psUpdate;

        boolean gotSQLExc;
		try
        {       	
             //conn.setAutoCommit(false);

			//        	CREATE TABLE TDAYDATA (
			//        			  EQUITYKEY CHAR(16) NOT NULL,
			//        			  DAYDATE CHAR(10) NOT NULL,
			//        			  LAST_PRICE DECIMAL(11,3) NOT NULL,
			//        			  HI_PRICE DECIMAL(11,3) NOT NULL,
			//        			  MIN_PRICE DECIMAL(11,3) NOT NULL,
			//        			  CHANGE DECIMAL(8,3) NOT NULL,
			//        			  VOLUME INT NOT NULL,
			//        			  DATA_TS TIMESTAMP NOT NULL
			//        			  );
        	
        	
            //psInsert = conn.prepareStatement("insert into firsttable values (?, ?)");
        	
            									//	   1,2,3,4,5,6,7,8
        	String sSQL = "insert into tdaydata values (?,?,?,?,?,?,?,?)";
            psInsert = conn.prepareStatement(sSQL) ;

            psInsert.setString(1, strVal );
            psInsert.setString(2, ddA.getDateString());
            psInsert.setDouble(3, ddA.getLast());
            psInsert.setDouble(4, ddA.getHi());
            psInsert.setDouble(5, ddA.getLow());
            psInsert.setDouble(6, ddA.getChange());
            psInsert.setInt(7, ddA.getVolume());
            
            java.util.Date date= new java.util.Date();
       	 	Timestamp tS = new Timestamp(date.getTime());  
       	 	ddA.setDataTS(tS);
            psInsert.setTimestamp(8, tS);
            
            psInsert.executeUpdate();
            System.out.println("Insert: " + strVal + " " + ddA.getGSONStringDTO());
		
		// fill in prepared statement
		// make jdbc call
		// if exists, call delete
		// try add again 

            // >>> tn  NEED psInsert.close();  ?????
            
            // error 23505 duplicate error
        } catch (SQLException se)  {
			logger.info("Insert Error: " + se.getSQLState() + "/" + se.getErrorCode() 
					+ ":"+ se.getMessage());
       		gotSQLExc = true;
			bf = false ;
        }
		 catch (Exception e) {
			bf = false ;
			logger.info("Insert Error: " + e.getMessage());
			// TODO: handle exception
		}
		        
 		return bf;
	}

	
	public DayDataDTO getDayDataDTO(String symbol, String yyyymmdd) {
		
		DayDataDTO ddDTO = null;
		
		return ddDTO ;
		
	}
	
	/**
	 * 
	 */
	private void closeConn() {
		conn = CheckDerby.doDerbyEmbeddedConnection(dbLocName,"shutdown");
	}
	
	private void openDBConn(String dbName) {

		CheckDerby chkDerby = new CheckDerby();
        // Create (if needed) and connect to the database.
              // The driver is loaded automatically.
        conn = CheckDerby.doDerbyEmbeddedConnection(dbName,"open"); 
        
        if (conn!=null)
        	logger.info("Connected to database " + dbName);
       	else
        	logger.info("Not connected to database " + dbName);
        
	}

	//	public int saveDayDataDTO(DayDataDTO ddDTO, String symbol) {
//	
//	int eNum = 0;
//	
//	
//	return eNum;
//}

}
