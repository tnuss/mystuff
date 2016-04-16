package pwd;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
 
 
public class PWordAndDate {
    
    private static final String Y4MMDD_FORMAT= "yyyy-MM-dd";
 
    private PWord pwInstance = null;
    private long milliTime = -1;
 
    // String formatedTime = null;
    // Date sqlDate = null;
    // Timestamp sqlTS = null;
    
    
    public PWordAndDate (PWord pw, Date sqld) {
        pwInstance = pw;
        milliTime = sqld.getTime();
 
    }
    
    public PWordAndDate (PWord pw, long time) {
        pwInstance = pw;
        milliTime = time;
    }
 
    public PWordAndDate (PWord pw, Timestamp ts) {
        pwInstance = pw;
        milliTime = ts.getTime();
    }
    
    public String getPWord (){
        
        return pwInstance.getPWord();
    }
 
    public long getDateInMillis (){
        
        return milliTime;
        
    }
 
    public String getY4mmdd (){
        
        //java.sql.Date sqlDate = new java.sql.Date(javaTime);
        //System.out.println("The SQL DATE is: " + sqlDate.toString());
        
        if (milliTime < 0)
            milliTime = 0;
        
        SimpleDateFormat startSDF = new SimpleDateFormat(Y4MMDD_FORMAT);
 
        return startSDF.format(milliTime);
    
    }
    
    public String toString() {
        return (pwInstance.pWord + "|" + getY4mmdd() + "|" + milliTime) ;
    }
    
    
    // end of class
}
