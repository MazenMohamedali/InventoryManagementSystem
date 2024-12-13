import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.Locale;

public class Product {
    // this final becuse max len for string in data base is 50 char
    final private int maxchar = 50;
    private int id;
    private String name;
    private double price;
    private int quantity;
    private int supplierID;
    private String category;
    private String expirDate;
    private String productionDate;

    // VALIDATION METHODS
    private static boolean isValidDate(String date) {
        //            dd-mm-yyyy-isvalid                 --0 for not valid ,, 1 for valid
        int[] arrDate = {0, 0, 0};
        if(date.length()>10)
            return false;

        String[] separteDate = date.trim().split("-");
        if(separteDate.length != 3) 
            return false;

        if(separteDate[0].length() > 2 || separteDate[1].length()>2 ||separteDate[2].length()>4)
            return false;

        arrDate[0] = Integer.parseInt(separteDate[0]);
        arrDate[1] = Integer.parseInt(separteDate[1]);
        arrDate[2] = Integer.parseInt(separteDate[2]);

        if (arrDate[0] < 1 || arrDate[0] > 31 || arrDate[1] < 1 || arrDate[1] > 12)
            return false;

        // Check if the day exists in the given year and month
        YearMonth yearMonth = YearMonth.of(arrDate[2], arrDate[1]);
        int daysInMonth = yearMonth.lengthOfMonth();
        return arrDate[0] <= daysInMonth;
    }


    // CONSTRUCTORS
    Product() {
    }

    Product(int id, String name, double price, int initialQuantity, int supplierID, String category, String exDate, String proDate) {
        setName(name);
        setPrice(price);
        setQuantity(initialQuantity);
        setID(id);
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

    public String getExpirDate() {
        return expirDate;
    }

    public String getProductionDate() {
        return productionDate;
    }


    // SETTER
    public void setCategory(String category) {
        if (category.length() > maxchar) {
            throw new IllegalArgumentException("Name must not exceed 50 characters.");
        }
        this.category = category.toLowerCase(Locale.ROOT);
    }

    public void setName(String name) {
        if (name.length() > maxchar) {
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

    
    // DATE FORMATE dd-mm-yyyy
    public void setExpirDate(String expirDate) {
        if(isValidDate(expirDate))
            this.expirDate = expirDate;
        else {
            throw new IllegalArgumentException("Invalid expiration date format or value: " + expirDate);
        }
    }

    public void setProductionDate(String productionDate) {
        if(isValidDate(productionDate))
            this.productionDate = productionDate;
        else {
            throw new IllegalArgumentException("Invalid expiration date format or value: " + expirDate);
        }
    }

    protected void setID(int id) {
        this.id = id;
    }


    // SEARCH METHODS
    private static String search(String table, String column, String value) {
        String sqlquery = "SELECT * FROM " + table + " WHERE " + column + " = ?";

        try (Connection connection = DriverManager.getConnection(connectDB.getDburl());
                PreparedStatement statement = connection.prepareStatement(sqlquery)) {

            statement.setString(1, value); 
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

    public static String searchProductCategory(String productCategory) {
        return search("product", "category", productCategory);
    }

    public static String searchProductName(String name) {
        return search("product", "name", name);
    }

    public static String searchProductProduction(String productProductionDate) {
        if(isValidDate(productProductionDate))
            return "not found";
        return search("product", "ProductionDate", productProductionDate);
    }

    public static String searchProductExpiration(String productExpirationDate) {
        if(isValidDate(productExpirationDate))
            return "not found";
        return search("product", "expireDate", productExpirationDate);
    }

}
