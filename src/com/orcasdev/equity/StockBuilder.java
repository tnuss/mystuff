package com.orcasdev.equity;

public class StockBuilder {
	
	Stock stock ;
	enum StockAttributeName {
		NAME, SYMBOL, STATUS, SECTOR;
	}
	
	/**
	 *  Constructor will create new Stock instance as well as
	 *   itself
	 */
	StockBuilder(){
	}
	
	public void initNewStock() {
		stock = null;
		stock = new Stock();
		stock.setInactive();
		//stock.setType(EquityType.STOCK);
	}

	public void setClassVar(String classVarName, String classVal) {
		// TODO Auto-generated method stub
		StockAttributeName stockAttr = this.getAttributeEnumName(classVarName);
		
		switch (stockAttr) {
			case NAME:
				stock.setName(classVal);
				break;
			case SYMBOL:
				stock.setSymbol(classVal);
				break;
			case SECTOR:
				stock.setSector(classVal);
				break;
			default:
				break;
		}
	}
	
	public Stock getStock() {
		return stock;
	}

	/**
	 * 
	 * @param nameValuePair 
	 *  Usage: "AttributeName=Value"
	 */
	public void setClassVar(String nameValuePair) {
		StringBuffer sb = new StringBuffer(nameValuePair);
		
		int i = sb.indexOf("=");
		
		String sName = sb.substring(0, i);
		String sVal = sb.substring(i+1, sb.length());
		
		setClassVar(sName,sVal);
		// TODO Auto-generated method stub
		
	}
	
	private StockAttributeName getAttributeEnumName (String classVarName) {
		
		StockAttributeName stockAttr = StockAttributeName.valueOf(classVarName.toUpperCase());
		return stockAttr;
		
	}

}  // e of class
