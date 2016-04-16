package com.orcasdev.service;

import static org.junit.Assert.*;

import java.io.File;

import infra.ApplicationProperties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileIOServiceTest {

	static final String SP100 = "SP100";
	static final String FILENAME_COMP_STRING = "q-sp100-20140801.html";
	static final String TEST_DAY = "20140801";

	Logger logger = Logger.getLogger(FileIOServiceTest.class.getClass());
	ApplicationProperties ap = null;
	ApplContext parserContext ;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	
		ap = ApplicationProperties.getApplicationProperties(null);
		parserContext = new ApplContext(SP100,ap);
	    parserContext.setTimeDependences(TEST_DAY, true);
	       
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWriteDayDataFile() {

		FileIOService urlFS = new FileIOService(parserContext);
		
		StringBuffer sb = new StringBuffer("Testing String Buffer -- Testing String Buffer</end>");
		
		urlFS.writeDayDataFile(sb, true);
		
		File testFile = new File(parserContext.getTestDir(false));
		
		assertTrue(testFile.exists());

	}
	
	@Test
	public void testSetSourceURL() {
		
		FileIOService urlFS = new FileIOService(parserContext);
		String nameURL = "http://www.google.com";
		
		urlFS.setSourceURL(nameURL);
		assertTrue(nameURL.equals(urlFS.getSourceURL()));
		
	}

}
