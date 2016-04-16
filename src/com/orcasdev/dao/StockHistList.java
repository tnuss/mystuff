package com.orcasdev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.orcasdev.equity.DayDataAdaptor;
import com.orcasdev.equity.DayDataDTO;
import com.orcasdev.equity.DayDataList;
import com.orcasdev.equity.EquityList;
import com.orcasdev.equity.EquityType;
import com.orcasdev.equity.Stock;
import com.orcasdev.service.DayDataRequestService;
import com.orcasdev.service.ApplContext;

import infra.ApplicationProperties;

public class StockHistList {

    Logger logger = Logger.getLogger(this.getClass());

	Stock stk ;
	//DayDataList dDataList = null;
	ApplContext sContext = null;
	String dbLocName = null;
	ArrayList <DayDataDTO> listDDD = null;
	
	//private DayDataList saveThisDataList;

	private Connection conn = null;

	private final int EQUITYLISTBAD =3;
	private final int SAVEDAYDATAFAIL = 2;
	private final int DBCONNECTFAIL = 1;
	private final int DBACCESSGOOD = 0;

	private DayDataAdaptor ddAdaptor;

	public StockHistList(ApplContext stocksContext) {
		sContext = stocksContext;
	}
	
	public ArrayList <DayDataDTO> getList(Connection c, Stock stk) {
		
		conn = c;
		this.stk = stk;

		int eNum = 0;
		
		if (conn!=null) {
			boolean dataFound = fillList();
			dataFound = false;
		}
		
		ArrayList <DayDataDTO> retList = new <DayDataDTO> ArrayList() ;
		if (eNum==0){
			retList.addAll(listDDD);
		}
			
		return listDDD;
	}
	
	private boolean fillList() {
		
		boolean bf = true;

		//ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements
        PreparedStatement psSelect;
        //PreparedStatement psUpdate;

        boolean gotSQLExc = false;
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
        	
        	
            									//	   1,2,3,4,5,6,7,8
        	String sSQL = "select * from tdaydata where equitykey='" + stk.getSymbol()
        	         + "' order by daydate";
        	
            psSelect = conn.prepareStatement(sSQL) ;
         
            ResultSet rs = psSelect.executeQuery();
            //psSelect.executeQuery()
            System.out.println("Select Executed: " + sSQL);
                 //+ " " + ddA.getGSONStringDTO());
		
            ResultSetMetaData metaRS = rs.getMetaData();
            int numCols = metaRS.getColumnCount();
            System.out.println("Num Columns: " + numCols);
            
            ddAdaptor = new DayDataAdaptor(EquityType.STOCK, stk.getDfQuote() );
            listDDD = new ArrayList <DayDataDTO> ();
            DayDataDTO ddd ;
            while (rs.next())
            {
                  //System.out.println("RS1/2: " + rs.getString(1) + "/" + rs.getString(2));
                  //System.out.println("RS3/4: " + rs.getString(3) + "/" + rs.getString(4));
                  //System.out.println("RS5/6: " + rs.getString(5) + "/" + rs.getString(6));
                  //System.out.println("RS7/8: " + rs.getString(7) + "/" + rs.getString(8));
                  // ddAdaptor.setDayDataQuote(hi2, low2, last2, change2, volume2);
            	ddAdaptor.resetDayDataAdaptor();
            	ddAdaptor.setDateString(rs.getString(2));
                ddAdaptor.setLast(rs.getDouble(3));
                ddAdaptor.setHi(rs.getDouble(4));
                ddAdaptor.setLow(rs.getDouble(5));
                ddAdaptor.setVolume(rs.getInt(7));
                ddAdaptor.setDataTS(rs.getTimestamp(8));
                System.out.println("GSON: " + ddAdaptor.getGSONStringDTO());
                ddd = ddAdaptor.cloneDayDataDTO();
                listDDD.add(ddd);
            }
            
            rs.close();
            psSelect.close();

            // error 23505 duplicate error
        } catch (SQLException se)  {
			logger.info("Select Error: " + se.getSQLState() + "/" + se.getErrorCode() 
					+ ":"+ se.getMessage());
       		gotSQLExc = true;
			bf = false ;
        }
		 catch (Exception e) {
			bf = false ;
			logger.info("Select Error: " + e.getMessage());
			// TODO: handle exception
		}
		        
 		return bf;
	}	
}
