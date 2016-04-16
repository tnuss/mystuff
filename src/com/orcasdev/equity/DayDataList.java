package com.orcasdev.equity;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class DayDataList {
	
	//String lFileName = null;
	StorageType storageType = null;
	String listName = null;
	String listKey = null;
	//String lastUpdateTime = null;
	DayDataAdaptor dayData = null;
	
	HashMap <String,DayDataAdaptor> dayDataDTOList = new HashMap<String,DayDataAdaptor>(); 	
	
    // logger here --- root class has to setup logger...
    Logger logger = Logger.getLogger(this.getClass());

    /**
     * 
     * @param listName ex: SP100, 20140801 (date)
     * @param storeType
     */
	public DayDataList(String listName, StorageType storeType) {
	        this.storageType = storeType;
	        this.listName = listName;
	}

	public int size() {
		return dayDataDTOList.size();
	}

	public StorageType getStorageType() {
		return storageType;
	}

	public void add(String key, DayDataAdaptor dataDTO) {
		
		dayDataDTOList.put(key, dataDTO);
		
	}

	public DayDataAdaptor getDayDataDTO(String key) {
		
		DayDataAdaptor dDDTO= null;
		
		if (dayDataDTOList.containsKey(key)) 
			dDDTO = dayDataDTOList.get(key);
			
		return dDDTO;
	}

	public String getListKey() {
		return listKey;
	}

	public void setListKey(String listKey) {
		this.listKey = listKey;
	}

}  // eoc
