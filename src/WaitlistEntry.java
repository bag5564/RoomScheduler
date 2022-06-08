import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author benja
 */
public class WaitlistEntry {
    private String faculty;
    private Date date;
    private int seats;
    private Timestamp timestamp;
    
    public WaitlistEntry(){
    }
    public WaitlistEntry(String faculty, Date date, int seats){
        this.setFaculty(faculty);
        this.setDate(date);
        this.setSeats(seats);
        
    }
    public WaitlistEntry(String faculty, Date date, int seats, Timestamp timestamp){
        this.setFaculty(faculty);
        this.setDate(date);
        this.setSeats(seats);
        this.setTimestamp(timestamp);
        
    }
    public void setFaculty(String faculty){
        this.faculty = faculty;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public void setSeats(int seats){
        this.seats = seats;
    }
    public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    }
    public String getFaculty(){
        return this.faculty;
    }
    public Date getDate(){
        return this.date;
    }
    public int getSeats(){
        return this.seats;
    }
    public Timestamp getTimestamp(){
        return this.timestamp;
    }
}

