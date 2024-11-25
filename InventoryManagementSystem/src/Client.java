/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yousf
 */
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



public class Client extends Person 
{
      private static int count;
      private     String clientID;
      private static int orderCount;
      private     double balance = 0;

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

      public double getBalance() {
            return balance;
      }

      public static int getCount() {
            return count;
      }

      
      public String getClientID() {
            return clientID;
      }
      
      public static int getOrderCount() {
            return orderCount;
      }
      
      public void/*For now*/getData()
      {
            //connect to the database, and get the client's complete data
      }
      public void placeOrder()
      {
            orderCount++;
            //the products ordered get reduced
      }
}