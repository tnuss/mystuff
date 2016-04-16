package com.orcasdev.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 *   Service class to generalize file operations
 *   Keep track of where to put/find files
 *   Retrieve file handle??
 *   1) URL Html file
 *   2) ??
 *   
 * @author tnuss
 *
 */
public class FileIOService {
	
    Logger logger = Logger.getLogger(this.getClass());

	private String sourceURL = null;
	private ApplContext pContext;
	private int MAX_BACKUPS = 2;
	
	public FileIOService(ApplContext parserContext) {
		pContext = parserContext;
	}

	public void setSourceURL(String equityURL) {
		sourceURL = equityURL;
	}

	public String getSourceURL() {
		// TODO Auto-generated method stub
		return sourceURL;
	}

	public void writeDayDataFile(StringBuffer sb, boolean isTest) {
		
        String fName = pContext.getDayDataFileName(false);
        
        File dDataFile = new File(fName);

        if (dDataFile.exists()) {
        	
        	logger.info("Doing Backup  for: " + fName);
        	int iterationNum = 1;
        		// recursive call
        	writeDayDataFileBackup(fName,iterationNum);

        }
        
       	char [] chs = new char[sb.length()];
        sb.getChars(0, sb.length()-1, chs, 0);        	
       	writeCharsToFile(dDataFile, chs);
        	
	}

		/**
		 * 
		 * @param fName : String
		 * @param iterationNum : int
		 */
	public void writeDayDataFileBackup(String fName, int iterationNum)
	{

        String fNameBk = pContext.getDayDataFileName(true);
		
		String bkSuffix = ".bk" + iterationNum;
        fNameBk += bkSuffix;

        File dDataFileBk = new File(fNameBk);

        if (dDataFileBk.exists()) {

        	iterationNum++;
        	if (iterationNum <= MAX_BACKUPS) {
        		writeDayDataFileBackup(fNameBk,iterationNum);
        	} 
        	else {
        				
        		Boolean renameFlg = dDataFileBk.delete();
        		if (!renameFlg) {
        			logger.info("Rename Backup Error. Doing Return(): " + fNameBk);
        			return;
        		}
        	}
       	}
        
        File dDataFile = new File(fName);
        dDataFile.renameTo(dDataFileBk);
        logger.info("Backed up: " + fName + " to " + dDataFileBk);
        
        return;
		
	}
	
	/**
	 * @param dDataFile : File
	 * @param chs : char []
	 */
	private void writeCharsToFile(File dDataFile, char[] chs) {
		try {
		    dDataFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileWriter fw;
		try {
			fw = new FileWriter(dDataFile.getAbsoluteFile());
		    BufferedWriter bw = new BufferedWriter(fw, 8192);
		    bw.write(chs);
		    bw.newLine();
		    bw.flush();
		     //bw.write(testSB);
		    bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// eoc
}
