
/**
 *
 * @author benja
 */
public class RoomEntry {
    private String name;
    private int seats;
    
    public RoomEntry(){
    }
    public RoomEntry(String name, int seats){
        this.name = name;
        this.seats = seats;
    }
    public String getName(){
        return this.name;
    }
    public int getSeats(){
        return this.seats;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSeats(int seats){
        this.seats = seats;
    }
}
