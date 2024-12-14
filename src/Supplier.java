import java.sql.*;

public class Supplier extends Person {
    Supplier() {
    }

    Supplier(String name, String email, String phone) {
        super(name, email, phone);
    }

    static void showAll() {

        try (Connection conn = DriverManager.getConnection(connectDB.getDburl());
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT s.id,s.name,s.email,p.phone_number FROM supplier s JOIN phone_numbers p ON s.id = p.id ORDER BY s.id")) {
            ResultSet rs = stmt.executeQuery();
            System.out.println("-----------------------------------------------------------");

            System.out.printf("%-10s %-20s %-20s %-20s \n", "ID", "Name", "Email", "Phone");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone_number");
                String email = rs.getString("email");
                System.out.printf("%-10d %-20s %-20s %-20s \n", id, name, email, phone);
            }
            System.out.println("-----------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("SOMTHING WENT WRONG");
        }
    }

    static void show(int id) {

        try (Connection conn = DriverManager.getConnection(connectDB.getDburl());
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT s.name,s.email,p.phone_number FROM supplier s JOIN phone_numbers p ON s.id = p.id WHERE s.id ="
                                + id)) {
            ResultSet rs = stmt.executeQuery();
            System.out.println("--------------------------------------------------------------------");
            System.out.printf("%-10s %-20s %-30s %-20s \n", "ID", "Name", "Email", "Phone");
            String name = rs.getString("name");
            String phone = rs.getString("phone_number");
            String email = rs.getString("email");
            System.out.printf("%-10d %-20s %-30s %-20s \n", id, name, email, phone);

            System.out.println("--------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("SOMTHING WENT WRONG" + e.getMessage());
        }
    }

    public static boolean checkSupllierId(int sup_id) {
        try (Connection conn = DriverManager.getConnection(connectDB.getDburl());
                PreparedStatement stmt = conn.prepareStatement("SELECT name FROM supplier WHERE id = ?")) {

            stmt.setInt(1, sup_id);

            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public String toString() {
        return this.getId() + this.getName();
    }

    public static void main() {
        showAll();
        show(1);
        Admin.updateSupplierEmail(1, "sup1@email.com");
        show(1);
    }

}
