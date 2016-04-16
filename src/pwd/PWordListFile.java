package pwd;

import infra.ItemFileSuper;
import infra.ItemListIF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

//import tryout.LogInherit;


    /**
     *  Class to manage PWordListFile data
     *  Functions:
     *     1) Get PWord by date
     *     2) Set Pword by date
     *     3) Get Current days PWord
     *     4) Need a FACTORY to create by PWordList type
     *        in this case: PWordListFile
     *        future: PWordListDB
     *     
     */

public class PWordListFile extends ItemFileSuper implements ItemListIF<PWord> {

    // logger here --- root class has to setup logger...
    Logger logger = Logger.getLogger(PWordListFile.class);

    String fullFileName ;
    File pwdFile = null;
    ArrayList<PWord>pwA = null;
    
    /**
     *  simple constructor
     */
    public PWordListFile () {}  

    /**
     *  Load Array list for use
     * @throws IOException 
     */
    
    public ArrayList<PWord> loadIntoItemList() {
//    public ArrayList<PWord> loadPWList() {
        // TODO Auto-generated method stub
        
        pwdFile = new File(fullFileName);

        try {
        if (pwdFile != null && pwdFile.length() > 0) {
            
            BufferedReader br;
                
             br = new BufferedReader(new FileReader(pwdFile));
             String line = new String();
             StringBuffer sb = new StringBuffer();
            
             while ((line = br.readLine()) != null) {
                    System.out.println(line);
             }
            
             br.close();
                
        }
        
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return pwA;
    }
 
    /**
     *   savePwordTofile
     *   not implemented
     */
    
    public void saveItemList() throws Exception {
        // TODO Auto-generated method stub
 
        
    }

    /**
     *   not implemented.
     */
    public void saveItemList(ArrayList<PWord> pwList) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * 
     * @param set file name to be used by list
     * @throws Exception
     */
    
    public void setFileName(String fname) throws Exception {
        fullFileName = fname;
        
        try {
        
            if (fname==null) {
                Exception em;
                em = new Exception("Need full file name: " + fname);
                throw em;
            }
        } catch (Exception em) {
            em.printStackTrace();
        }
    }

    @Override
    public ArrayList<PWord> loadItemList() {
        return null;
    }


}
