package tryout;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class TryDouble {

	TryDouble() {
		;
	}
	
    public static void main(String[] args) throws IOException {
    
    	double d1 = 3;
    	double d2 = 2;
    	double d3 = 7;
    	
    	double r1 ;
    	double r2 ;
    	double r3 ;
    	
    	DecimalFormat df = new DecimalFormat();
    	df.setMaximumFractionDigits(2);
    	df.setMinimumFractionDigits(2);
    	df.setRoundingMode(RoundingMode.HALF_DOWN);
    	
    	//df.applyPattern("#,##0.000");
    	
    	r1 = 12345.1551 ;
    	r2 = .017 * 7;
    	r3 = (d2 / d1) * r1 ;

    	System.out.println("r1=" + df.format(r1)) ;
    	System.out.println("r2=" + df.format(r2)) ;
    	System.out.println("r3=" + df.format(r3)) ;
    	
    }	
}
