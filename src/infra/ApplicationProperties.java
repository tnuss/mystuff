package infra;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author de040472
 * 
 *  Function:
 *  1) Will load default properties via command line
 *  2) Has access to system properties
 *  3) Has access to environmental 'properties' Map structure
 *  
 */
public class ApplicationProperties {
    
    // property names

    final static String RESOURCES_DIR_PROP_NAME = "ResourcesDir";
    final static String DATA_DIR_PROP_NAME = "DataDir";
    final static String DATA_BK_DIR_PROP_NAME = "DataBkDir";
    final static String APPL_ABOSLUTE_PATH = "ApplAbsolutePath";
    final static String APPL_PROPS_FILENAME = "application.props";
    final static String TEST_DATA_DIR_PROP_NAME = "TestDataDir";
    final static String TEST_DATA_BK_DIR_PROP_NAME = "TestDataBkDir";
    final static String ISWEBAPP_PROP_NAME = "IsWebApp";
    final static String APP_DEPLOY_DIR_PROP_NAME = "AppDirDeploy";
    
    // defaults property values
//    final static String RESOURCES_DIR = "resources";
//    final static String DATA_DIR = "data";
//    final static String TEST_DATA_DIR = DATA_DIR + "\\" + "testdata";
//    final static String DATA_BK_DIR = DATA_DIR + "\\data_bk";
//    final static String TEST_DATA_BK_DIR = TEST_DATA_DIR + "\\" + "testdata_bk";
    		// LINUX
    final static String DATA_DIR = "data";
    final static String TEST_DATA_DIR = DATA_DIR + "/" + "testdata";
    final static String DATA_BK_DIR = DATA_DIR + "/data_bk";
    final static String TEST_DATA_BK_DIR = TEST_DATA_DIR + "/" + "testdata_bk";

    final static String RESOURCES_DIR = "resources";
    final static String APPL_PROPERTIES_FNAME = "app.properties";
    
    protected static ApplicationProperties applProps = null;
    protected static Properties appProps = new Properties();
    protected static Properties sysProps = new Properties();
    protected static Map<String,String> envMap = null;
    
    static String [] createArgs = null;
    ByteArrayInputStream iByteStrm ;
    
   
    /**
     * 
     * @param args
     * 
     */
    protected ApplicationProperties(String[] args) {

        if (args!=null) {
            createArgs = args;
            loadPropArgs();
        }

        loadSysProperties();
        
        loadDefaultProps();
        
        loadAppFileProps();
        
    }
    
