import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

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
        
    }
}
