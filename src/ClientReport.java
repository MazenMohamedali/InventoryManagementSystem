import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientReport 
{
      public static void printLine()
      {
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
      }
      public static void genReport(int id)
      {
            try(Connection conn = DriverManager.getConnection(connectDB.getDburl()))
            {
                  String sql = 
                  "SELECT " + 
                  "      client.name, " +
                  "      orders.price, " +
                  "      orders.order_datetime, " +
                  "      orders.arrival_date, " +
                  "      product.name, " +
                  "      order_prod.quantity, " +
                  "      product.price as price_per_prod " +
                  "FROM" +
                  "      orders " +
                  "JOIN" +
                  "      client ON orders.client_id = client.id " +
                  "JOIN" +
                  "      order_prod ON orders.id = order_prod.order_id " +
                  "JOIN" +
                  "      product ON order_prod.prod_id = product.id " +
                  "WHERE client.id = ?;";

                  PreparedStatement p = conn.prepareStatement(sql);
                  p.setInt(1, id);
                  ResultSet rs = p.executeQuery();
                  printLine();
                  System.out.println("Name\t\tPrice\t\tOrdered at\t\t\tArrival Date\t\tProduct name\t\tProduct Quantity\t\tPrice/product");
                  while(rs.next())
                  {
                        printLine();
                        System.out.println(rs.getString(1) + "\t" + rs.getDouble(2) + "\t\t" + rs.getString(3) + "\t\t" + rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t\t" + rs.getInt(6) + "\t\t\t\t" + rs.getDouble(7));
                        printLine();
                  }
                  ClientWindow.printSeparator();
            }
            catch(SQLException e)
            {
                  ClientWindow.printSeparator();
                  System.err.println("Error: " + "" + e.getMessage());
                  ClientWindow.printSeparator();
            }
      }
}
