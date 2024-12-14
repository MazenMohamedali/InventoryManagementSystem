import java.math.BigDecimal;
import java.sql.*;

public class Admin {

    private static final String DB_URL = "jdbc:sqlite:./databaseIMS.db"; // relative path is better for all of us (look
                                                                         // at Client.DB_URL)

    Supplier sup;
    Product product;
    // Report report;

    // --------------------------------------------->SupplierSection<-------------------------------------------------
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
            System.out.println("Add Supplier EROR 001");
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
            System.out.println("Supplier MaxId EROR 002");
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
            System.out.println("Phone Number EROR 003");
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

    // CHECK IF SUPPLIER ID EXIST ---> DONE
    public static boolean checkSupllierId(int sup_id) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement("SELECT name FROM supplier WHERE id = ?")) {

            stmt.setInt(1, sup_id);

            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // UPDATE SUPPLIER EMAIL
    public static boolean updateSupplierEmail(int id, String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement("UPDATE supplier SET email = ? WHERE id = ?")) {

            stmt.setString(1, email);
            stmt.setInt(2, id);

            // ResultSet rs = stmt.executeQuery();

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    // -------------------------------------------->ProductSection<----------------------------------------------

    // ADD PRODUCT ---> DONE
    public static boolean insertProduct(Product product) {
        String sqlquary = "INSERT INTO product (name, price, quantity, category, sup_id, expireDate, ProductionDate, purchasePrecent) VALUES (? ,?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(connectDB.getDburl());
                PreparedStatement statement = connection.prepareStatement(sqlquary)) {
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, BigDecimal.valueOf(product.getPrice()));
            statement.setInt(3, product.getQuantity());
            statement.setString(4, product.getCategory()); // Set the category
            statement.setInt(8, product.getPurchasePrecent());
            if (checkSupllierId(product.getSupplierID())) {
                statement.setInt(5, product.getSupplierID());
            } else {
                System.out.println("Supplier id not found");
            }
            statement.setString(6, product.getExpirDate()); // Set the expiration date
            statement.setString(7, product.getProductionDate()); // Set the production date

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE PRODUCT ---> DONE
    public static boolean deleteProduct(int prouctId) {

        String sql = "DELETE FROM product WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, prouctId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // UPDATE PRODUCT BY OPTION PRICE || QUANTITY ---> DONE
    public static boolean updateProduct(int id, double x, String option) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement("UPDATE product SET " + option + " = ? WHERE id = ?")) {

            // stmt.setString(1, option);
            stmt.setDouble(1, x);
            stmt.setInt(2, id);

            // ResultSet rs = stmt.executeQuery();

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // public static int getQuantity(int prouctId) {

    // String sql = "SELECT quantity FROM product WHERE id = ?";
    // try (Connection conn = DriverManager.getConnection(DB_URL);
    // PreparedStatement stmt = conn.prepareStatement(sql)) {

    // stmt.setInt(1, prouctId);
    // ResultSet rs = stmt.executeQuery();
    // if (rs.next()) {
    // return rs.getInt("quantity");
    // } else {
    // return -1;
    // }
    // } catch (SQLException e) {
    // System.out.println(e.getMessage());
    // return -1;
    // }
    // }
    public static void main() {
        // Product.showProuct(7001);
        // updateProduct(7001, 70, "quantity");
        // Product.showProuct(7001);
    }
}
