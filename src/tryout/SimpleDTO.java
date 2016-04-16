package tryout;

public class SimpleDTO {
	int ix = 2;
	String str = "Two";
	
	private static int I3= 3;
	
	SimpleDTO(){
		
	}
	
	void setIX (int i) {
		ix = i;
	}
	public String toString() {
		return ("ix=" + ix + "," + "str=" + str + ", I3=" + I3);
	}

}
