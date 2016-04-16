package pwd;

public class PWord {

    String pWord = null;
    StringBuffer pWBuff = null; 
    
    public PWord() {
        
    }
    
    public void setPWord (StringBuffer pwb) {
        pWord = pwb.toString();
        pWBuff = new StringBuffer(pwb);
    }
    
    public void setPWord (String pw) {
        pWord = new String(pw);
        pWBuff = new StringBuffer(pw);
    }
    
    public String getPWord () {
        return new String(pWord);
    }
 
}
    

