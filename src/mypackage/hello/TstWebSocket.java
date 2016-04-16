package mypackage.hello;

import com.orcasdev.dao.CheckDerby;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

//@ServerEndpoint("/DoWebSocket")
public class TstWebSocket {
	
	// ------------------------------------------------------------------------------------- 
	//  
	// ----------- NOTE this will not work on eclipse's web browser plugin ------------------ 
	//  
	// ------------------------------------------------------------------------------------- 
	
// @OnMessage
// 	public String messageReceiver(String message) {
//	 	//System.out.println("Trans#: " transNum);
//	 	String retStr = setMessage(message);
//	 	
//	 	//  -- CHECK DERBY --
//	 	//CheckDerby cd = new CheckDerby();
//	 	//cd.main(null);
//	 
//	    return retStr ;
//	}
//
//	private String  setMessage(String msg) {
//	
//		String retStr ;
//		if (msg.charAt(0)=='T')
//			retStr = DoTrans(msg);
//		else
//			retStr = "Message Received 13Fef: " + msg ; 
//		
//		return retStr;
//		
//	}
//	
//	private String DoTrans(String msg) {
//		
//		String ret = "X,Y\n0,0\n";
//		
//		switch (msg) {
//			case "T0":
//				ret = "X,Y\n" + "2,1\n" + "4,3\n" + "5,2\n" + "7,3\n" + "8,5\n"
//						+ "10,2\n" + "12,12\n" ;
//				break;
//				
//			case "T1":
//				ret = getStockHistRawList("BA");
//				break;
//		}
//				
//		return ret;
//	}
//
//	private String getStockHistRawList(String string) {
//		String ret = "X,Y\n0,0\n";
//		
//		return ret;
//	}
	
}