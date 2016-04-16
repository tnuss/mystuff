package com.orcasdev.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.orcasdev.equity.DayDataAdaptor;
import com.orcasdev.equity.DayDataList;
import com.orcasdev.equity.Equity;
import com.orcasdev.equity.EquityList;
import com.orcasdev.equity.EquityType;
import com.orcasdev.equity.Stock;
import com.orcasdev.equity.StockBuilder;
import com.orcasdev.equity.StorageType;

public class DayDataGenerator {

	Logger logger = Logger.getLogger(this.getClass());

	private EquityList equityList;
	private String dataFileName;
	private ApplContext parserContext;
	private Properties generatorProps;
	private EquityHTMLParser eHtmlParser;
	private DayDataList ddList = new DayDataList("yyyymmdd", StorageType.FILE_XML);

	private DayDataParsingDTO dDPDTO = new DayDataParsingDTO();

/**
 * 	
 * @param eList
 * @param fileEListName
 * 
 *     -Process:
 *    1) Request to get Day Data equity list
 *       -- Get data location
 *       -- Get/Set Parsing params by Equity List Name
 *          --- regex, delimiters for daily data fields-->origin data search string link to day data fields and field data type
 *       -- Read/Parse day data by params and equity list
 *         --- Temp store keyed by values in equity list
 *       -- Take Temp store and add equity day data to separate main day data store by equity  
 * 
 */
	
	public DayDataGenerator(EquityList eList, String fileName) {
		equityList = eList;
		dataFileName = fileName;
	}
	
	public void setParserContextEnvironment(ApplContext pContext) {
		parserContext = pContext;
		generatorProps = parserContext.getGroupProperties();
		dDPDTO.initNonIndexPropValues();
	}
	
	public DayDataList getDayDataList() {
		return ddList;
	}

	public void generateStockList() {
		
			// go thru equity list keys and get data
		ArrayList <String> keysEList = equityList.getEquityListKeys();
				
		// use EquityHTMLParser methods to get strings with data
		eHtmlParser = new EquityHTMLParser();
		eHtmlParser.setEquityList(equityList);

		eHtmlParser.setParceFile(dataFileName);
		
		StringBuffer sb = eHtmlParser.readFileDataInto();
		DayDataAdaptor ddDTO = null;
		
		Stock stock;
		for (String key:keysEList) {
			stock = (Stock) equityList.getEquity(key);
			ddDTO = fillEquityDayData(stock);
			ddList.add(stock.getSymbol(), ddDTO);
		}

		// do one for debugging put data into DayDataAdaptor and its list
//		stock = (Stock) equityList.getEquity(keysEList.get(1)) ;
//		ddDTO = fillEquityDayData(stock);
//		ddList.add(stock.getSymbol(), ddDTO);
		
	}

	//	</thead><tbody>  <tr id="dt1_AAPL" class="">
		//    <td align="left" class="ds_symbol qb_shad" nowrap="nowrap"><a href="/quotes/stocks/AAPL">AAPL</a></td>
		//    <td align="left" class="ds_name qb_shad" nowrap="nowrap"><a href="/quotes/stocks/AAPL">Apple Inc</a></td>
		//    <td id="dt1_AAPL_last" align="right" class="ds_last qb_shad" nowrap="nowrap">96.13</td>
		//    <td id="dt1_AAPL_change" align="right" class="ds_change qb_shad" nowrap="nowrap"><span class="qb_up">+0.53</span></td>
		//    <td id="dt1_AAPL_pctchange" align="right" class="ds_pctchange qb_shad" nowrap="nowrap"><span class="qb_up">+0.55%</span></td>
		//    <td id="dt1_AAPL_high" align="right" class="ds_high qb_shad" nowrap="nowrap">96.62</td>
		//    <td id="dt1_AAPL_low" align="right" class="ds_low qb_shad" nowrap="nowrap">94.81</td>
		//    <td id="dt1_AAPL_volume" align="right" class="ds_volume qb_shad" nowrap="nowrap">48,511,298</td>
		//    <td id="dt1_AAPL_displaytime" align="right" class="ds_displaytime qb_shad" nowrap="nowrap">08/01/14</td>
		//    <td align="center" class="qb_shad noprint" nowrap><a href="/detailedquote/stocks/AAPL" title="Detailed Quote for AAPL"><img src="/shared/images/quote_icon.gif" width="12" height="10" border="0" /></a>&nbsp;<a href="/charts/stocks/AAPL" title="Chart for AAPL"><img src="/shared/images/chart_icon.gif" width="12" height="10" border="0" /></a>&nbsp;<a href="/opinions/stocks/AAPL" title="Opinion for AAPL"><img src="/shared/images/opinion_icon.gif" width="12" height="10" border="0" /></a>&nbsp;<a href="/cheatsheet.php?sym=AAPL" title="Cheat Sheet for AAPL"><img src="/shared/images/cheatsheet_icon.gif" width="12" height="10" border="0" /></a></td>
		//  </tr>

