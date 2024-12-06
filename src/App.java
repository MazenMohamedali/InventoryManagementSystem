import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
<<<<<<< HEAD
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
    public static final String DB_URL = "jdbc:sqlite:./databaseIMS.db";

    public static void main(String[] args) throws SQLException 
    {
        
>>>>>>> 647abb0062052adaf3fcd56cc56beb82b959fa07
    }
}
