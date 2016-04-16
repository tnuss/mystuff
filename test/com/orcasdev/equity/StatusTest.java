package com.orcasdev.equity;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StatusTest {

	Status stat = null;
	
	@Before
	public void setUp() throws Exception {

		stat = new Status();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetActive() {
		stat.setActive();
		assertEquals(true, stat.isActive()) ;
		stat.setInactive();
		assertEquals(false, stat.isActive()) ;
	}

	@Test
	public void testSetInactive() {
		stat.setInactive();
		assertEquals(false, stat.isActive()) ;
		assertEquals(true, !stat.isActive()) ;
	}

	@Test
	public void testGetStatusCode() {
		stat.setInactive();
		assertEquals("I", stat.getStatusCode());
		stat.setActive();
		assertEquals("A", stat.getStatusCode());
	}

	@Test
	public void testGetStatusDescription() {
		stat.setInactive();
		assertEquals("Inactive", stat.getStatusDescription());
		stat.setActive();
		assertEquals("Active", stat.getStatusDescription());
	}

}
