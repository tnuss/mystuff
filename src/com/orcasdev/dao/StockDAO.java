package com.orcasdev.dao;

import java.util.Collection;

import com.orcasdev.equity.Stock;

public interface StockDAO {

	public abstract int addStock(Stock stock);

	public abstract int updateStock(Stock stock);

	public abstract Stock getStock(String symbol);

	public abstract String setType();
	
	public Collection getStockGroup(String name);
	
}