package com.orcasdev.equity;

import java.lang.reflect.Type;
import java.sql.Timestamp;

public class DayDataDTO  {

	protected double last = 0;
	protected double hi = 0;	
	protected double low = 0;	
	protected double change = 0;
	protected int volume = 0;
    protected String dataDateString = null ;
    protected Timestamp dataTS = null ; // YYYYMMDDHHMMSSmmm 
	
    public DayDataDTO() {
		
	}

    public String toString() {
		return "DayDataDTO.toString" + dataDateString
			+ ", last=" + last + ", hi=" + hi + ", low=" + low + ", volume=" + volume
			+ ", Change=" + change + ", dataDateString=" + dataDateString 
			+ ", dataTS=" + dataTS + "]";
	}

}