    private void loadAppFileProps() {

 		String rDir = getResourcesDir();
		String absDir = getApplAbsolutePath();
		String fileSep = getApplFileSeparator() ;
		String appFileName = absDir + fileSep + rDir + fileSep + APPL_PROPERTIES_FNAME;

		System.out.println("");
		System.out.println("appPropsFileName: " + appFileName);
		System.out.println("Catalina Base: " + sysProps.getProperty("catalina.base"));
		
		FileInputStream input;
		try {
			
			input = new FileInputStream(appFileName);
			// load a properties file
			appProps.load(input);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
     * 
     * @param command line args ...application properties values
     * @return instance of Application properties...singleton type
     */
    
    public static ApplicationProperties getApplicationProperties(String[] args) {
        
        if (applProps==null) 
            applProps = new ApplicationProperties(args);
        
        return applProps;
        
    }
    
    public Properties getProperties() {
        return appProps;
    }
    
    
    /**
     * Load Default Properties
     */
    static void loadDefaultProps() {
     
           // resources dir name
     appProps.put(RESOURCES_DIR_PROP_NAME, RESOURCES_DIR);
            
     // data dir name
     appProps.put(DATA_DIR_PROP_NAME, DATA_DIR);
     appProps.put(DATA_BK_DIR_PROP_NAME, DATA_BK_DIR);

     	// data dir name
     appProps.put(TEST_DATA_DIR_PROP_NAME, TEST_DATA_DIR);
     appProps.put(TEST_DATA_BK_DIR_PROP_NAME, TEST_DATA_BK_DIR);
     
     	String isWebApp = appProps.getProperty(ISWEBAPP_PROP_NAME,"N");
		
	    String appFileName = "";
	    String absDir="";
		String currDir = "";
		
	    if (isWebApp.equals("Y")) {
	    	currDir = sysProps.getProperty("catalina.base") + "/" + 
	              appProps.getProperty(APP_DEPLOY_DIR_PROP_NAME);
	    }
	    else {
	    	File f = new File("");
	    	currDir = f.getAbsolutePath();
	    }

	    System.out.println("*Referencing Dir: " + currDir);
    	appProps.put(APPL_ABOSLUTE_PATH, currDir);
        
    }
    
    /**
     *   read thru command args looking for specific properties and fill them
     */
    void loadPropArgs() {
        
        String eSign = "=";
        
        for (int i = 0; i < createArgs.length; i++) {
            
            if (createArgs[i].contains(RESOURCES_DIR_PROP_NAME)
            || createArgs[i].contains(ISWEBAPP_PROP_NAME)
            || createArgs[i].contains(APP_DEPLOY_DIR_PROP_NAME))
            
            	if (createArgs[i].contains(eSign)) {
            		loadPropViaBytes(i);
            }
            
		//            if (createArgs[i].contains(ISWEBAPP_PROP_NAME)
		//            && createArgs[i].contains(eSign)) {
		//                loadPropViaBytes(i);
		//            }
		//            
		//            if (createArgs[i].contains(APP_DEPLOY_DIR_PROP_NAME)
		//            & createArgs[i].contains(eSign)) {
		//            	loadPropViaBytes(i);
		//            }
            
            
        }
        System.out.println("WebAppPropertyValue: " + appProps.getProperty(ISWEBAPP_PROP_NAME));
        System.out.println("AppDirDeploy: " + appProps.getProperty(APP_DEPLOY_DIR_PROP_NAME));
    }
    
    /**
     * 
     *    Loads a property that was defined as part of input args
     *    
     * @param i integer into createArgs string array
     *  
     */
    void loadPropViaBytes(int i) {

        //Properties tmpP = new Properties();
        iByteStrm = new ByteArrayInputStream(createArgs[i].getBytes());
        
        try {
            
            //tmpP.load(iByteStrm);
            //Object x = tmpP.get(createArgs[i]);
            
            appProps.load(iByteStrm);
            iByteStrm.close();
        
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public String getResourcesDir() {
    
        return appProps.getProperty(RESOURCES_DIR_PROP_NAME);
    }
    
    public String getDataDir(boolean isBackup) {
        
    	if (isBackup)
            return appProps.getProperty(DATA_BK_DIR_PROP_NAME);
    	else
            return appProps.getProperty(DATA_DIR_PROP_NAME);
    }
    
    public String getTestDataDir(boolean isBackup) {
        
    	if (isBackup)
            return appProps.getProperty(TEST_DATA_BK_DIR_PROP_NAME);
    	else	
    		return appProps.getProperty(TEST_DATA_DIR_PROP_NAME);
    }

    public String getApplAbsolutePath() {
        
        return appProps.getProperty(APPL_ABOSLUTE_PATH);
    }

        /**
         * 
         * @return file system string "/" Linux? or "\" Windows
         */
    public String getApplFileSeparator() {
        
        return sysProps.getProperty("file.separator");
    }
    
    /**
     *  Load System and ENV properties
     */
    private void loadSysProperties() {
        
        System.out.println("\n====NEED Log FOR SYS PROPS================================================\n");
        sysProps = System.getProperties();
        sysProps.list(System.out);
        
        System.out.println("\n====Need Log for ENV props================================================\n");
        envMap = System.getenv();
        for (String envName : envMap.keySet()) {
            System.out.format("%s=%s%n", envName, envMap.get(envName));
        }
    
    }
    
    public Properties getSysProperties() {
        return sysProps;
    }
    
    public Map<String,String> getEnvProperties() {
        return envMap;
    }
    
    // end of class
}
