package tryout;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.URLDecoder;
 
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.Properties;

//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.SSLSession;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.*;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

public class HttpConn {

    public static String ENC = "UTF-8";
    //private static Log logger = LogFactory.getLog(HttpConn.class);
    private Properties props;
    // Declare data members
    static URLConnection connection = null;
    static boolean connectionGood = false; 

    public static void main(String[] args) {

        try {

            //openHttpConn("http://usseacctools.am.health.ge.com/cgi-bin/showTandFile/to/erxs/erx030s");

        	   /// --- the current day for SP100
            //openHttpConn("http://www.barchart.com/stocks/sp100.php?_dts1=change");
        	
        	// historical for GOOG'
        	openHttpConn("http://www.barchart.com/chart.php?sym=GOOG&t=BAR&size=M&v=1&g=1&p=WO&d=X&qb=1&style=technical&template=");
            
        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    /**

     * Constructor to open HTTP connection to the specified URL.
     * 
     * @param dfUrl
     *            URL of DrFirst.
     * @throws IOException
     *             The IO exception throws to the calling program.
     */
    public HttpConn(String dfUrl) throws IOException {

        // Retrieve system information from the property file
        props = new Properties();

        try {

            //props.load(getClass().getResourceAsStream(PROPERTIES_FILE));
            //cacertsFile = props.getProperty("cacertsFile");
           // String filename = cacertsFile.replace('/', File.separatorChar);

            String password = "changeit"; 

            //System.setProperty("javax.net.ssl.trustStore",filename);
            System.setProperty("javax.net.ssl.trustStorePassword",password);

           // HostnameVerifier hv = new HostnameVerifier() {
          //      public boolean verify(String urlHostName, SSLSession session) {
          //          if (logger.isDebugEnabled()) {
          //              logger.trace("Warning: URL Host: " + urlHostName + " vs. "
          //                      + session.getPeerHost());
          //          }
          //          return true;
          //      }
          //  };

            //HttpsURLConnection.setDefaultHostnameVerifier(hv);

            URL url = new URL(dfUrl);
            connection = (HttpURLConnection) url.openConnection();
            // connection.setRequestProperty("Content-Type", "text/xml;
            // charset=utf-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoInput(true);
            connection.setDoOutput(true);

        } catch (Exception e) {

            //logger.error("Failed to construct HttpConn.", e);
            throw new IOException(e.getMessage());

        }
    }

    public static void openHttpConn(String inUrl) throws IOException {

        URL url = new URL(inUrl);

        connection = url.openConnection();
        if (connection!=null) {
            connectionGood = true;
        }

        InputStreamReader inStrm = null;
        inStrm = new InputStreamReader(url.openStream()); ;

        BufferedReader in = new BufferedReader(inStrm);
        //BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        char [] kChars = new char [1000] ;

        int numCs = 0;
 
        StringBuffer inBuff = new StringBuffer() ;

        numCs = in.read(kChars, 0, 0);

        if (numCs!=-1) {
            int st = 0;
            int end = 1000;
            while (numCs!=-1) {
                
                numCs = in.read(kChars, st, end);

                if (numCs!=-1) {
                   inBuff.append(kChars, 0, numCs);
                }   
            }
  
            byte b[] = new byte [1] ;

           System.out.println(Calendar.getInstance().getTimeInMillis());
  
           for (int i=0;i<inBuff.length();i++) {

                 //System.out.print(inBuff.charAt(i));

                b[0] = (byte) inBuff.charAt(i);

                System.out.write(b, 0, 1);

             }

            System.out.flush();
            System.out.println(Calendar.getInstance().getTimeInMillis());

            // using charAt
            //1359763039557
            //1359763039684
  
            //use write
            //1359763141322
            //1359763141401


//            if (inBuff.length() > 0) {
//                String x = inBuff.toString();
//                System.out.print(x);
//            }
        }
        in.close();        

      

      /*  

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();

*/


        /*
        ByteArrayInputStream iByteStrm ;
        InputStream inStrm = connection.getInputStream();
        byte [] inBytes = new byte[40000];
        int i = inStrm.read(inBytes);
        //iByteStrm = (ByteArrayInputStream) connection.getInputStream();
        String xis = inBytes.toString() ;
        */

        int i = 0; 

    }

    public boolean isConnectionGood() {
        return connectionGood;
    }

    public void setConnectionGood(boolean connectionGood) {
        this.connectionGood = connectionGood;
    }
}


