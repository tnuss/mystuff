package infra;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ApplicationPropertiesTest {

	ApplicationProperties ap = null;
	
	@Before
	public void setUp() throws Exception {
		ap = ApplicationProperties.getApplicationProperties(null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetApplicationProperties() {
		assertNotNull(ap);
		//fail("Not yet implemented");
	}

	@Test
	public void testGetProperties() {
		Properties props = ap.getProperties();
		assertNotNull(props);
	}

	@Test
	public void testGetResourcesDir() {

		String rDir = ap.getResourcesDir();
		String absDir = ap.getApplAbsolutePath();
		String fileSep = ap.getApplFileSeparator() ;
		System.out.println("");
		System.out.println("Resources Dir:" + absDir + fileSep + rDir);
		String dDir = ap.getDataDir(false);
		System.out.println("Data Dir:" + absDir + fileSep + dDir);
		
		//fail("Not yet implemented");
	}

	@Test
	public void testLoadAppFileProps() {
		
		Properties props = ApplicationProperties.appProps;
		assertTrue (props.getProperty("DBLocation")!=null);
		
	}
}
