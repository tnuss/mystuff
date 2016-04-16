package com.orcasdev.equity;

import static org.junit.Assert.*;

import java.util.Enumeration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StockTest {

	String ST_SYMBOL = "FRD" ;
	String ST_NAME = "Fred" ;
	String ST_TYPE = "STOCK" ;
	
	Equity stock = null;
	
	@Before
	public void setUp() throws Exception {
		
		stock = new Stock();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetGetName() {
		stock.setName(ST_NAME);
		assertEquals(ST_NAME,stock.getName());
	}

	@Test
	public void testSetGetSymbol() {
		stock.setSymbol(ST_SYMBOL);
		assertEquals(ST_SYMBOL,stock.getSymbol());
	}

	@Test
	public void testSetSymbol() {
		stock.setSymbol(ST_SYMBOL);
		assertEquals(ST_SYMBOL, stock.getSymbol());
	}

	@Test
	public void testGetStatus() {
		assertEquals(true, stock.isStatusInactive());
		
	}

	@Test
	public void testSetStatus() {
		stock.setActive();
		assertEquals(true,stock.isStatusActive());
		assertEquals(false,stock.isStatusInactive());
	}

	@Test
	public void testGetType() {
		
		stock.setType(EquityType.STOCK);
		assertEquals(EquityType.STOCK, stock.getType());
	}

	@Test
	public void testSetType() {
		
		stock.setType(EquityType.STOCK);
		assertEquals(EquityType.STOCK, stock.getType());
	}

}
