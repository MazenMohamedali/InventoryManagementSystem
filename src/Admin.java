import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {

    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\Msi\\Desktop\\GitHub\\InventoryManagementSystem\\databaseIMS.db";

    Supplier sup;
    // Report report;

    // ADD SUPPLIER ---> DONE
    public static boolean addSupplier(Supplier sup) {// NEED Supplier CLASS

        String sql = "INSERT INTO supplier (id,name,email) VALUES (?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            int sup_id = supplierMaxId() + 1;
            stmt.setInt(1, sup_id);
            stmt.setString(2, sup.getName());
            stmt.setString(3, sup.getEmail());

            PreparedStatement stmt2 = conn
                    .prepareStatement("INSERT INTO phone_numbers (id,phone_number) VALUES (?,?)");

            stmt2.setInt(1, sup_id);
            stmt2.setString(2, sup.getPhone_no());

            return stmt.executeUpdate() > 0 && stmt2.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // GET SUPPLIER LAST ID ---> DONE
    public static int supplierMaxId() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) AS max_id FROM supplier")) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("max_id");
            } else
                return -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    // ADD PHONE NUMBER ---> DONE ------> NO NEED
    public static boolean addPhoneNumber(Connection conn, int id, String phone) {
        try (PreparedStatement pstmt = conn
                .prepareStatement("INSERT INTO phone_numbers (id, phone_number)VALUES (?,?)")) {
            pstmt.setInt(1, id);
            pstmt.setString(2, phone);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (

        SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // DELETE SUPPLIER ---> DONE
    public static boolean deleteSupplier(int supId) {

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM supplier WHERE id = ?")) {

            stmt.setInt(1, supId);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("RAWS AFFECTED: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // ADD PRODUCT
    public void addProduct(Product product) {// NEED Product CLASS

        String sql = "INSERT INTO supplier (name,price,quantity,category,sup_id) VALUES(?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sup.getName());
            stmt.setDouble(2, sup.getPrice());
            stmt.setInt(3, sup.getQuantity());
            stmt.setString(4, prdct.getCategory());
            stmt.setInt(5, prdct.sup.getId());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("RAWS AFFECTED: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // CHECK IF SUPPLIER ID EXIST ---> DONE
    public static boolean checkSupllierId(Connection conn, int sup_id) {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT name FROM supplier WHERE id = ?")) {

            stmt.setInt(1, sup_id);

            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void deleteProduct(int prouctId) {

        String sql = "DELETE FROM product WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, prouctId);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("RAWS AFFECTED: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