		//		Name=SP100
		//		EquityListFileName=SP100-0709.xml
		//		NumberLevelsRegEx=2
		//		RegEx_1=(XFINDMEX.*<\\\\/td>)
		//		RegExReplaceChars_1=XFINDMEX
		//		Prefix_1=dt1_
		//		Suffix_1=FieldRegExSuffix
		//		RegEx_2=>.+<\\\\/td>
		//		StDelimeter=>
		//		EndDelimiter=<
		//		NumDataFields=2
		//		FieldName_1=last
		//		FieldType_1=double
		//		FieldRegExSuffix_1=_last
		//		FieldName_2=volume
		//		FieldType_2=int
		//		FieldRegExSuffix_2=_volume

	private DayDataAdaptor fillEquityDayData(Equity stock) {

		int numFieldsToParce = Integer.parseInt((String) generatorProps.get("NumDataFields"));

		DayDataAdaptor stockDayData = new DayDataAdaptor(EquityType.STOCK,new Stock().getDfQuote());
				
		dDPDTO.keySymbol = stock.getSymbol();
		
		String strVal;
		
		for (int i = 0 ; i<numFieldsToParce; i++){
			
			dDPDTO.setIndexDependentFields(i+1);
			dDPDTO.setSearchFor(i+1);
			dDPDTO.setRegex1st();
			
			strVal = getFieldNamePropValue();
			// show first field of each...
			if (i==0)
				logger.info("dDPDTO.FieldName Value: " + dDPDTO.keySymbol + ":" 
			      + dDPDTO.fieldName + ":" + strVal);
			
			putInDayDataDTO(stockDayData,dDPDTO, strVal);
			
		}
		
		return stockDayData;
	}
	
	private void putInDayDataDTO(DayDataAdaptor stockDayData, DayDataParsingDTO dDPDTO, String valueIn) {

		//>>>> need tocheck for being a number before saving
		//
		String value = null;
		if (valueIn == null)
			valueIn = "0";
		
		value = valueIn;

		if (valueIn.contains(","))
			value = valueIn.replaceAll(",", "");
			
		boolean bf = NumberUtils.isNumber(value);
		if (!bf)
			value = "0";
		
		if (dDPDTO.fieldName.equalsIgnoreCase(DayDataAdaptor.LAST))
			stockDayData.setLast(value);
	
		if (dDPDTO.fieldName.equalsIgnoreCase(DayDataAdaptor.LOW))
		   stockDayData.setLow(value);

		if (dDPDTO.fieldName.equalsIgnoreCase(DayDataAdaptor.HI))
		   stockDayData.setHi(value);

		if (dDPDTO.fieldName.equalsIgnoreCase(DayDataAdaptor.VOLUME)) {
		       stockDayData.setVolume(Integer.parseInt(value));
		}
		
		if (dDPDTO.fieldName.equalsIgnoreCase(DayDataAdaptor.DATA_DATE)) {
			
			String yyyymmdd ;
			String sa[] = valueIn.split("/");
			
			if (sa.length == 3) {
				yyyymmdd = "20" + sa[2] + sa[0] + sa[1];
    		    stockDayData.setDateString(yyyymmdd);
			}
			else
				stockDayData.setDateTodayString();
						
		}

		if (value==null){
			logger.error("NULL Value for DataDTO FIELD: " + dDPDTO.keySymbol 
					+ "." + dDPDTO.fieldName);
			// Should create a file with SP100 with no data
		}
	}
	
