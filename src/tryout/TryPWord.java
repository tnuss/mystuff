package tryout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import infra.ApplicationProperties;
import infra.Log4Config;

import org.apache.log4j.Logger;

import pwd.PWdApplProperties;
import pwd.PWord;
import pwd.PWordListFile;

public class TryPWord {

    static Logger logger = null;
    static ApplicationProperties appProps = null;
    static PWdApplProperties pwApplProps = null;
     /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        appProps = ApplicationProperties.getApplicationProperties(args);

            // not sure what a specialized class does for us????
            // but it can be done
        pwApplProps = PWdApplProperties.getApplicationProperties(args);
        
        System.out.println("-App Props done-");
        
        Log4Config log4jAdapt = new Log4Config(appProps);
        
        logger = Logger.getLogger(TryPWord.class);

        logger.info("Starting Logging: TryPWord");
        
        logger.info("Resources Dir: " + appProps.getResourcesDir());
        logger.info("Appl Absolute Dir: " + appProps.getApplAbsolutePath());

        logger.info("PW Resources Dir: " + pwApplProps.getPWdResourcesDir());
        logger.info("PW Appl Absolute Dir: " + pwApplProps.getApplAbsolutePath());

        // investigateFilePaths();
        
        /*
            Here we go
         */
            // this is the final file with pw & date
        PWordListFile plFile = new PWordListFile();
        String fileSeparator = pwApplProps.getApplFileSeparator();
            // this is input file
        String pwFileInNm = "AbcIn.txt" ;
        String pwFileOutNm = "AbcOut.txt" ;
        
        File pwInFile = new File(pwApplProps.getPWdResourcesDir() + fileSeparator + pwFileInNm);

        if (pwInFile != null && pwInFile.length() > 0) {
            BufferedReader br = new BufferedReader(new FileReader(pwInFile));
            String line = new String();
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        }
        
        PWordListFile pwdListFile = new PWordListFile();
        ArrayList<PWord> aL = null ;
        try {
            
            //  >> tn SHOULD USE FULL FILE NAME HERE or Not??? 
            //        Or just best to move within the structure of the application
            pwdListFile.setFileName(pwApplProps.getPWdResourcesDir() + fileSeparator + pwFileInNm);
            aL = pwdListFile.loadIntoItemList();
            
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
       
        
            // generate and add dates
        if (aL!=null && aL.size() > 0) {
            // add Dates to each one....
            ;
        }

        // out file...???
        try {

            plFile.setFileName(pwApplProps.getPWdResourcesDir() + fileSeparator + pwFileOutNm) ;
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    static void investigateFilePaths() {

        File f = new File("");
        
        System.out.println("ABSOLUTE: " + f.getAbsolutePath());
        
        f = new File(f.getAbsoluteFile(),"");
        
        System.out.println("Path: " + f.getPath());
        System.out.println("Name File: " + f.getName());
        System.out.println("Name Parent: " + f.getParent());
        
        String s[] = f.list();
        for (String string : s) {
            System.out.println("Dir File: " + string);
        }
        
    }
    // end of class
}
