package com.orcasdev.equity;

public abstract class Equity {
	
	private String name  ;
	private String symbol;
	protected Status status ;
	private EquityType type ;
	private String sector = null;
	private int quotePrecision = 4;
	private int quoteNumDecimals = 2;
	private boolean noDataFound = false;
		
	@Override
	public String toString() {
		return "Equity [name=" + name + ", symbol=" + symbol + ", status="
				+ status + ", type=" + type + ", sector=" + sector
				+ ", quotePrecision=" + quotePrecision + ", quoteNumDecimals="
				+ quoteNumDecimals + ", noDataFound=" + noDataFound + "]";
	}
	public boolean isNoDataFound() {
		return noDataFound;
	}
	public void setNoDataFound(boolean noDataFound) {
		this.noDataFound = noDataFound;
	}
	public int getQuoteNumDecimals() {
		return quoteNumDecimals;
	}
	public int getQuotePrecision() {
		return quotePrecision;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSymbol() {
		return symbol;
	}
		public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public boolean isStatusActive() {
		return status.isActive();
	}

	public boolean isStatusInactive() {
		return !status.isActive();
	}

	public void setActive() {
		if (status==null){
			status = new Status();
		}
		status.setActive();;
	}
	
	public void setInactive() {
		if (status==null){
			status = new Status();
		}
		status.setInactive();;
	}

	public EquityType getType() {
		return type;
	}
	
	protected void setType(EquityType type) {
		this.type = type;
	}
	
	public String getSector() {
		return sector;
	}
	
	public void setSector(String sector) {
		this.sector = sector;
	}
}