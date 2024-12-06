import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static final String DB_URL = "jdbc:sqlite:src/database.db";

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        try {
            System.out.println(Admin.addSupplier("sup5", "sup5@email", "1111"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
