/**
 *
 * @author benja
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

public class Faculty
{
    private static Connection connection;
    private static PreparedStatement addFaculty;
    private static PreparedStatement getFacultyList;
    private static ResultSet resultSet;
    
    public static int addFaculty(String name) throws SQLException
    {
        connection = DBConnection.getConnection();
        int status = 0;
        try
        {
            addFaculty = connection.prepareStatement("insert into faculty (name) values (?)");
            addFaculty.setString(1, name);
            status = addFaculty.executeUpdate();
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
    
    public static ArrayList<String> getFacultyList()
    {
        connection = DBConnection.getConnection();
        ArrayList<String> faculty = new ArrayList<String>();
        try
        {
            //bring all faculty names ordered by name
            getFacultyList = connection.prepareStatement("select name from faculty order by name");
            resultSet = getFacultyList.executeQuery();
            
            while(resultSet.next())
            {
                faculty.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return faculty;
        
    }
    
    
}
