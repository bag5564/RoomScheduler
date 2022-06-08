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
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

public class Dates {
    private static Connection connection;
    private static PreparedStatement addDate;
    private static PreparedStatement getAllDates;
    private static ResultSet resultSet;
    
    public static int addDate(Date date) throws SQLException
    {
        connection = DBConnection.getConnection();
        int status = 0;
        try
        {
            addDate = connection.prepareStatement("insert into dates (date) values (?)");
            addDate.setDate(1, date);
            status = addDate.executeUpdate();
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
    
    public static ArrayList<Date> getAllDates()
    {
        connection = DBConnection.getConnection();
        ArrayList<Date> dateList = new ArrayList<Date>();
        try
        {
            //get all dates in ascending order
            getAllDates = connection.prepareStatement("select date from dates order by date");
            resultSet = getAllDates.executeQuery();
            
            while(resultSet.next())
            {
                dateList.add(resultSet.getDate(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return dateList;
        
    }
}
