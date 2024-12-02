
/*

/**
 *
 * @author yousf
 */
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
      private int startID = 10000;
      private static int orderCount;
      private double balance = 0;



      public Client(String name, String email, String password, String address, String phone_no, double balance)
      {
            super(name, email, phone_no,password, address);
            this.balance = balance;
            addToClientTable();
      }



      private void addToClientTable() 
      {
            String sql = "INSERT INTO client (id, name, email, address, password, balance) VALUES (?, ?, ?, ?, ?, ?)";
            String maxIDQuery = "SELECT MAX(id) AS max_id FROM client";

            try
            (
                  Connection conn = DriverManager.getConnection(DB_URL);
                  PreparedStatement pstmt = conn.prepareStatement(sql);
                  PreparedStatement pstmt2 = conn.prepareStatement(maxIDQuery);
                  ResultSet rs = pstmt2.executeQuery();
            ) 
            {
                  int max_ID = startID;
                  if(rs.next())
                  {
                        max_ID = rs.getInt("max_id");
                        pstmt.setInt(1, max_ID + 1); 
                        pstmt.setString(2, this.name);
                        pstmt.setString(3, this.email);
                        pstmt.setString(4, this.address);
                        pstmt.setString(5, this.password);
                        pstmt.setBigDecimal(6, BigDecimal.valueOf(this.balance));
                        addPhoneNumber(max_ID, phone_no);
                  }
                  else
                  {

                        pstmt.setInt(1, startID+1);
                        pstmt.setString(2, this.name);
                        pstmt.setString(3, this.email);
                        pstmt.setString(4, this.address);
                        pstmt.setString(5, this.password);
                        pstmt.setBigDecimal(6, BigDecimal.valueOf(this.balance));
                        addPhoneNumber(startID, phone_no);
                  }
                  
                  // Execute the query
                  pstmt.executeUpdate();
                  
                  // Log success
                  System.out.println("Client added successfully.");
                  conn.close();
            } 
            catch (SQLException e) 
            {
                  System.err.println("Error: " + e.getMessage());
                  // Log the error properly
            }
      }




      public static void addPhoneNumber(int id, String phone)
      {
            try(Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO client_phone_numbers (id, phone_number) VALUES (?,?)"))
            {
                  pstmt.setInt(1, id);
                  pstmt.setString(2, phone);

                  pstmt.executeUpdate();
                  System.out.println("number added");

                  conn.close();
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
      


      public static void getData(int id)
      {
            //connect to the database, and get the client's complete data
            try
            {
                  Connection conn = DriverManager.getConnection(DB_URL);
                  conn.setAutoCommit(true);
                  Statement stmt = conn.createStatement();
                  stmt.executeQuery("SELECT * FROM client, client_phone_numbers WHERE client.id = " + id + " AND client_phone_numbers.client_id = " + id);
                  
                  //log the operation
            }
            catch(SQLException e)
            {
                  e.getMessage();
                  //log the error
            }
      }


      public void placeOrder(int[] orderIDs)
      {
            orderCount++;
            Order order = new Order(orderIDs);
            //the products ordered get reduced
      }
}