package infra;

import java.util.ArrayList;

/**
 * 
 * @author de040472
 *  Class to add common implementations for working with files
 */
public class ItemFileSuper  {

    String fullFileName = null;

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

}
