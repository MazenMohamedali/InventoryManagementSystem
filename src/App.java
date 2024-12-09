import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

<<<<<<< HEAD
public class App {
    public static final String DB_URL = "jdbc:sqlite:C:\\Users\\Msi\\Desktop\\GitHub\\InventoryManagementSystem\\databaseIMS.db";

    public static void main(String[] args) throws SQLException {

        Supplier s = new Supplier("sup3", "sup3@email", "2222");
        Connection conn = DriverManager.getConnection(DB_URL);
        try {
            System.out.println(Admin.addPhoneNumber(conn, 3, "2222"));
        } catch (Exception e) {
            e.printStackTrace();
        }
=======
public class App 
{
    public static void main(String[] args) 
    {  
        /* 
        for(int i = 0; i < 100; i++)
        {
            Client c = new Client("x" + i,"x" + i,"x" + i,"x" + i,"x" + i,0);
            Client.addToClientTable(c);
        }
        
        try
        {
            ArrayList <Client> c = new ArrayList<Client>(); 
            c.addAll(Client.getData(10101));
            for(Client cl : c)
            {
                System.out.println(cl.getName() + " " + cl.getBalance());
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        try
        {
            ArrayList <Client> c = new ArrayList<Client>(); 
            c.addAll(Client.getData(10101));
            c.get(0).setName("update");
            Client.updateData(c.get(0), 10101);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        } 
        */
>>>>>>> 7ea449a9f8774ceb694ce96a2ba46843eb5d22ec
    }
}
