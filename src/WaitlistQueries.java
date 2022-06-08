/**
 *
 * @author benja
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

public class WaitlistQueries {
    private static Connection connection;
    private static PreparedStatement addWaitlistEntry;
    private static PreparedStatement getFullWaitlist;
    private static PreparedStatement getWaitlistByFaculty;
    private static PreparedStatement getWaitlistByTimestamp;
    private static PreparedStatement deleteWaitlistEntry;
    private static PreparedStatement getWaitlistByFacultyAndDate;
    private static ResultSet resultSet;
    
    public static int addWaitlistEntry(WaitlistEntry waitlistEntry) throws SQLException
    {
        connection = DBConnection.getConnection();
        int status = 0;
        try
        {
            if(waitlistEntry.getTimestamp() == null){
                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                waitlistEntry.setTimestamp(currentTimestamp);
            }
            addWaitlistEntry = connection.prepareStatement("insert into waitlist (faculty, date, seats, timestamp) values (?,?,?,?)");
            addWaitlistEntry.setString(1, waitlistEntry.getFaculty());
            addWaitlistEntry.setDate(2, waitlistEntry.getDate());
            addWaitlistEntry.setInt(3, waitlistEntry.getSeats());
            addWaitlistEntry.setTimestamp(4, waitlistEntry.getTimestamp());
            status = addWaitlistEntry.executeUpdate();
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
    public static ArrayList<WaitlistEntry> getFullWaitlist()
    {
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> entries = new ArrayList<WaitlistEntry>();
        try
        {
            //get all waitlist entries ordered by date and timestamp
            getFullWaitlist = connection.prepareStatement("select * from waitlist order by date, timestamp");
            resultSet = getFullWaitlist.executeQuery();
            while(resultSet.next())
            {
                entries.add(new WaitlistEntry(
                    resultSet.getString("faculty"),
                    resultSet.getDate("date"),
                    resultSet.getInt("seats")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entries;
        
    }
    public static ArrayList<WaitlistEntry> getWaitlistByTimestamp()
    {
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> entries = new ArrayList<WaitlistEntry>();
        try
        {
            //get all waitlist entries ordered by date and timestamp
            getWaitlistByTimestamp = connection.prepareStatement("select * from waitlist order by timestamp");
            resultSet = getWaitlistByTimestamp.executeQuery();
            while(resultSet.next())
            {
                entries.add(new WaitlistEntry(
                    resultSet.getString("faculty"),
                    resultSet.getDate("date"),
                    resultSet.getInt("seats")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entries;
        
    }
    public static ArrayList<WaitlistEntry> getWaitlistByFaculty(String name)
    {
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> entries = new ArrayList<WaitlistEntry>();
        try
        {
            //get all waitlist entries for given faculty ordered by date
            getWaitlistByFaculty = connection.prepareStatement("select * from waitlist where faculty = (?) order by date");
            getWaitlistByFaculty.setString(1, name);
            resultSet = getWaitlistByFaculty.executeQuery();
            while(resultSet.next())
            {
                entries.add(new WaitlistEntry(
                    resultSet.getString("faculty"),
                    resultSet.getDate("date"),
                    resultSet.getInt("seats")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entries;
        
    }
    public static void deleteWaitlistEntry(WaitlistEntry waitlistEntry)
    {
        connection = DBConnection.getConnection();
        try
        {
            deleteWaitlistEntry = connection.prepareStatement("delete from waitlist where faculty = (?) and date = (?)");
            deleteWaitlistEntry.setString(1, waitlistEntry.getFaculty());
            deleteWaitlistEntry.setDate(2, waitlistEntry.getDate());
            deleteWaitlistEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    public static ArrayList<WaitlistEntry> getWaitlistByFacultyAndDate(String name, Date date)
    {
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> entries = new ArrayList<WaitlistEntry>();
        try
        {
            //get all waitlist entries for given faculty ordered by date
            getWaitlistByFacultyAndDate = connection.prepareStatement("select * from waitlist where faculty = (?) and date = (?)");
            getWaitlistByFacultyAndDate.setString(1, name);
            getWaitlistByFacultyAndDate.setDate(2, date);
            resultSet = getWaitlistByFacultyAndDate.executeQuery();
            while(resultSet.next())
            {
                entries.add(new WaitlistEntry(
                    resultSet.getString("faculty"),
                    resultSet.getDate("date"),
                    resultSet.getInt("seats")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entries;
        
    }
}
