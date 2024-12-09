
// import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import java.util.Date;


//           ________________           still underbuilt         ______________
public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private int supplierID;
    private Date expirDate;
    private Date productionDate;

    Product() {
    }

    Product(int id, String name, double price, int initialQuantity, int supplierID, String exDate, String proDate) {
        setName(name);
        setPrice(price);
        setQuantity(initialQuantity);
        setID(id);
        setSupplierID(supplierID);
        setExpirDate(exDate);
        setProductionDate(proDate);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public String getExpirDate() {
        return dateFormat.format(expirDate);
    }

    public String getProductionDate() {
        return dateFormat.format(productionDate);
    }

    public void setName(String name) {
        if (name.length() > 50) {
            throw new IllegalArgumentException("Name must not exceed 50 characters.");
        }
        this.name = name.toLowerCase(Locale.ROOT);
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public void setSupplierID(int supplierID) {

        this.supplierID = supplierID;
    }

    // DATE FORMATE YYYY-MM-DD
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void setExpirDate(String expirDate) {
        try {
            this.expirDate = dateFormat.parse(expirDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.", e);
        }
    }

    public void setProductionDate(String productionDate) {
        try {
            this.productionDate = dateFormat.parse(productionDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.", e);
        }
    }

    protected void setID(int id) {
        this.id = id;
    }

    public String search(String table, String column, String value) {
        String sqlquery = "SELECT * FROM " + table + " WHERE " + column + " = ?";
        StringBuilder res = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(connectDB.getDburl());
                PreparedStatement statement = connection.prepareStatement(sqlquery)) {
            statement.setString(1, table); // Set the productName parameter
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return "Not found";
            } else {
                // String name = resultSet.getString("name");
                // System.out.println("Found product: " + name);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    res.append(columnName).append(": ").append(columnValue).append(", ");
                }
                return res.toString();
            }
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String searchProductCategory(String productCategory) {
        return search("product", "category", productCategory);
    }

    public String searchProductproduction(String productProduction) {
        return search("product", "ProductionDate", productProduction);
    }

    public String searchProductexpiration(String productExpiration) {
        return search("product", "expireDate", productExpiration);
    }

}
