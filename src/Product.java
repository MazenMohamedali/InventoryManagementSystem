import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
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
    private int purchasePrecent;

    // VALIDATION METHODS

    // this for formate string to effecency in databasw
    // if 1-2-2024 will be 01-02-2024
    private static String makeFormated(String date) {
        StringBuilder res = new StringBuilder();
        String[] parts = date.split("-");
        res.append(parts[0].length() == 1 ? "0" + parts[0] : parts[0]);
        res.append("-");
        res.append(parts[1].length() == 1 ? "0" + parts[1] : parts[1]);
        res.append(parts[2]);
        return res.toString();
    }

    private static boolean isValidDate(String date) {
        // dd-mm-yyyy-isvalid --0 for not valid ,, 1 for valid
        int[] arrDate = { 0, 0, 0 };
        if (date.length() > 10)
            return false;

        String[] separteDate = date.trim().split("-");
        if (separteDate.length != 3)
            return false;

        if (separteDate[0].length() > 2 || separteDate[1].length() > 2 || separteDate[2].length() > 4)
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

    Product(int id, String name, double price, int initialQuantity, int supplierID, String category, String exDate,
            String proDate, int purchasePrecent) {
        setName(name);
        setPrice(price);
        setQuantity(initialQuantity);
        setID(id);
        setSupplierID(supplierID);
        setExpirDate(exDate);
        setProductionDate(proDate);
        setCategory(category);
        setPurchasePrecent(purchasePrecent);

    }

    Product(String name, double price, int initialQuantity, int supplierID, String category, String exDate,
            String proDate, int purchasePrecent) {
        setName(name);
        setPrice(price);
        setQuantity(initialQuantity);
        setSupplierID(supplierID);
        setExpirDate(exDate);
        setProductionDate(proDate);
        setCategory(category);
        setPurchasePrecent(purchasePrecent);
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

    public int getPurchasePrecent() {
        return purchasePrecent;
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
        if (isValidDate(expirDate))
            this.expirDate = makeFormated(expirDate);
        else {
            throw new IllegalArgumentException("Invalid expiration date format or value: " + expirDate);
        }
    }

    public void setProductionDate(String productionDate) {
        if (isValidDate(productionDate))
            this.productionDate = makeFormated(productionDate);
        else {
            throw new IllegalArgumentException("Invalid expiration date format or value: " + expirDate);
        }
    }

    protected void setID(int id) {
        this.id = id;
    }

    public void setPurchasePrecent(int percent) {
        this.purchasePrecent = percent;
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
        if (isValidDate(productProductionDate))
            return "not found";
        return search("product", "ProductionDate", productProductionDate);
    }

    public static String searchProductExpiration(String productExpirationDate) {
        if (isValidDate(productExpirationDate))
            return "not found";
        return search("product", "expireDate", productExpirationDate);
    }

    // SHOW ALL FUNC --> OMAR MOHSEN <--
    static void showAll() {

        try (Connection conn = DriverManager.getConnection(connectDB.getDburl());
<<<<<<< HEAD
                PreparedStatement stmt = conn.prepareStatement("SELECT id,name, expireDate FROM product")) {
=======
                PreparedStatement stmt = conn.prepareStatement("SELECT id,name,sup_id FROM product")) {
>>>>>>> bcaf8adc84598207d248dd1068df672317e5b164
            ResultSet rs = stmt.executeQuery();

            System.out.printf("%-10s %-20s %-10s \n", "ID", "Name", "Supplier_ID");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int supplier_id = rs.getInt("sup_id");
                System.out.printf("%-10d %-20s %-10d \n", id, name, supplier_id);
>>>>>>> bcaf8adc84598207d248dd1068df672317e5b164
            }
        } catch (SQLException e) {
            System.out.println("SOMTHING WENT WRONG" + e.getMessage());
        }
    }

    public static void printLine() {
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------------------");
    }

    public static void showAllforClient() {
        try (Connection conn = DriverManager.getConnection(connectDB.getDburl());
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT name, price, quantity, productionDate, expireDate FROM product")) {
            ResultSet rs = stmt.executeQuery();

            printLine();
            System.out.println("Name\t\t|\tPrice\t\t|\tQuantity\t|\tProductionDate\t\t|\tExpireDate\t|");
            printLine();
            while (rs.next()) {
                String name = rs.getString(1);
                double price = rs.getDouble(2);
                int quantity = rs.getInt(3);
                String productionDate = rs.getString(4);
                String expireDate = rs.getString(5);
                System.out.println(name + "\t\t|\t" + price + "\t\t|\t" + quantity + "\t\t|\t" + productionDate
                        + "\t\t|\t" + expireDate + "\t|");
                printLine();
            }

        } catch (SQLException e) {
            System.out.println("SOMTHING WENT WRONG" + e.getMessage());
        }
    }

    /*
     * THIS WORKS BUT WITHOUT VERTICAL SEPARATORS
     * 
     * System.out.println("Name\t\tPrice\t\tQuantity\tProductionDate\t\tExpireDate")
     * ;
     * printLine();
     * while (rs.next())
     * {
     * String name = rs.getString(1);
     * double price = rs.getDouble(2);
     * int quantity = rs.getInt(3);
     * String productionDate = rs.getString(4);
     * String expireDate = rs.getString(5);
     * System.out.println(name + "\t\t" + price + "\t\t" + quantity + "\t\t" +
     * productionDate + "\t\t" + expireDate);
     * printLine();
     * }
     */

    static void showProuct(int id) {

        try (Connection conn = DriverManager.getConnection(connectDB.getDburl());
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM product WHERE id = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            System.out.println(
                    "--------------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-20s %-20s %-10s %-10s %-10s%n",
                    "ID", "Name", "Category", "Price", "Quantity", "Supplier_ID");

            String name = rs.getString("name");
            BigDecimal price = rs.getBigDecimal("price");
            int quantity = rs.getInt("quantity");
            String category = rs.getString("category");
            int sup_id = rs.getInt("sup_id");

            System.out.printf("%-10d %-20s %-20s %-10.2f %-10d %-10d%n",
                    id, name, category, price, quantity, sup_id);
            System.out.println(
                    "--------------------------------------------------------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("SOMTHING WENT WRONG" + e.getMessage());
        }
    }

    // public static void main(String[] args) {
    // showAll();
    // // searchProductName("milk");

    // try (Connection conn = DriverManager.getConnection(connectDB.getDburl());
    // Statement stm = conn.createStatement()) {

    // // Add the new column with a default value of 0
    // String alterTableSQL = "ALTER TABLE product ADD COLUMN purchasePrecent int
    // NOT NULL DEFAULT 0";

    // // Execute the ALTER TABLE statement
    // stm.executeUpdate(alterTableSQL);
    // System.out.println("Column added successfully.");

    // } catch (SQLException e) {
    // System.out.println("SQL Error: " + e.getMessage());
    // }
    // }

    // Dummy method for showing all
}
