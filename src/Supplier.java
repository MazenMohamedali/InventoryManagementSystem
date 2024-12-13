import java.sql.*;

public class Supplier extends person {
    Supplier() {
    }

    Supplier(String name, String email, String phone) {
        super(name, email, phone);
    }

    static void showAll() {

        try (Connection conn = DriverManager.getConnection(connectDB.getDburl());
                PreparedStatement stmt = conn.prepareStatement("SELECT id,name FROM supplier")) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("ID\t\tName");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println(id + "\t\t" + name);
            }

        } catch (SQLException e) {
            System.out.println("SOMTHING WENT WRONG");
        }
    }

    @Override
    public String toString() {
        return this.getId() + this.getName();
    }

}
