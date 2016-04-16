package com.orcasdev.equity;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StockBuilderTest {

	StockBuilder sb = null;

	@Before
	public void setUp() throws Exception {
		sb = new StockBuilder();
		sb.initNewStock();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateStockBuilder() {
		assertNotNull(sb);
	}

/*	@Test
	public void testSetAttributeXMLNode() {
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" 
				+ "<STOCK><Name>Google</Name><Symbol>GOOG</Symbol></STOCK>";
		sb.setAttributesXMLNode(xml);
		Stock stock = sb.getStock();
		assertTrue("Google".equalsIgnoreCase(stock.getName()));
		assertTrue("GOOG".equalsIgnoreCase(stock.getSymbol()));
	}
	*/
	@Test
	public void testSetAttributeClassVar() {
		String classVar = "NAME";
		String classVal = "Google" ;
	
		sb.setClassVar(classVar,classVal);
		assertTrue("Google".equals(sb.getStock().getName()));
	}

	@Test
	public void testSetAttributeNameValuePair() {
		String nameValuePair = "NAME=Google";
		
		sb.setClassVar(nameValuePair);
		
		assertTrue("Google".equals(sb.getStock().getName()));
	}

}
