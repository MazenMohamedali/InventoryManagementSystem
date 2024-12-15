/* 
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class garbage {
    public String SHOWSPPLIER() {
        String sqlquery = "SELECT * FROM supplier";

        try (Connection connection = DriverManager.getConnection(connectDB.getDburl());
                PreparedStatement statement = connection.prepareStatement(sqlquery)) {

            ResultSet resultSet = statement.executeQuery();

            StringBuilder res = new StringBuilder();
            boolean found = false;

            while (resultSet.next()) {
                found = true;
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                res.append("ID: ").append(id)
                        .append(", Name: ").append(name)
                        .append(", Email: ").append(email)
                        .append("\n");
            }

            if (found) {
                return res.toString(); // Return the result as a string
            } else {
                return "No suppliers found."; // No rows found
            }

        } catch (SQLException e) {
            return "Error: " + e.getMessage(); // Return error message if there's a SQL exception
        }
    }

    public String SHOWPRODUCTS() {
        String sqlquery = "SELECT * FROM product";

        try (Connection connection = DriverManager.getConnection(connectDB.getDburl());
        
        PreparedStatement statement = connection.prepareStatement(sqlquery)) {

            ResultSet resultSet = statement.executeQuery();

            StringBuilder res = new StringBuilder();
            boolean found = false;
            System.out.println(resultSet.next());
            while (resultSet.next()) {
                found = true;
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price"); // Correct way to get decimal price
                int quantity = resultSet.getInt("quantity"); // You can add more fields as needed
                String category = resultSet.getString("category");
                int sup_id = resultSet.getInt("sup_id"); // Supplier ID if needed

                res.append("ID: ").append(id)
                        .append(", Name: ").append(name)
                        .append(", Price: ").append(price)
                        .append(", Quantity: ").append(quantity)
                        .append(", Category: ").append(category)
                        .append(", Supplier ID: ").append(sup_id)
                        .append("\n");
            }

            if (found) {
                return res.toString(); // Return the result as a string
            } else {
                return "No products found."; // No rows found
            }

        } catch (SQLException e) {
            return "Error: " + e.getMessage(); // Return error message if there's a SQL exception
        }
    }

    public void insertProduct() {
        String sqlquary = "INSERT INTO product (name, price, quantity, category, sup_id, expireDate, ProductionDate, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(connectDB.getDburl());
        PreparedStatement statement = connection.prepareStatement(sqlquary)) {
            statement.setString(1, getName());
            statement.setBigDecimal(2, BigDecimal.valueOf(getPrice()));
            statement.setInt(3, getQuantity());
            statement.setString(4, getCategory()); // Set the category
            statement.setInt(5, getSupplierID()); // Set the supplier ID
            statement.setString(6, getExpirDate()); // Set the expiration date
            statement.setString(7, getProductionDate()); // Set the production date
            statement.setInt(8, getId()); // Set the production date
            
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    public int[] ordersIdForSpacificMonth() {
        String sqlQuary = "SELECT id FROM orders WHERE arrival_date LIKE ?";
        try(Connection conn = DriverManager.getConnection(connectDB.getDburl());
        PreparedStatement pre = conn.prepareStatement(sqlQuary)) {

            String monthFormated = String.format("%02d", month);
            String dateFormated = "%-" + monthFormated + "-" + year;

            pre.setString(1, dateFormated);
            ResultSet executed = pre.executeQuery();

            int counter = 0;
            while (executed.next()) {
                counter++;
            }

            // Rewind the ResultSet for further processing
            executed.beforeFirst();

            int[] orders = new int[counter];
            int i=0;
            while (executed.next()) {
                orders[i++] = executed.getInt("id");
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return new int[0];
        }
    }

    // صدمه
    //SQLite only supports TYPE_FORWARD_ONLY cursors
    public static String[] generlizeSelect(String[] columns, String table, String hasWhere) {
        StringBuilder sqlQuary = new StringBuilder("SELECT ");// = "SELECT id,  FROM orders WHERE arrival_date LIKE ?";
        
        int len = columns.length;
        for(int i=0; i<len; i++) {
            sqlQuary.append(columns[i]);
            if(i != len-1)
                sqlQuary.append(", ");
        }

        sqlQuary.append(" FROM ").append(table);
        if(hasWhere != "") {
            sqlQuary.append(" WHERE ").append(hasWhere);
        }


        String[] results = null;
        int rowCount = 0;


        try(Connection conn = DriverManager.getConnection(connectDB.getDburl());
            // ResultSet.TYPE_SCROLL_INSENSITIVE: used for "resultSet.beforeFirst(); // rewind", 
            //You can scroll (move) both forward and backward in the ResultSet.

            // ResultSet.CONCUR_READ_ONLY: This defines the concurrency mode of the ResultSet
        PreparedStatement preSatement = conn.prepareStatement(sqlQuary.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = preSatement.executeQuery();
            
            
            // count numbers of row to hold in array becuse more efficent use arrayList
            resultSet.last(); // this last row in query
            rowCount = resultSet.getRow(); // count of rows
            resultSet.beforeFirst(); // rewind

            results = new String[rowCount];

            int k=0;
            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                for(int i=0; i<len; i++) {
                    row.append(resultSet.getString(columns[i]));
                    if(i != len-1)
                        row.append(", ");
                }
                results[k++] = row.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

}
*/