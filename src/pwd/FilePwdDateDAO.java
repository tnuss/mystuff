package pwd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;

import org.apache.log4j.Logger;

//import tryout.LogInherit;

//import PWDate;

public class FilePwdDateDAO implements PwdDateDAO {

    // logger here --- root class has to setup logger...
    Logger logger = Logger.getLogger(FilePwdDateDAO.class);
    
    private String basePath;
    PWDate pwd = null;
    ArrayList <String>pwdAList = new ArrayList<String>(); 
    
    public FilePwdDateDAO(String basePath) {
      this.basePath = basePath;
    }
    
    public void loadFile() throws Exception {
        
        try {
        
          FileReader fr = new FileReader(basePath);
          BufferedReader br = new BufferedReader(fr);
          
          String line = null;
          
          while ((line=br.readLine()) != null) {
              pwdAList.add(line);
          }
      
        } catch (Exception e) {
            exceptionHandler(e);
        }
            
      }
    
    @Override
    public PWDate getPwdByDate(Date d) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PWDate getPwdByDateLong(long lDate) {
        // TODO Auto-generated method stub
        return null;
    }
    
    private void exceptionHandler(Exception inE) throws Exception {
        
        boolean exceptionIdentified = false;
        
        System.out.println("FilePwdDateDAO Exception " + inE.getMessage());
        
        if (!exceptionIdentified && (inE instanceof FileNotFoundException)) {
           System.out.println("File Not Found: " + basePath);
           exceptionIdentified = true;
        }
        
        // exception was not identified
        if (!exceptionIdentified) {
            inE.printStackTrace();
        }
        
        
    }

}
