package com.orcasdev.service;

import infra.ApplicationProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class ApplContext {

//  <td id="dt1_AAPL_last" align="right" class="ds_last qb_shad" nowrap="nowrap">96.13</td>
//  <td id="dt1_AAPL_change" align="right" class="ds_change qb_shad" nowrap="nowrap"><span class="qb_up">+0.53</span></td>
//  <td id="dt1_AAPL_pctchange" align="right" class="ds_pctchange qb_shad" nowrap="nowrap"><span class="qb_up">+0.55%</span></td>
//  <td id="dt1_AAPL_high" align="right" class="ds_high qb_shad" nowrap="nowrap">96.62</td>
//  <td id="dt1_AAPL_low" align="right" class="ds_low qb_shad" nowrap="nowrap">94.81</td>
//  <td id="dt1_AAPL_volume" align="right" class="ds_volume qb_shad" nowrap="nowrap">48,511,298</td>
//  <td id="dt1_AAPL_displaytime" align="right" class="ds_displaytime qb_shad" nowrap="nowrap">08/01/14</td>
	
//	String dataDateString ;
//	double last = 0;
//	double hi = 0;
//	double low = 0;
//	double change = 0;
//	int volume = 0;
//	DecimalFormat df = null;
//	EquityType dataType = null;

	Logger logger = Logger.getLogger(ApplContext.class.getClass());

	private String rDir ;
	private String absDir;
	private String fileSep;
	private String dDir;
	private String tDir;
	private String dBkDir;
	private String tBkDir;

	private ApplicationProperties appProps ;
	public ApplicationProperties getAppProps() {
		return appProps;
	}

	private String propFileName;

	private String dayDataFileName;
	private String backupDayDataFileName;
	static public final String DAYDATA_FILENAME_SUFFIX = "_daydata.xml" ;
	static public final String DAYDATA_FILENAME_SUFFIX_BACKUP = "_daydata_bk.xml" ;

	private Calendar currDate = null;
	private TimeZone zone = TimeZone.getDefault();
	private Calendar startAfterTime = null ;
	private Calendar endLocalTime = null ;
	
	static public final int START_AT_HOUR = 17;
	static public final int END_AT_HOUR = 23;
	
	private String groupName ;
	private String groupFullFName;

	public Properties groupProps = new Properties();
	
	// >>> tn maybe name HTMLParserProperties
	public ApplContext(String groupName, ApplicationProperties appProps) {
		
		this.groupName = groupName;
		this.appProps=appProps;
		
		rDir = appProps.getResourcesDir();
		absDir = appProps.getApplAbsolutePath();
		fileSep = appProps.getApplFileSeparator() ;
		dDir = appProps.getDataDir(false);
		tDir = appProps.getTestDataDir(false);
		dBkDir = appProps.getDataDir(true);
		tBkDir = appProps.getTestDataDir(true);

		propFileName = absDir + fileSep + appProps.getResourcesDir() + fileSep 
		    + groupName + ".properties";
		
		logger.debug(groupName+" Resource File:" + " " + propFileName);
		
		loadGroupPropertiesFile();
		
		//String listName = groupProps.getProperty("Name");
		String fName = groupProps.getProperty("EquityListFileName");
		groupFullFName = getDataDir(false) + fileSep + fName;
		
	}
	
	private void loadGroupPropertiesFile() {

		InputStream input = null;
		try {
			 
			input = new FileInputStream(propFileName);
	 
			// load a properties file
			groupProps.load(input);
	 
//			// get the property value and print it out
//			System.out.println(prop.getProperty("database"));
//			System.out.println(prop.getProperty("dbuser"));
//			System.out.println(prop.getProperty("dbpassword"));
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupFullFName() {
		return groupFullFName;
	}
	
	public Properties getGroupProperties() {
		
		return groupProps;

	}
	
	public String getDayDataFileName(boolean isBackup) {
	
		if (isBackup)
			return backupDayDataFileName;
		else
			return dayDataFileName;
		
	}

	public String getDayDataFileName() {
		
		return dayDataFileName;
	}

	public void setTimeDependences(String yyyymmdd, boolean isTest) {

		dayDataFileName = null;
		if (yyyymmdd==null || yyyymmdd.length()!=8) {
			logger.debug("Date yyyymmdd not correct format: " + yyyymmdd);
		}
		else {
			
			String tempDir ;
			if (isTest)
				tempDir = this.tDir;
			else
				tempDir = this.dDir;
				
			dayDataFileName = absDir + fileSep + tempDir + fileSep + "q-" + groupName.toLowerCase() + "-" 
			          + yyyymmdd + ".html" ;

			if (isTest)
				tempDir = this.tBkDir;
			else
				tempDir = this.dBkDir;
				
			backupDayDataFileName = absDir + fileSep + tempDir + fileSep + "q-" + groupName.toLowerCase() + "-" 
			          + yyyymmdd + ".html" ;
		
		}
				
	}

	public boolean isTimeToGetData(Calendar theTime)
	{
		boolean bf = false;
		
		if (logger.isInfoEnabled()) {
			String DATE_FORMAT_YR_THRU_SEC = "yyyy-MM-dd HH:mm:ss";
		    SimpleDateFormat sDF = new SimpleDateFormat(DATE_FORMAT_YR_THRU_SEC);
		    logger.info("Input Time:" + sDF.format(theTime.getTime()));
		}
	
		int dayOfWeek = theTime.get(Calendar.DAY_OF_WEEK);
		
		if (dayOfWeek!=Calendar.SATURDAY && dayOfWeek!=Calendar.SUNDAY) {
			
			int hour = theTime.get(Calendar.HOUR_OF_DAY);
			if (hour>=START_AT_HOUR && hour<=END_AT_HOUR) {
				bf = true;
			}
		}
		return bf;
	}
	
//	public Calendar getRunAfterTime() {
//		return startAfterTime;
//		
//	}

	private void setRunBetweenTimes() {
		
		startAfterTime = Calendar.getInstance();
		startAfterTime.set(Calendar.HOUR_OF_DAY, START_AT_HOUR);
		
		endLocalTime = Calendar.getInstance(zone);
		
	}

	public String getEquityDayDataFileName(String symbol, boolean isBackup, boolean isTest) {

		String fileName = symbol;
				
		if (isBackup)
			fileName = fileName + DAYDATA_FILENAME_SUFFIX_BACKUP;
		else
			fileName = fileName + DAYDATA_FILENAME_SUFFIX;
			
		if (isTest) 
			fileName = getTestDir(isBackup) + fileSep + fileName;
		else
			fileName = getDataDir(isBackup) + fileSep + fileName;
		
		return fileName;
	}

	public String getTestDir(boolean isBackup) {
		
		if (isBackup)
			return absDir + fileSep + tBkDir;
		else
			return absDir + fileSep + tDir;
	}
	
	public String getDataDir(boolean isBackup) {

		if (isBackup)
			return absDir + fileSep + dBkDir;
		else 
			return absDir + fileSep + dDir;
	}

}  // eoc
