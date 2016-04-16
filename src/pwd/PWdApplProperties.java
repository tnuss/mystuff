/**
 * 
 */
package pwd;

import java.io.File;

import infra.ApplicationProperties;

/**
 * @author de040472
 *
 */
public class PWdApplProperties extends ApplicationProperties {

    /**
     * 
     */
    
    protected static PWdApplProperties pwdApplProps = null;
    // property names

    final static String PW_DIR_NAME = "PW_RESOURCES_DIR_NAME";
    
    // defaults property values
    final static String PW_RESOURCES_DIR = "resources";

    private PWdApplProperties(String[] args) {
        super(args);
        
        // TODO Auto-generated constructor stub
        
        if (appProps.getProperty(PW_DIR_NAME) == null) {
            appProps.put(PW_DIR_NAME, PW_RESOURCES_DIR);
        }
    }

    public static PWdApplProperties getApplicationProperties(String[] args) {
        
        if (pwdApplProps==null) 
            pwdApplProps = new PWdApplProperties(args);
        
//        return (PWdApplProperties) applProps;
        return pwdApplProps;
        
    }
    
    public String getPWdResourcesDir() {
        
        return appProps.getProperty(PW_DIR_NAME);
    }

    public void SetPWFileName(String x) {
        
    }
    
    public File getPWfile() {
        
        File f = null;
        
        return f;
    }
}