    private class DayDataParsingDTO {

    	//    	Name=SP100
    	//	EquityListFileName=SP100-0709.xml
    	//    	NumberLevelsRegEx=2
//    	RegEx_1=(XFINDMEX.*&lt;\\/td&gt;){1}
//    	RegExReplaceChars_1=XFINDMEX
//    	Prefix_1=dt1_
//    	Suffix_1=FieldRegExSuffix
//    	RegEx_2=&gt;<\\/span>.+&lt;\\/td&gt;
//    	StDelimeter=span>
//    	EndDelimiter=<span
//    	NumDataFields=4
//    	FieldName_1=last
//    	FieldType_1=double
//    	FieldRegExSuffix_1=_last
//    	FieldName_2=volume
//    	FieldType_2=int
//    	FieldRegExSuffix_2=_volume
//    	FieldName_3=low
//    	FieldType_3=double
//    	FieldRegExSuffix_3=_low
//    	FieldName_4=hi
//    	FieldType_4=double
//    	FieldRegExSuffix_4=_hi
//    	
    	
        // Start stepping through the array from the beginning

            String prefix = null;
            String propRegex1st = null;
            String fieldRegExSuffix = null;
            String regex1st = null ;
            String replaceMeRegEx1 = null;            
            
            String regex2nd = null;
            String stDelimit = null;
            String endDelimit = null;
            	//changes by field
            String fieldType = null;
            String fieldName = null;

            String keySymbol = null;
            String searchFor = null;

            protected void initNonIndexPropValues() {

            	prefix = generatorProps.getProperty("Prefix_1");
                propRegex1st = generatorProps.getProperty("RegEx_1");
		        replaceMeRegEx1 = generatorProps.getProperty("RegExReplaceChars_1");
                fieldRegExSuffix = null;
                regex1st = null ;
                
                regex2nd = generatorProps.getProperty("RegEx_2");
                stDelimit = generatorProps.getProperty("StDelimeter");
                endDelimit = generatorProps.getProperty("EndDelimiter");
                //changes by field
                fieldType = "";
                fieldName = "";

                keySymbol = "";
                searchFor = "";
            	
            };
            
			public String getSearchFor() {
				return searchFor;
			}

			protected void setIndexDependentFields(int i) {
				dDPDTO.fieldName = generatorProps.getProperty("FieldName_" + i);
				dDPDTO.fieldType = generatorProps.getProperty("FieldType_" + i);
	            fieldRegExSuffix = generatorProps.getProperty("FieldRegExSuffix" + "_" + i);
				
			}
			/**
			 * must set prefix, keySymbol, feildRegExSuffix first
			 */
			protected void setSearchFor(int i) {
	            searchFor = prefix + keySymbol + fieldRegExSuffix;
			}
			public String getRegex1st() {
				return regex1st ;
			}
			
			/**
			 * must set searchFor first
			 */
			protected void setRegex1st() {

		        this.regex1st = propRegex1st.replaceAll(replaceMeRegEx1, searchFor);
				logger.debug("RegEx_1:" + this.regex1st);
				//this.regex1st = "(" + searchFor + generatorProps.getProperty("RegEx_1") ;
			}   

    } 	
    
	public String getFieldNamePropValue() {
		String strValue = null;
		
		ArrayList <StringBuffer> match = eHtmlParser.getDataMatchFromRegEx(dDPDTO.regex1st) ;
		//assertNotNull(match);	
		if (match!=null) {
			int j = 0;
			for (StringBuffer stringBuffer : match) {
				j++;
				if (j==1)
					strValue = 
							eHtmlParser.getStringValue(stringBuffer, dDPDTO.regex2nd,
									dDPDTO.stDelimit, dDPDTO.endDelimit);
				else
					logger.info("More Than One String Value:" + j + ":" + stringBuffer);
			}
		}
		
		logger.debug("RegEx1:RegEx2 " + dDPDTO.regex1st + ":" + dDPDTO.regex2nd);
		logger.debug("dDPDTO.FieldName Value: " + dDPDTO.keySymbol + ":" + strValue);
		
		return strValue; 
	}
	
	public Properties getGeneratorProps() {
		return generatorProps;
	}
	
}
