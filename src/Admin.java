import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Supplier {
}

class Product {
}

public class Admin {

    private static final String DB_URL = "jdbc:sqlite:src/database.db";

    Supplier sup;
    Product product;
    // Report report;

    public void addSupplier(Supplier sup) {

        String sql = "INSERT INTO supplier (name,email) VALUES (?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sup.getName());
            stmt.setString(2, sup.getEmail());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("RAWS AFFECTED: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int supplierMaxId() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) AS max_id FROM supplier")) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("max_id");
            } else
                return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public static void addPhoneNumber(Connection conn, int id, String phone) {
        try (PreparedStatement pstmt = conn
                .prepareStatement("INSERT INTO supplier_phone_numbers (id, phone_number) VALUES (?,?)")) {
            pstmt.setInt(1, id);
            pstmt.setString(2, phone);

            pstmt.executeUpdate();
            System.out.println("number added");

            // log the operation
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // log the error
        }
    }

    // UNDER BUILD
    public void addProduct(Product product) {

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

    public void deleteSupplier(int supId) {

        String sql = "DELETE FROM supplier WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, supId);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("RAWS AFFECTED: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
