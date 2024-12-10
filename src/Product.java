
// import java.sql.Date;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
    private String category;
    private Date expirDate;
    private Date productionDate;

    // FOR TEST
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

        try (Connection connection = DriverManager.getConnection(connectDB.getDburl());
                PreparedStatement statement = connection.prepareStatement(sqlquary)) {
            statement.setString(1, getName());
            statement.setBigDecimal(2, BigDecimal.valueOf(getPrice()));
            statement.setInt(3, getQuantity());
            statement.setString(4, getCategory()); // Set the category
            statement.setInt(5, getSupplierID()); // Set the supplier ID
            statement.setDate(6, new java.sql.Date(getExpirDate().getTime())); // Set the expiration date
            statement.setDate(7, new java.sql.Date(getProductionDate().getTime())); // Set the production date
            statement.setInt(8, getId()); // Set the production date

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // for alter becuse i alrdy alterd from data base but not effectd in java
    public void addColumnsToProduct() {
        String alterExpireDate = "ALTER TABLE product ADD COLUMN expireDate TEXT NOT NULL;";
        String alterProductionDate = "ALTER TABLE product ADD COLUMN productionDate TEXT NOT NULL;";

        try (Connection connection = DriverManager.getConnection(connectDB.getDburl());
                Statement statement = connection.createStatement()) {

            // Execute the first ALTER TABLE statement
            statement.executeUpdate(alterExpireDate);
            System.out.println("expireDate column added successfully.");

            // Execute the second ALTER TABLE statement
            statement.executeUpdate(alterProductionDate);
            System.out.println("productionDate column added successfully.");

        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        }
    }

    Product() {
    }

    Product(int id, String name, double price, int initialQuantity, int supplierID, String category, String exDate,
            String proDate) {
        setName(name);
        setPrice(price);
        setQuantity(initialQuantity);
        setID(id);
        setSupplierID(supplierID);
        setExpirDate(exDate);
        setProductionDate(proDate);
        setCategory(category);
    }

    Product(String name, double price, int initialQuantity, int supplierID, String category, String exDate,
            String proDate) {
        setName(name);
        setPrice(price);
        setQuantity(initialQuantity);
        setSupplierID(supplierID);
        setExpirDate(exDate);
        setProductionDate(proDate);
        setCategory(category);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
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

    public Date getExpirDate() {
        return expirDate;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setCategory(String category) {
        if (category.length() > 50) {
            throw new IllegalArgumentException("Name must not exceed 50 characters.");
        }
        this.category = category.toLowerCase(Locale.ROOT);
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
        // String sqlquery = "SELECT * FROM product WHERE category = ? ";
        String sqlquery = "SELECT * FROM " + table + " WHERE " + column + " = ?";
        // SELECT category FROM product WHERE category = 'Dairy'

        try (Connection connection = DriverManager.getConnection(connectDB.getDburl());
                PreparedStatement statement = connection.prepareStatement(sqlquery)) {

            statement.setString(1, value); // Set the productName parameter
            ResultSet resultSet = statement.executeQuery();

            StringBuilder res = new StringBuilder();
            if (!resultSet.next()) {
                return "Not found";
            } else {
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

    public String searchProductName(String name) {
        return search("product", "name", name);
    }

    public String searchProductproduction(String productProduction) {
        return search("product", "ProductionDate", productProduction);
    }

    public String searchProductexpiration(String productExpiration) {
        return search("product", "expireDate", productExpiration);
    }

    public void main(String[] args) {
        // addColumnsToProduct();
        // Product test = new Product(7002, "Bread", 0.99, 100, 11, "Bakery",
        // "2024-12-31", "2024-12-01");
        // Product test2 = new Product(7003, "Eggs", 3.49, 30, 12, "Dairy",
        // "2025-01-05", "2024-11-20");
        // Product test = new Product(7004, "Bread", 0.99, 100, 11, "Bakery",
        // "2024-12-31", "2024-12-01");
        // test.insertProduct();
        // test2.insertProduct();
        // System.out.println(SHOWSPPLIER());
        // System.out.println(SHOWPRODUCTS());
        System.out.println(searchProductCategory("dairy"));
        System.out.println(searchProductName("bread"));
        System.out.println(searchProductproduction("2024-12-31"));

    }
}
