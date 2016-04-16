package com.orcasdev.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.orcasdev.equity.EquityList;
import com.orcasdev.util.StringBufferUtil;

public class EquityHTMLParser {
	
    // logger here --- root class has to setup logger...
    Logger logger = Logger.getLogger(this.getClass());
	private String parceFile = null;
	private EquityList equityList ;
	private StringBuffer bufferToParse = null;

	public void setParceFile(String source) {
		parceFile = source;
	}
	
	public void setEquityList(EquityList eL) {
		equityList = eL;
	}
	
	public EquityList getEquityList() {
		return equityList ;
	}

	public StringBuffer readFileDataInto() {
		
		logger.info("Parser filename: " + parceFile);
		bufferToParse = null;
		
    	try {
    			
    		FileReader fileR = new FileReader(parceFile);
    		
   	        BufferedReader buffReader = new BufferedReader(fileR);
          		//put reader chars to String buffer
            bufferToParse = StringBufferUtil.readByCharToStringBuffer(buffReader);
    		
    		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return bufferToParse;
		
	}

	public String getStringValue(StringBuffer sb, String regex, String stDelimit, String endDelimit) {
		
		StringBuffer mSB = new StringBuffer();
		StringBuffer subSB = null;
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sb);
		
		if (m.find()) {
	    	CharSequence chars = sb.subSequence(m.start(), m.end());
	    	mSB.append(chars);
	    	int st = mSB.indexOf(stDelimit, 0);
	    	int end = mSB.indexOf(endDelimit, 0);
	    	subSB = new StringBuffer(mSB.subSequence(st+stDelimit.length(), end));
	    	logger.debug("Value: " + subSB.toString());
		}
		
		return subSB.toString();
			
	}
	public ArrayList <StringBuffer> getDataMatchFromRegEx(String regex) {
	
		//regex = "(dt1_GOOG.*<\\/td>)";
		ArrayList <StringBuffer> match = null;
		
		Pattern p = Pattern.compile(regex);
		
	       //  get a matcher object put string buffer as source
	    Matcher m = p.matcher(bufferToParse);
	    
	    int count = 0;
	    while(m.find()) {
	    	
	    	if (count == 0) {
	    		match = new ArrayList <StringBuffer>();
	    	}
	    	CharSequence chars = bufferToParse.subSequence(m.start(), m.end());
	    	match.add(new StringBuffer(chars));
	    	
	    	count++;
	        logger.debug("RegEx Match Count: " + regex + "  "+ count);
	    }
	    
		return match;
	    }

}  // eoc
