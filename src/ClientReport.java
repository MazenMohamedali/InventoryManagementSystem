import java.sql.SQLException;
import java.util.ArrayList;

public class ClientReport 
{
      public static void genReport(int id)
      {
            try
            {
                  ArrayList<Client> client = Client.getData(id);
            }
            catch(SQLException e)
            {
                  System.out.println(e.getMessage());
            }
      }
}
