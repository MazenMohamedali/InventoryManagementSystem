import java.sql.SQLException;

public class App 
{
    public static void main(String[] args)
    {
        try 
        {
            System.out.println(Client.deletePhoneNumber(10002, "null"));
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}
