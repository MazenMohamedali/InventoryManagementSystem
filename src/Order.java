import java.time.LocalDate;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Order {
    private static final String DB_URL = "jdbc:sqlite:./databaseIMS.db";
    private static int idCounter = 1; 
    private final int orderID;       
    private int[] prodID;            
    private int[] amount;            
    private double[] pricePerID;       
    private double totalPrice;       
    private String clientAddress;    
    private LocalDate orderDate;      
    private LocalDate arrivalDate;    



    public Order(int[] prodID, int[] amount, double[] pricePerID, String clientAddress, LocalDate orderDate, LocalDate arrivalDate) {
        this.orderID = idCounter++;  
        this.prodID = prodID;
        this.amount = amount;
        this.pricePerID = pricePerID;
        this.clientAddress = clientAddress;
        this.orderDate = orderDate;
        this.arrivalDate = arrivalDate;
        calculateTotalPrice();
    }

    
    public int getOrderID() {
        return orderID;
    }

    
    public int[] getProdID() {
        return prodID;
    }

    public void setProdID(int[] prodID) {
        this.prodID = prodID;
        calculateTotalPrice();
    }

    
    public int[] getAmount() {
        return amount;
    }

    public void setAmount(int[] amount) {
        this.amount = amount;
        calculateTotalPrice();
    }

    
    public double[] getPricePerID() {
        return pricePerID;
    }

    public void setPricePerID(double[] pricePerID) {
        this.pricePerID = pricePerID;
        calculateTotalPrice();
    }

    
    public double getTotalPrice() {
        return totalPrice;
    }

    
    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    
    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    
    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

   
    private void calculateTotalPrice() {
        totalPrice = 0;
        if (prodID != null && amount != null && pricePerID != null) {
            for (int i = 0; i < prodID.length; i++) {
                totalPrice += amount[i] * pricePerID[i];
            }
        }
    }
    public static void processOrder(String[] productNames, int[] amounts, String clientAddress) {
        LocalDate orderDate = LocalDate.now();
        LocalDate arrivalDate = LocalDate.now().plusDays(6);
        if (productNames.length != amounts.length) {
            throw new IllegalArgumentException("Product names and amounts arrays must have the same length.");
        }

        String insertOrderSQL = "INSERT INTO orders (id,client_address, arrival_Date, price,client_id,order_datetime) VALUES (?, ?, ?, ?)";
        String insertOrderProdSQL = "INSERT INTO order_prod (prod_id, order_id, quantity) VALUES (?, ?, ?)";
        String updateProductSQL = "UPDATE product SET Quantity = Quantity - ? WHERE id,name=id,name ?";
        String selectProductSQL = "SELECT id, Quantity FROM product WHERE name=name ?";

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false); // Start transaction

            try (
                PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL, PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement orderProdStmt = conn.prepareStatement(insertOrderProdSQL);
                PreparedStatement updateProductStmt = conn.prepareStatement(updateProductSQL);
                PreparedStatement selectProductStmt = conn.prepareStatement(selectProductSQL)
            ) {
                double totalPrice = 0.0;

                // 1. Calculate total price and validate product availability
                int[] productIDs = new int[productNames.length];
                for (int i = 0; i < productNames.length; i++) {
                    selectProductStmt.setString(1, productNames[i]);
                    try (ResultSet rs = selectProductStmt.executeQuery()) {
                        if (!rs.next()) {
                            throw new IllegalArgumentException("Product not found: " + productNames[i]);
                        }
                        int productID = rs.getInt("id");
                        int availableAmount = rs.getInt("Quantity");
                        if (amounts[i] > availableAmount) {
                            throw new IllegalArgumentException("Insufficient stock for product: " + productNames[i]);
                        }
                        productIDs[i] = productID;
                        totalPrice += amounts[i] * getProductPrice(productID, conn); // Assume a method for product price
                    }
                }

                // 2. Log the order in the orders table
                orderStmt.setString(1, clientAddress);
                orderStmt.setDate(2, java.sql.Date.valueOf(orderDate));
                orderStmt.setDate(3, java.sql.Date.valueOf(arrivalDate));
                orderStmt.setDouble(4, totalPrice);
                orderStmt.executeUpdate();

                // Retrieve the generated order ID
                int orderID;
                try (ResultSet rs = orderStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderID = rs.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve generated order ID.");
                    }
                }

                // 3. Log order products and update product stock
                for (int i = 0; i < productNames.length; i++) {
                    // Log in order_prod table
                    orderProdStmt.setInt(1, orderID);
                    orderProdStmt.setInt(2, productIDs[i]);
                    orderProdStmt.setInt(3, amounts[i]);
                    orderProdStmt.addBatch();

                    // Update product stock
                    updateProductStmt.setInt(1, amounts[i]);
                    updateProductStmt.setString(2, productNames[i]);
                    updateProductStmt.addBatch();
                }
                orderProdStmt.executeBatch();
                updateProductStmt.executeBatch();

                conn.commit(); // Commit transaction
                System.out.println("Order processed successfully!");
            } catch (Exception e) {
                conn.rollback(); // Rollback on error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static double getProductPrice(int productID, Connection conn) throws SQLException {
        String selectPriceSQL = "SELECT price FROM product WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(selectPriceSQL)) {
            stmt.setInt(1, productID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("price");
                } else {
                    throw new IllegalArgumentException("Price not found for product ID: " + productID);
                }
            }
        }
    }


    //Display order details
    public void displayOrder() {
        System.out.println("Order Details:");
        System.out.println("Order ID: " + orderID);
        System.out.println("Client Address: " + clientAddress);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Expected Arrival Date: " + arrivalDate);
        System.out.println("Products:");
        for (int i = 0; i < prodID.length; i++) {
            System.out.println("Product ID: " + prodID[i] + ", Amount: " + amount[i] + ", Price per ID: " + pricePerID[i]);
        }
        System.out.println("Total Price: " + totalPrice);
    }
    public void saveToDatabase() {
            String insertOrderSQL = "INSERT INTO orders (orderID, clientAddress, orderDate, arrivalDate, totalPrice) VALUES (?, ?, ?, ?, ?)";
            String insertOrderDetailsSQL = "INSERT INTO order_details (orderID, prodID, amount, pricePerID) VALUES (?, ?, ?, ?)";
    
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                conn.setAutoCommit(false); // Enable transaction
    
                try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL);
                     PreparedStatement detailsStmt = conn.prepareStatement(insertOrderDetailsSQL)) {
    
                    // Insert order data
                    orderStmt.setInt(1, orderID);
                    orderStmt.setString(2, clientAddress);
                    orderStmt.setDate(3, java.sql.Date.valueOf(orderDate));
                    orderStmt.setDate(4, java.sql.Date.valueOf(arrivalDate));
                    orderStmt.setDouble(5, totalPrice);
                    orderStmt.executeUpdate();
    
                    // Insert product details
                    for (int i = 0; i < prodID.length; i++) {
                        detailsStmt.setInt(1, orderID);
                        detailsStmt.setInt(2, prodID[i]);
                        detailsStmt.setInt(3, amount[i]);
                        detailsStmt.setDouble(4, pricePerID[i]);
                        detailsStmt.addBatch(); // Batch processing for efficiency
                    }
                    detailsStmt.executeBatch();
    
                    conn.commit(); // Commit transaction
                } catch (SQLException e) {
                    conn.rollback(); // Rollback if any issue occurs
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}

