package infra;

import java.io.File;

//import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4Config {
    
    //String resourceDir = "resources";
    
    public Log4Config() {
        
        File propertiesFile = new File("resources", "log4j.properties");
        System.out.println("Absolute Dir: " + propertiesFile.getAbsolutePath() 
                + " Path: " + propertiesFile.getPath());

              //   this is Log4j specific
        PropertyConfigurator.configure(propertiesFile.toString());

    }

    public Log4Config(ApplicationProperties aprops) {
        
        
        // >>> tn --> need to beable to bring in property to set up logging..for instance
        //           ResourcesDir=resources, or ResourceDir=/asfd/resources
        
        File propertiesFile = new File(aprops.getResourcesDir(), "log4j.properties");
        
        System.out.println("Log4Config - Absolute Dir: " + propertiesFile.getAbsolutePath() 
                + " Path: " + propertiesFile.getPath() + " Parent: " + propertiesFile.getParent());
        
        PropertyConfigurator.configure(propertiesFile.toString());

        //Logger logger = Logger.getLogger(LoggingEx.class);

    }

}
