import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*TODO
 * get names
 * get amounts
 * UPDATE product SET col_amount = amounts[0,1,2..,i] WHERE product_id, col_name = name, id
 * SELECT id from product WHERE name = name
 * col_amounts -= amounts[0,1,2..,i] (decrease amounts of specified products)
 * log order on orders table
 * log order on order_prod
 * save to database
 */


public class Client extends Person {
      private static final String DB_URL = "jdbc:sqlite:./databaseIMS.db";
      private static final int startID = 10000;
      private static int orderCount;
      public double balance = 0;
      public int id = startID;
      public ArrayList<String> phoneNumbers = new ArrayList<String>();

      public Client() {
      }

      public Client(String name, String email, String phone_no,String password, String address, double balance) {
            this.setPhone_no(phone_no);
            this.setPassword(password); 
            this.setEmail(email);
            this.setName(name);
            this.setAddress(address);
            this.balance = balance;
            this.id = startID;
            phoneNumbers.add(phone_no);
      }
      

      public double getBalance() {
            return balance;
      }

      public int getstartID() {
            return startID;
      }

      public static int getOrderCount() {
            return orderCount;
      }



      public static void addToClientTable(Client c) 
      {
            String sql = "INSERT INTO client (id, name, email, address, password, balance) VALUES (?, ?, ?, ?, ?, ?)";
            String maxIDQuery = "SELECT MAX(id) AS max_id FROM client";
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                  conn.setAutoCommit(false); // Enable transaction handling

                  // Retrieve the current max ID
                  try (Statement maxIDStatement = conn.createStatement();
                              ResultSet rs = maxIDStatement.executeQuery(maxIDQuery)) 
                  {
                        int max_ID;
                        rs.next();
                        if (rs.getInt(1) == 0) 
                        {
                              max_ID = startID;
                        } 
                        else 
                        {
                              max_ID = rs.getInt("max_id");
                        }

                        // Insert into the client table
                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) 
                        {
                              pstmt.setInt(1, max_ID + 1);
                              pstmt.setString(2, c.getName());
                              pstmt.setString(3, c.getEmail());
                              pstmt.setString(4, c.getAddress());
                              pstmt.setString(5, c.getPassword());
                              pstmt.setBigDecimal(6, BigDecimal.valueOf(c.balance));
                              pstmt.executeUpdate();
                        }

                        // Add phone number
                        addPhoneNumber(conn, max_ID + 1, c.getPhone_no());

                        // Commit the transaction
                        conn.commit();
                        System.out.println("Client and phone number added successfully.");
                  }
            } catch (SQLException e) {
                  System.out.println("Error: " + e.getMessage());
            }
      }



      public static void addPhoneNumber(Connection conn, int id, String phone) 
      {
            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO phone_numbers (id, phone_number) VALUES (?,?)")) 
            {
                  pstmt.setInt(1, id);
                  pstmt.setString(2, phone);

                  pstmt.executeUpdate();
                  // log the operation
            } 
            catch (SQLException e) 
            {
                  System.out.println(e.getMessage());
                  // log the error
            }
      }



      public static ArrayList<String> getPhoneNumbers(int id) 
      {
            ArrayList<String> ans = new ArrayList<String>();
            String sql = "SELECT phone_number FROM phone_numbers where id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL);
                        PreparedStatement p = conn.prepareStatement(sql)) {
                  p.setInt(1, id);
                  ResultSet rs = p.executeQuery();
                  while (rs.next()) {
                        ans.add(rs.getString(1));
                  }
            } catch (SQLException e) {

            }
            return ans;
      }



      public static Client getData(int id) throws SQLException {
            Client client = new Client();
            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "SELECT name, id, email, address, balance, password " +
                        "FROM client " +
                        "WHERE client.id = ?";

            PreparedStatement p = conn.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            rs.next();

            client.setName(rs.getString(1));
            client.id = rs.getInt(2);
            client.setEmail(rs.getString(3));
            client.setAddress(rs.getString(4));
            client.balance = rs.getDouble(5);
            client.setPassword(rs.getString(6));

            client.phoneNumbers.addAll(getPhoneNumbers(client.id));

            rs.close();
            p.close();
            conn.close();

            return client;

      }



      public static int getID(String email) {
            int id = -1;
            String sql = "SELECT id FROM client WHERE email = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                  PreparedStatement p = conn.prepareStatement(sql);
                  p.setString(1, email);
                  ResultSet rs = p.executeQuery();

                  if (rs.next()) {
                        id = rs.getInt(1);
                  }

                  rs.close();
                  p.close();
                  conn.close();
            } catch (SQLException e) {
                  System.out.println(e.getMessage());
            }
            return id;
      }



      public static boolean exists(String table, String column, String value) {
            String sql = "SELECT " + column + " FROM " + table + " WHERE " + column + " = ?";
            boolean result = false;
            try (Connection connection = DriverManager.getConnection(DB_URL);
                 PreparedStatement statement = connection.prepareStatement(sql)) 
            {
                  statement.setString(1, value);
                  ResultSet rs = statement.executeQuery();
                  while (rs.next()) {
                        result = value.equals(rs.getString(column));
                        if (result == true) {
                              break;
                        }
                  }
            } catch (SQLException e) {
                  System.out.println(e.getMessage());
            }
            return result;
      }



      public static int deletePhoneNumber(int id, String phoneNumber) throws SQLException {
            int rowsAffected = 0;
            String selectQuery = "select * from phone_numbers where id = ?";
            String deleteQuery = "delete from phone_numbers where id = ? and phone_number = ?";
            ArrayList<String> numbers = new ArrayList<String>();

            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                  conn.setAutoCommit(false);
                  try (PreparedStatement s = conn.prepareStatement(selectQuery)) {

                        s.setInt(1, id);
                        ResultSet rs = s.executeQuery();

                        while (rs.next()) {
                              numbers.add(rs.getString(2));
                        }
                  } catch (SQLException e) {
                        System.err.println("cannot establish a connection" + e.getMessage());
                        return 0;
                  }
                  if (numbers.contains(phoneNumber)) {
                        try (PreparedStatement d = conn.prepareStatement(deleteQuery)) {
                              d.setInt(1, id);
                              d.setString(2, phoneNumber);
                              rowsAffected = d.executeUpdate();
                        } catch (SQLException e) {
                              System.err.println(e.getCause());
                        }
                  } else {
                        throw new SQLException("phone number does not exist or does not belong to the customer");
                  }
                  conn.commit();
                  conn.close();
            }
            return rowsAffected;
      }



      public static void updatePhoneNumber(int id, String oldPhoneNumber, String newPhoneNumber) 
      {
            String sql = "UPDATE phone_numbers SET phone_number = ?  WHERE id = ?;";
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement p = conn.prepareStatement(sql)) 
            {
                  if (!exists("phone_numbers", "phone_number", newPhoneNumber)) 
                  {
                        p.setString(1, newPhoneNumber);
                        p.setInt(2, id);
                        p.executeUpdate();
                  }
            } 
            catch (SQLException e) 
            {
                  e.printStackTrace();
            }
      }



      public static void updateRow(String table, String column, int id, String newVal)
      {
            String sql = "UPDATE " + table + " SET " + column + " = ? WHERE id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                  PreparedStatement pstmt = conn.prepareStatement(sql);
                  pstmt.setString(1, newVal);
                  pstmt.setInt(2, id);
                  pstmt.executeUpdate();
            } catch (SQLException e) {
                  System.out.println(e.getMessage());
            }
      }



      public static void updateDatabase(Client c) 
      {
            String sql = "UPDATE client SET name = ?, email = ?, address = ?, password = ?, balance = ? WHERE id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                  conn.setAutoCommit(false);
                  try 
                  {
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, c.getName());
                        pstmt.setString(2, c.getEmail());
                        pstmt.setString(3, c.getAddress());
                        pstmt.setString(4, c.getPassword());
                        pstmt.setBigDecimal(5, BigDecimal.valueOf(c.balance));
                        pstmt.setInt(6, c.id);
                        pstmt.executeUpdate();
                        System.out.println("Client data updated successfully.");
                  } 
                  catch (SQLException e) 
                  {
                        System.out.println("Error: " + e.getMessage());
                        e.printStackTrace();
                  }
                  conn.commit();
            } 
            catch (SQLException e) 
            {
                  System.out.println(e.getStackTrace());
            }
      }

/*       public static void main(String[] args) {
            // Usage example
            System.out.println(Client.exists("client", "name", "x"));
      } */
}
