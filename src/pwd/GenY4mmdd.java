package pwd;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GenY4mmdd {

    private static final String Y4MMDD_FORMAT= "yyyy-MM-dd";
    private static final String Y4MMDD_FORMAT_NO_DASH = "yyyyMMdd";
    // public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
 
    SimpleDateFormat startSDF = null;
    
    String Y4mmdd = null ;
    String Y4mmdd_noDash = null ;
    Timestamp tmStamp = null;
    
    public GenY4mmdd() {
        
    }
    
    public void setStartDate(Calendar calDate, String format) {
        startSDF = new SimpleDateFormat(Y4MMDD_FORMAT);
        Y4mmdd = startSDF.format(calDate.getTime());
        startSDF = new SimpleDateFormat(Y4MMDD_FORMAT_NO_DASH);
        Y4mmdd_noDash = startSDF.format(calDate.getTime());
    }
    
    public void setCurrGY4mmddDates() {
    	Calendar calDate = Calendar.getInstance();
    	startSDF = new SimpleDateFormat(Y4MMDD_FORMAT);
        Y4mmdd = startSDF.format(calDate.getTime());
        startSDF = new SimpleDateFormat(Y4MMDD_FORMAT_NO_DASH);
        Y4mmdd_noDash = startSDF.format(calDate.getTime());
    }
 
    public String getY4mmdd () {
        return Y4mmdd;
    }
    public String getY4mmdd_noDash () {
        return Y4mmdd_noDash;
    }
    
    public ArrayList<String> makeListY4mmddByWeek(Calendar fromDate, int numweeks) {
    
        ArrayList<String> alY4mmdd = new ArrayList<String>();
 
        if (fromDate == null) {
            fromDate = Calendar.getInstance();
            tmStamp = new Timestamp(fromDate.getTimeInMillis());
            
        }
            
            // this will set data time values... there are other set() methods
        //fromDate.set(Calendar.YEAR, 2012);
 
        setStartDate(fromDate,Y4MMDD_FORMAT);
        
        alY4mmdd.add(Y4mmdd);
 
        SimpleDateFormat sDF = new SimpleDateFormat(Y4MMDD_FORMAT);
        
        for (int i = 1; i<numweeks ; i++) {
            fromDate.add(Calendar.DAY_OF_YEAR,7);
            //addDays(fromDate,7);
            alY4mmdd.add(sDF.format(fromDate.getTime()));
        }
        
        return alY4mmdd;
        
    }
    
    public String currY4mmdd() {
        
        Calendar fromDate = Calendar.getInstance();
        setStartDate(fromDate,Y4MMDD_FORMAT);
        
        return Y4mmdd;
        
    }
    
    private void addDays(Calendar fromDate, int ndays) {
        fromDate.add(Calendar.DAY_OF_YEAR,ndays);
 
    }
    
    public static void  main(String arg[]) {
 
          GenY4mmdd gY4 = new GenY4mmdd();
          
          Calendar fDate = null;
          
          ArrayList <String> alStrDates = gY4.makeListY4mmddByWeek(fDate, 52);
          
          System.out.println("Now:" + gY4.startSDF.getCalendar().getTime().toString());
         // System.out.println(gY4.getY4mmdd());
          System.out.println("Today: " + gY4.currY4mmdd());
          System.out.print("Timestamp:" + gY4.tmStamp.toString());
          System.out.println("");
          
          int i = 1;
          for (String dispStr : alStrDates) {
              System.out.println(i++ + ") " + dispStr);
          }
 
          System.out.println("Y4mmdd_NoDash:" + gY4.getY4mmdd_noDash());
          //System.out.println(fDate.toString());
          
      }
    
    // end of class
    
}
