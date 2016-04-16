package com.orcasdev.comm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

//import java.io.BufferedReader;
import java.net.URLConnection;

//import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.orcasdev.comm.HTTPPageStringBuffer;

public class HTTPUrlConnectionTest {

	private static final String SP100_DAILY_STOCKS_URL = "http://www.barchart.com/stocks/sp100.php?_dtp1=0";
	private static final String GOOGLE = "http://www.google.com";
	private static final String BADURL = "http://www.ZZZQQQKKK.com";
	
	HTTPPageStringBuffer httpUrl = null;
	URLConnection testConn = null;
	
	@Before
	public void setUp() throws Exception {
		httpUrl = new HTTPPageStringBuffer();
	}

	@After
	public void tearDown() throws Exception {
	
		httpUrl = null;
	}

	@Test
	public void testOpenHTTPUrlConn() throws Exception {
		
		assertEquals(true, setGoodURLConn() );
		//fail("Not yet implemented");
	}

	@Test
	public void testOpenBADHTTPUrlConn() throws Exception {
	
		HTTPPageStringBuffer httpUrlx = new HTTPPageStringBuffer();
		try {
			
			httpUrlx.setURL(BADURL) ;
			boolean f = httpUrl.openURLConn();
			fail("Tried to Open Bad URL -- Should have thrown an Exception");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetInputStream() {
		
		if (setGoodURLConn())
			assertEquals(true, httpUrl.readURLHTTPContent());
		else
			fail("Tried to Open Good URL - Failed.");

	}

	@Test
	public void testGetURLStringBuffer() {
		
		setGoodURLConn();
				
		assertEquals(true,httpUrl.readURLHTTPContent());
		
		StringBuffer sb = httpUrl.getURLStringBuffer();
		
		assertNotNull("String Buffer from URL is NULL", sb);
		
		String str = sb.toString() ;
		
		System.out.println(str);
		
		//readByCharToStringBuffer(BufferedReader in)

		// >>> TIME TO REFACTOR getURLStringBuffer
		// >>> TIME TO REFACTOR --- use constructor to set URL String
		// >>> TIME TO REFACTOR 
		
	}
	
	public void testreadByCharToStringBuffer() {
	
		// test Private method????
	    //readByCharToStringBuffer(BufferedReader in)
	}
	
	private boolean setGoodURLConn() {
		
		httpUrl.setURL(GOOGLE) ;
		boolean bf = httpUrl.openURLConn();
		
		return bf;
	}

}
