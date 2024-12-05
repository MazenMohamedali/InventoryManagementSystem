
/*

/**
 *
 * @author yousf
 */
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//comment Order at actual implementation
class Order
{
      int amount;
      int[] prodID;
      int[] pricePerID;
      double[] totalPrice;
      Date orderDate;
      Date arrivalDate;
      public Order(int[] id)
      {
      }
}


public class Client extends Person 
{
      private static final String DB_URL = "jdbc:sqlite:src/database.db";
      private static int startID = 10000;
      private static int orderCount;
      public double balance = 0;
      public String phoneNumber = "";
      public int id;
      
      
      
      public Client(String name, int id, String email, String password, String address, String phone_no, double balance)
      {
            super(name, email, phone_no, password, address);
            this.balance = balance;
            this.id = id;
      }
      
      
      
      public static void addToClientTable(Client c) 
      {
            String sql = "INSERT INTO client (id, name, email, address, password, balance) VALUES (?, ?, ?, ?, ?, ?)";
            String maxIDQuery = "SELECT MAX(id) AS max_id FROM client";
            try (Connection conn = DriverManager.getConnection(DB_URL)) 
            {
                  conn.setAutoCommit(false); // Enable transaction handling
                  
                  // Retrieve the current max ID
                  try (Statement maxIDStatement = conn.createStatement();
                  ResultSet rs = maxIDStatement.executeQuery(maxIDQuery)) 
                  {
                        int max_ID = startID;
                        if (rs.next()) 
                        {
                              max_ID = rs.getInt("max_id");
                        }
                        
                        // Insert into the client table
                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) 
                        {
                              pstmt.setInt(1, max_ID + 1);
                              pstmt.setString(2, c.name);
                              pstmt.setString(3, c.email);
                              pstmt.setString(4, c.address);
                              pstmt.setString(5, c.password);
                              pstmt.setBigDecimal(6, BigDecimal.valueOf(c.balance));
                              pstmt.executeUpdate();
                        }
                        
                        // Add phone number
                        addPhoneNumber(conn, max_ID + 1, c.phone_no);
                        
                        // Commit the transaction
                        conn.commit();
                        System.out.println("Client and phone number added successfully.");
                  }
            } 
            catch (SQLException e) 
            {
                  System.out.println("Error: " + e.getMessage());
                  e.printStackTrace();
            }
      }
      
      
      
      
      
      
      public static void addPhoneNumber(Connection conn ,int id, String phone)
      {
            try(PreparedStatement pstmt = conn.prepareStatement("INSERT INTO client_phone_numbers (id, phone_number) VALUES (?,?)"))
            {
                  pstmt.setInt(1, id);
                  pstmt.setString(2, phone);
                  
                  pstmt.executeUpdate();
                  System.out.println("number added");
                  
                  //log the operation
            }
            catch(SQLException e)
            {
                  System.out.println(e.getMessage());
                  //log the error
            }
      }
      
      
      
      public double getBalance()
      {
            return balance;
      }
      
      
      
      public int getstartID()
      {
            return startID;
      }
      
      
      
      public static int getOrderCount()
      {
            return orderCount;
      }
      
      
      
      
      public static ArrayList<Client> getData(int id) throws SQLException 
      {
            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "SELECT client.name, client.id, client_phone_numbers.phone_number, client.email, client.address, client.balance, password " +
            "FROM client " + 
            "JOIN client_phone_numbers " + 
            "ON client.id = client_phone_numbers.id " + 
            "WHERE client.id = ?";
            
            PreparedStatement p = conn.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            
            
            // Initialize arraylist with `Client` objects
            var data = new ArrayList<Client>();
            while (rs.next()) 
            {
                  Client client = new Client("", 0,"", "", "", "", 0);
                  client.name = rs.getString(1);
                  client.id = rs.getInt(2);
                  client.phoneNumber = rs.getString(3);
                  client.email = rs.getString(4);
                  client.address = rs.getString(5);
                  client.balance = rs.getDouble(6);
                  client.password = rs.getString(7);
                  
                  data.add(client); // Assign the object to the array
            }
            
            rs.close();
            p.close();
            conn.close();
            
            return data;
      }
      


      public void placeOrder(int[] orderIDs)
      {
            orderCount++;
            Order order = new Order(orderIDs);
            //the products ordered get reduced
      }
}

