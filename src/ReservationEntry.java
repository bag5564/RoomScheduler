import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author benja
 */
public class ReservationEntry {
    private String faculty;
    private String room;
    private Date date;
    private int seats;
    private Timestamp timestamp;
    
    public ReservationEntry(){
    }
    public ReservationEntry(String faculty, String room, Date date, int seats){
        this.setFaculty(faculty);
        this.setRoom(room);
        this.setDate(date);
        this.setSeats(seats);
        
    }
    public void setFaculty(String faculty){
        this.faculty = faculty;
    }
    public void setRoom(String room){
        this.room = room;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public void setSeats(int seats){
        this.seats = seats;
    }
    public String getFaculty(){
        return this.faculty;
    }
    public String getRoom(){
        return this.room;
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
