package com.orcasdev.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import com.orcasdev.util.StringBufferUtil;

public class HTTPPageStringBuffer {

	private String httpUrl = null;
	private URLConnection connection = null;
	private boolean connectionGood = false;
	private StringBuffer inBuff = null ; 
	
	
	/**
	 *    HTTPPageStringBuffer 
	 *    Functions:
	 *     1) Connect to URL
	 *     2) Read the content a the URL
	 *     3) Return a String Buffer with that content
	 */
	public HTTPPageStringBuffer() {
		;
	}
	
	public void setURL(String httpUrl) {
		
		this.httpUrl = httpUrl;
		
	}

	public boolean openURLConn() {
		
        URL url = null;
        connectionGood = false;
        
		try {
			url = new URL(httpUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
			connection = url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (connection!=null) {
            connectionGood = true;
        }
        
		return connectionGood;
	
	}

	public StringBuffer getURLStringBuffer() {
		
		StringBuffer buffer = null;
		
		if (inBuff!=null) 
			buffer = new StringBuffer().append(inBuff);

		return buffer;
	}


	/** 
	 *   Reads the text coming from an opened HTTP connection
	 *   
	 * @return boolean, read without error or with error
	 */
	public boolean readURLHTTPContent() {

		boolean bf = false;
		
		if (!connectionGood)
			return bf;
		
		InputStreamReader inStrm = null;
		try {
			inStrm = new InputStreamReader(connection.getInputStream());
		} catch (IOException e) {
				e.printStackTrace();
		} ;

       BufferedReader in = new BufferedReader(inStrm);

       		//put reader chars to String buffer
       inBuff = StringBufferUtil.readByCharToStringBuffer(in);
       
	   try {
		   in.close();
	   } catch (IOException e) {
		   e.printStackTrace();
	   }

       if (inBuff!=null)
    	   bf = true;
		
       return bf;

	}

}
