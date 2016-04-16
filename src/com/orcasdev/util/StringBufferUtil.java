package com.orcasdev.util;

import java.io.BufferedReader;
import java.io.IOException;

public class StringBufferUtil {

	public StringBufferUtil() {
		
	}

	public static StringBuffer readByCharToStringBuffer(BufferedReader in) {
		
		if (in==null)
			return null;

		boolean bf = false;
						
		char [] kChars = new char [1000] ;
		StringBuffer outBuff = null;
		int numCs = 0;
		
		try {
			numCs = in.read(kChars, 0, 0);
	    } catch (IOException e1) {
		    e1.printStackTrace();
	    }

	    if (numCs!=-1) {
		   
		   outBuff = new StringBuffer();
		   int st = 0;
	       int end = 1000;
	       while (numCs!=-1) {
	            
	    	   try {
	    		  numCs = in.read(kChars, st, end);
	    	   } catch (IOException e) {
	    		  e.printStackTrace();
	    	   }

	           if (numCs!=-1) {
	        	   outBuff.append(kChars, 0, numCs);
	           }   
	       }
	    }
	   
	    if (outBuff!=null && outBuff.length() > 0)
	    	bf = true;
	    
	    if (!bf)
	    	outBuff = null;
	    
		return outBuff;
	}
	
}
