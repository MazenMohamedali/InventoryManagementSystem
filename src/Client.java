
/*

/**
 *
 * @author yousf
 */

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;

//comment class Person for implementation with actual Person class
class Person 
{
      String name;
      String email;
      String password;
      String address;
      
      Person(String name, String email, String password, String address)
      {
            this.name     = name;
            this.email    = email;
            this.password = password;
            this.address  = address;
      }
}

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
      private static int count;
      private String clientID;
      private static int orderCount;
      private double balance = 0;

      Client(String name, String email, String password, String address)
      {
            super(name, email, password, address);
            clientID = setID(count);
      }

      private String setID(int count)
      {
            count++;
            return "C" + count;
      }

      public double getBalance()
      {
            return balance;
      }

      public static int getCount()
      {
            return count;
      }

      
      public String getClientID()
      {
            return clientID;
      }
      
      public static int getOrderCount()
      {
            return orderCount;
      }
      
      public void/*For now*/getData()
      {
            //connect to the database, and get the client's complete data
      }
      public void placeOrder(int[] orderIDs)
      {
            orderCount++;
            Order order = new Order(orderIDs);
            //the products ordered get reduced
      }
}