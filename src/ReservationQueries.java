
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author benja
 */
public class ReservationQueries {
    private static Connection connection;
    private static PreparedStatement checkRoomReservationByDate;
    private static PreparedStatement addReservationEntry;
    private static PreparedStatement getReservationsByDate;
    private static PreparedStatement getReservationsByFaculty;
    private static PreparedStatement getReservationsByFacultyAndDate;
    private static PreparedStatement deleteReservation;
    private static PreparedStatement getReservationsByRoom;
    private static ResultSet resultSet;
    
    public static boolean checkRoomReservationByDate(String room, Date date)
    {
        connection = DBConnection.getConnection();
        boolean roomAvailable = false;
        try
        {
            //Returns true if the room is already reserved for given date
            checkRoomReservationByDate = connection.prepareStatement("select room from reservations where room = (?) and date = (?)");
            checkRoomReservationByDate.setString(1, room);
            checkRoomReservationByDate.setDate(2, date);
            resultSet = checkRoomReservationByDate.executeQuery();
            if(resultSet.next() == false)
            {
                roomAvailable = true;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return roomAvailable;
        
    }
    public static void addReservationEntry(ReservationEntry ReservationEntry)
    {
        connection = DBConnection.getConnection();
        try
        {
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            addReservationEntry = connection.prepareStatement("insert into reservations (faculty, room, date, seats, timestamp) values (?,?,?,?,?)");
            addReservationEntry.setString(1, ReservationEntry.getFaculty());
            addReservationEntry.setString(2, ReservationEntry.getRoom());
            addReservationEntry.setDate(3, ReservationEntry.getDate());
            addReservationEntry.setInt(4, ReservationEntry.getSeats());
            addReservationEntry.setTimestamp(5, currentTimestamp);
            addReservationEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    public static ArrayList<ReservationEntry> getReservationsByFaculty(String name)
    {
        connection = DBConnection.getConnection();
        ArrayList<ReservationEntry> reservations = new ArrayList<ReservationEntry>();
        try
        {
            //get all room reservations made for given faculty ordered by date
            getReservationsByFaculty = connection.prepareStatement("select * from reservations where faculty = (?) order by date");
            getReservationsByFaculty.setString(1, name);
            resultSet = getReservationsByFaculty.executeQuery();
            while(resultSet.next())
            {
                reservations.add(new ReservationEntry(
                    resultSet.getString("faculty"),
                    resultSet.getString("room"),
                    resultSet.getDate("date"),
                    resultSet.getInt("seats")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return reservations;
        
    }
    public static ArrayList<ReservationEntry> getReservationsByDate(Date date)
    {
        connection = DBConnection.getConnection();
        ArrayList<ReservationEntry> reservations = new ArrayList<ReservationEntry>();
        try
        {
            //get all room reservations made for given date
            getReservationsByDate = connection.prepareStatement("select * from reservations where date = (?)");
            getReservationsByDate.setDate(1, date);
            resultSet = getReservationsByDate.executeQuery();
            while(resultSet.next())
            {
                reservations.add(new ReservationEntry(
                    resultSet.getString("faculty"),
                    resultSet.getString("room"),
                    resultSet.getDate("date"),
                    resultSet.getInt("seats")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return reservations;
        
    }
    public static ArrayList<ReservationEntry> getReservationsByFacultyAndDate(String name, Date date)
    {
        connection = DBConnection.getConnection();
        ArrayList<ReservationEntry> reservations = new ArrayList<ReservationEntry>();
        try
        {
            //get all room reservations by faculty and date
            getReservationsByFacultyAndDate = connection.prepareStatement("select * from reservations where faculty = (?) and date = (?)");
            getReservationsByFacultyAndDate.setString(1, name);
            getReservationsByFacultyAndDate.setDate(2, date);
            resultSet = getReservationsByFacultyAndDate.executeQuery();
            while(resultSet.next())
            {
                reservations.add(new ReservationEntry(
                    resultSet.getString("faculty"),
                    resultSet.getString("room"),
                    resultSet.getDate("date"),
                    resultSet.getInt("seats")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return reservations;
        
    }
    public static void deleteReservation(ReservationEntry reservationEntry){
        connection = DBConnection.getConnection();
        try
        {
            deleteReservation = connection.prepareStatement("delete from reservations where faculty = (?) and date = (?) and room = (?)");
            deleteReservation.setString(1, reservationEntry.getFaculty());
            deleteReservation.setDate(2, reservationEntry.getDate());
            deleteReservation.setString(3, reservationEntry.getRoom());
            deleteReservation.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
     public static ArrayList<ReservationEntry> getReservationsByRoom(String room)
    {
        connection = DBConnection.getConnection();
        ArrayList<ReservationEntry> reservations = new ArrayList<ReservationEntry>();
        try
        {
            //get all room reservations made for given room
            getReservationsByRoom = connection.prepareStatement("select * from reservations where room = (?) order by timestamp");
            getReservationsByRoom.setString(1, room);
            resultSet = getReservationsByRoom.executeQuery();
            while(resultSet.next())
            {
                reservations.add(new ReservationEntry(
                    resultSet.getString("faculty"),
                    resultSet.getString("room"),
                    resultSet.getDate("date"),
                    resultSet.getInt("seats")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return reservations;
        
    }
}
