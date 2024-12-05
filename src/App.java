import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try {
            System.out.println(Admin.supplierMaxId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
