package com.orcasdev.service;

import java.util.ArrayList;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import infra.ApplicationProperties;

@ServerEndpoint("/DoWebSocket")
public class WebSocketService {
	
	// ------------------------------------------------------------------------------------- 
	//  
	// ----------- NOTE this will not work on eclipse's web browser plugin ------------------ 
	//  
	// ------------------------------------------------------------------------------------- 

	Logger logger = Logger.getLogger(this.getClass());
	
public WebSocketService () {
	 logger.debug("In WebSocketService() Constructor");
}

 @OnMessage
 	public String messageReceiver(String message) {
	 	
	 logger.info("In messageReceiver(...) : " + message);
	 	//System.out.println("Trans#: " transNum);
	 	String retStr = setMessage(message);
	 	
	 	//  -- CHECK DERBY --
	 	//CheckDerby cd = new CheckDerby();
	 	//cd.main(null);
	 
	    return retStr ;
	}

	private String  setMessage(String msg) {
	
		String retStr ;
		if (msg.charAt(0)=='1')
			retStr = getGroupList(msg);
		else
		if (msg.charAt(0)=='T')
			retStr = DoTrans(msg);
		else
			retStr = "Msg Received (WebSocketService): " + msg ; 
		
		return retStr;
		
	}
	
	private String DoTrans(String msg) {
		
		String ret = "X,Y\n0,0\n";
		
		switch (msg) {
			case "T0":
				ret = "X,Y\n" + "2,1\n" + "4,3\n" + "5,2\n" + "7,3\n" + "8,5\n"
						+ "10,2\n" + "12,12\n" ;
				break;
				
			case "T1":
				ret = getStockHistRawList("BA");
				break;
		}
				
		return ret;
	}
	
	private String getGroupList(String msg) {
		
		StockHistListService sHLService ;
		ApplicationProperties ap;
		String grpName ="SP100";
		
		// ********  WEB APP: FILES NEED TO BE RELATIVE PATHS  *********
		String[]args = {"IsWebApp=Y","AppDirDeploy=wtpwebapps/Stks081415/WEB-INF"};
		ap = ApplicationProperties.getApplicationProperties(args);
		
//		testContext = new ApplContext(grpName,ap);
		sHLService = new  StockHistListService(ap, grpName);
		ArrayList <String> gsonArray = sHLService.getHistListSymbols();
		
		String jsonStr = sHLService.jsonHistListSymbols();

//		String retString = "";
//		String jsonStr = "{\"Symbols\": [ ";
//		
//		for (int i=0;i<gsonArray.size();i++) {
//			
//			retString += gsonArray.get(i);
//			jsonStr += "\"" + gsonArray.get(i) + "\"";
//
//			if (i<gsonArray.size()-1) {
//   			   retString += "/";
//			   jsonStr += ", ";
//			}
//		}
//		jsonStr += "]}";
//		//return "getGroupList Transaction";
		
		return jsonStr;
	}

	private String getStockHistRawList(String string) {
		String ret = "X,Y\n0,0\n";
		
		return ret;
	}
	
}

