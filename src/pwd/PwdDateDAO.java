package pwd;

import java.sql.Date;

class PWDate {
    
    public PWDate() {
        
    }
}

public interface PwdDateDAO {
    
    PWDate getPwdByDate(Date d);
    PWDate getPwdByDateLong(long lDate);

}
