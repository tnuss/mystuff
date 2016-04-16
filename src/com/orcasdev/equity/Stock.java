package com.orcasdev.equity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
 
/**
 * 
 * @author tnuss
 *     Stock DTO
 */
public class Stock extends Equity {
	
	private int numDecimals = 2;
	private DecimalFormat dfQuote = new DecimalFormat();
	
	public Stock() {
		super();
		this.status = new Status();
		status.setInactive();
		this.setType(EquityType.STOCK);
		
		setQuoteAttributes();
	
	}
	
	public DecimalFormat getDfQuote() {
		return dfQuote;
	}

	private void setQuoteAttributes() {

		dfQuote.setMaximumFractionDigits(numDecimals);
		dfQuote.setMinimumFractionDigits(numDecimals);
		dfQuote.setRoundingMode(RoundingMode.HALF_DOWN);
		
	}
	
}
