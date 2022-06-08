import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

/**
 *
 * @author benja
 */
public class RoomQueries {
    private static Connection connection;
    private static PreparedStatement getAllPossibleRooms;
    private static PreparedStatement addRoom;
    private static PreparedStatement deleteRoom;
    private static PreparedStatement getAllRooms;
    private static ResultSet resultSet;
    
    public static ArrayList<String> getAllPossibleRooms(int seats)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> rooms = new ArrayList<String>();
        try
        {
            //select all rooms with enough capacity ordered by # of seats 
            getAllPossibleRooms = connection.prepareStatement("select name from rooms where seats >= (?) order by seats");
            getAllPossibleRooms.setInt(1, seats);
            resultSet = getAllPossibleRooms.executeQuery();
            
            while(resultSet.next())
            {
                rooms.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return rooms;
        
    }
    public static int addRoom(RoomEntry roomEntry) throws SQLException
    {
        connection = DBConnection.getConnection();
        int status = 0;
        try
        {
            addRoom = connection.prepareStatement("insert into rooms (name, seats) values (?,?)");
            addRoom.setString(1, roomEntry.getName());
            addRoom.setInt(2, roomEntry.getSeats());
            status = addRoom.executeUpdate();
        }
        catch(DerbySQLIntegrityConstraintViolationException sqlException)
        {
          //do nothing so MainFrame handles message for dup key  
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            throw sqlException;
        }
        return status;
        
    }
    public static void deleteRoom(String room){
        connection = DBConnection.getConnection();
        try
        {
            deleteRoom = connection.prepareStatement("delete from rooms where name = (?)");
            deleteRoom.setString(1, room);
            deleteRoom.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    public static ArrayList<String> getAllRooms()
    {
        connection = DBConnection.getConnection();
        ArrayList<String> rooms = new ArrayList<String>();
        try
        {
            //select all rooms 
            getAllRooms = connection.prepareStatement("select name from rooms order by name");
            resultSet = getAllRooms.executeQuery();
            
            while(resultSet.next())
            {
                rooms.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return rooms;
        
    }
}
