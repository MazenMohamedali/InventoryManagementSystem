import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {
    private static final String DBurl = "jdbc:sqlite:./databaseIMS.db";
    public static String getDburl() {
        return DBurl;
    }
}
