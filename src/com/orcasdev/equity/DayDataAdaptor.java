package com.orcasdev.equity;

import infra.GenY4mmdd;

import java.sql.Timestamp;
import java.text.DecimalFormat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import tryout.SimpleDTO;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DayDataAdaptor {
	
	private DayDataDTO ddDTO = new DayDataDTO();
	
	public static String LAST = "last";
	public static String HI = "hi";
	public static String LOW = "low";
	public static String CHANGE = "change";
	public static String VOLUME = "volume";
	public static String DATA_DATE = "displaytime";
	public static String DATA_TS = "datats";

	private DecimalFormat df = null;
	private EquityType dataType = null;
	private String dataStatus = null;

	public String getDataStatus() {
		return dataStatus;
	}

	private static String PARCING_ERROR = "PARCING_ERROR";
	private static String NO_DATA_ERROR = "NO_DATA_ERROR";
	
	public DayDataAdaptor(EquityType equityType, DecimalFormat decimalFormat) {
		dataType = equityType;
		df = decimalFormat;
	}

	public void resetDayDataAdaptor() {
		
		ddDTO.change = 0;
		ddDTO.dataDateString = null;
		ddDTO.dataTS = null;
		ddDTO.hi = 0;
		ddDTO.last = 0;
		ddDTO.low = 0;
		ddDTO.volume = 0;
		setNoDataError();
		
	}

	public double getChange() {
		return ddDTO.change;
	}

	
	public void setChange(double change) {
		ddDTO.change = change;
	}	

	public void setChange(String doubleNum) {
		try {
			ddDTO.change = Double.valueOf(doubleNum);
		}
		catch(NumberFormatException ne) {
			ddDTO.change = 0 ;
		}
	}
	
	public String getChangeString() {
		return df.format(ddDTO.change);
	}

	public void setDecimalFormat(DecimalFormat decimalFormat) {
		df = decimalFormat;
	}
	
	public String getLastString() {
		return df.format(ddDTO.last);
	}

	public double getLast() {
		return ddDTO.last;
	}

	public void setLast(double last) {
		ddDTO.last = last;
		if (last == 0)
			this.setNoDataError();
	}

	public void setLast(String last) {
		try {
			ddDTO.last = Double.valueOf(last);
		}
		catch(NumberFormatException ne) {
			setLast(0) ;
		}
	}

	public String getHiString() {
		return df.format(ddDTO.hi);
	}
	
	public double getHi() {
		return ddDTO.hi;
	}

	public void setHi(double hi) {
		ddDTO.hi = hi;
	}

	public void setHi(String doubleNum) {
		try {
			ddDTO.hi = Double.valueOf(doubleNum);
		}
		catch(NumberFormatException ne) {
			ddDTO.hi = 0 ;
		}
	}

	public double getLow() {
		return ddDTO.low;
	}

	public String getLowString() {
		return df.format(ddDTO.low);
	}

	public void setLow(double low) {
		ddDTO.low = low;
	}

	public void setLow(String doubleNum) {
		try {
			ddDTO.low = Double.valueOf(doubleNum);
		}
		catch(NumberFormatException ne) {
			ddDTO.low = 0 ;
		}
	}
	
	public int getVolume() {
		return ddDTO.volume;
	}

	public void setVolume(int volume) {
		if (volume == 0)
			this.setNoDataError();
		
		ddDTO.volume = volume;
	}

	public void setDateTodayString() {
		
		GenY4mmdd genY4Date = new GenY4mmdd();
		genY4Date.setCurrGY4mmddDates();
		String testDay = genY4Date.getY4mmdd_noDash();
		setDateString(testDay);
		
	}

	public void setDateString(String yyyymmdd) {
		
		ddDTO.dataDateString = yyyymmdd;
		//return dataDateString;
	}

	public String getDateString() {
		return ddDTO.dataDateString;
	}
	
	public void setDataTS(Timestamp tS) {
		ddDTO.dataTS = tS;
	}
	
	public Timestamp getDataTS() {
		return ddDTO.dataTS;
	}

	public void setParcingError() {
		dataStatus = PARCING_ERROR;
	}
	
	public boolean isParcingError() {
		boolean bF = false;
		if (dataStatus!=null) {
			if (dataStatus.equals(PARCING_ERROR))
				bF=true;
		}
		return bF ;
	}
		
	public void setNoDataError() {
		dataStatus = NO_DATA_ERROR;
	}
	
	public boolean isNoDataError() {
		boolean bF = false;
		if (dataStatus!=null) {
			if (dataStatus.equals(NO_DATA_ERROR))
				bF=true;
		}
		return bF ;
	}

	public boolean isError() {
		return (dataStatus!=null);
	}

	@Override
	public String toString() {
		return ddDTO.toString();
//		return "DayDataAdaptor [dataDateString=" + ddDTO.dataDateString + ", Type=" + dataType
//			+ ", last=" + ddDTO.last + ", hi=" + ddDTO.hi + ", low=" + ddDTO.low + ", volume=" + ddDTO.volume
//			+ ", Change=" + ddDTO.change + ", dataTS=" + ddDTO.dataTS + "]";
	}

	public void setDayDataQuote(double hi2, double low2, double last2,
		double change2, int volume2) {
		ddDTO.hi = hi2 ;
		ddDTO.low = low2;
		ddDTO.last = last2;
		ddDTO.volume = volume2;
		ddDTO.change = change2;
	}

	public void setDayDataDTO(DayDataDTO dayDataDTO) {
			ddDTO.hi = dayDataDTO.hi;
			ddDTO.low = dayDataDTO.low;
			ddDTO.last = dayDataDTO.last;
			ddDTO.volume = dayDataDTO.volume;
			ddDTO.change = dayDataDTO.change;
			ddDTO.dataDateString = dayDataDTO.dataDateString;
			ddDTO.dataTS = dayDataDTO.dataTS;
		}
	
	public DayDataDTO cloneDayDataDTO() {
		
		DayDataDTO newDDD = new DayDataDTO();
		newDDD.hi = ddDTO.hi;
		newDDD.low = ddDTO.low;
		newDDD.last = ddDTO.last;
		newDDD.volume = ddDTO.volume;
		newDDD.change = ddDTO.change;
		newDDD.dataDateString = ddDTO.dataDateString;
		newDDD.dataTS = ddDTO.dataTS;
		
		return newDDD;
	}
	
	/**
	 * Returns JSON representation of DayDataDTO structure
	 * @return
	 */
	public String getGSONStringDTO() {

		Gson gson = getGSONObject();

		String gsonDTO = gson.toJson(ddDTO);
		//System.out.println("DayData as GSON: " + gsonDTO);
		
		return gsonDTO;
			
	}

	/**
	 * 
	 * @param gsonDTOString
	 * JSON representation of DayDataDTO structure
	 */
	public void setDTOFromGSONString(String gsonDTOString) {

		Gson gson = getGSONObject();

		DayDataDTO dto2 = gson.fromJson(gsonDTOString, DayDataDTO.class);
		setDayDataDTO(dto2);
		
		System.out.println(this.toString());
		
	}

	/**
	 * @return gson object
	 */
	private Gson getGSONObject() {
		Gson gson = new GsonBuilder()
        .disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .setPrettyPrinting()
        .serializeNulls()
        .create();
		return gson;
	}

	public DayDataDTO getDataDTO() {
		// TODO Auto-generated method stub
		return ddDTO;
	}


}
