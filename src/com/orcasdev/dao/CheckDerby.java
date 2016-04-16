package com.orcasdev.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.orcasdev.service.DayDataRequestService;

public class CheckDerby {

	static final String CSdriver = new String("org.apache.derby.jdbc.EmbeddedDriver");
	static String connectionShutdownURL = "jdbc:derby:;shutdown=true";
    static String connectionOpenURL = null;
    Logger logger = Logger.getLogger(DayDataRequestService.class.getClass());

	
	public static Connection doDerbyEmbeddedConnection(String dbName, String connType) {
	
		Connection conn = null;

		// define the Derby connection URL to use
		//String connectionURL = "jdbc:derby:" + dbName + ";create=true";
 		
		connectionOpenURL = "jdbc:derby:" + dbName + ";";
		String connectionURL = null;

		
		if (connType.equals("open")) {
			
			try {
	
				Class.forName(CSdriver).newInstance();
	
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			connectionURL = connectionOpenURL;
			conn = setConnState(connectionURL);
		}
		
//		if (connType.equals("open")) {
//			connectionURL = connectionOpenURL;
//			conn = doOpenConn(connectionURL);
//		}
			// from static string
		if (connType.equals("shutdown")) {
			connectionURL = connectionShutdownURL;
			conn = setConnState(connectionURL);
			System.out.println("Connection Showdown : " + connectionOpenURL) ;

		}
		
    	return conn;
	}
	/**
	 * @param connectionURL
	 * @return connection
	 */
	private static Connection setConnState(String connectionURL) {
		boolean bf = true ;
       	boolean gotSQLExc = false;
       	Connection conn = null;
				
		try {
			conn = DriverManager.getConnection(connectionURL);
			
        } catch (SQLException se)  {     
        	if (se.getSQLState().equals("XJ015")) {          
        		gotSQLExc = true;
            }
        	else { 
        		se.printStackTrace();
        	}
		}
		
		//  NOTE when SHUTDOWN conn is null ---
		return conn;
    } 
	
	/**
	 * @param connectionURL
	 * @return connection
	 */
	private static Connection doShutdownConn(String connectionURL) {
		boolean bf = true ;
      	boolean gotSQLExc = false;
       	Connection conn = null;

        //   ## DATABASE SHUTDOWN SECTION ##
        /*** In embedded mode, an application should shut down Derby.
       Shutdown throws the XJ015 exception to confirm success. ***/  
             	
		try {
			conn = DriverManager.getConnection(connectionURL);
				
		} catch (SQLException se)  {     
			if ( se.getSQLState().equals("XJ015") ) {          
				gotSQLExc = true;
			}
		}
		if (!gotSQLExc) {
			System.out.println("Database did not shut down normally");
		}
		else  {
			System.out.println("Database shut down normally"); 
		} 
	
	return conn;
	
	}

	
	public static void main(String[] args) {
		
                  // define the driver to use 
    	//String driver = "org.apache.derby.jdbc.EmbeddedDriver";
                  // the database name 
    	String dbName="/home/tnuss/apps/derbydata/firstdb";
    	//String tName = "sEntity.tDayData";
    	String tName = "TDAYDATA";

        Connection conn = null;

              // Create (if needed) and connect to the database.
              // The driver is loaded automatically.
        conn = doDerbyEmbeddedConnection(dbName,"open");      
        System.out.println("Connected to database " + dbName);
        
        try {
			if (! wwdChk4Table(conn, tName))
			{ 
			    System.out.println (" . . Result Set can't connect to table firstdb");
			            //s.execute(createString);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
              
        try {
		
        	conn.close();
	        System.out.println("Closed connection");

        } catch (SQLException e) {
			e.printStackTrace();
		}                                
        	// shutdown derby when application is done
        conn = doDerbyEmbeddedConnection(dbName, "shutdown");
              
        System.out.println("Getting Started With Derby JDBC program ending.");
        
    }    
 
    public static boolean wwdChk4Table (Connection conTst, String tName ) throws SQLException {
    	
    	boolean chk = true;
        boolean doCreate = false;
        
        Statement s = conTst.createStatement();
        try {
            //s.execute("select * from firsttable");
        	s.execute("select * from " + tName);
        } 
        catch (SQLException sqle) {
           String theError = (sqle).getSQLState();
           //   System.out.println("  Utils GOT:  " + theError);
           		/** If table exists will get -  WARNING 02000: No row was found **/
           if (theError.equals("42X05"))   // Table does not exist
           { 
        	   return false;
           } 
           else
           {
        	   if (theError.equals("42X14") || theError.equals("42821"))  {
                   System.out.println("WwdChk4Table: Problem connection to firstdb");
                   throw sqle;  
        	   }
        	   else
        	   {
                   System.out.println("WwdChk4Table: Unhandled SQLException" );
                   throw sqle;
        	   }
           	}
        }
         
        String col1 = null ;
        String col2 = null;
        ResultSet rs = s.getResultSet();
        while (rs.next()) {
        	col1 = rs.getString(1);
            col2 = rs.getString(2);
            System.out.println("Result Set Col1/2: " + col1 + "/" + col2);
        }
                         
        s.close();
        	//  System.out.println("Just got the warning - table exists OK ");
        return true;

     }
}

