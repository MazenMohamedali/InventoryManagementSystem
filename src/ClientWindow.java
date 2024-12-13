import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientWindow 
{
      static Scanner in = new Scanner(System.in);
      public static int getOption()
      {
            int option = -1;
            System.out.println("WELCOME: ");//TODO get name 
            System.out.println("please enter a number corresponding to an option:");
            System.out.println("1. Create an order");
            System.out.println("2. Create a report");
            System.out.println("3. View products");
            System.out.println("4. Edit account");
            System.out.println("5. Log out");
            System.out.print("Your Choice? ");
            option = in.nextInt();
            return option;
      }



      public static void createOrder()
      {
            
            ArrayList<String> productNames = new ArrayList<String>();

            while(true)
            {
                  System.out.print("Please enter the product's name (enter 'exit' to finish): ");
                  String temp = in.next();
                  System.out.println('\n');
                  if(!temp.equalsIgnoreCase("exit"))
                  {
                        productNames.add(temp);
                  }
                  else
                  {
                        break;
                  }
            }
            
            int[] amounts = new int[productNames.size()];
            System.out.println("Please enter the amount of each product:");
            for(int i = 0; i < productNames.size(); i++)
            {
                  System.out.print(i + ". " + productNames.get(i) + ": ");
                  
                  amounts[i] = in.nextInt();
            }

            System.out.println("You Entered:");
            for(int i = 0; i < productNames.size(); i++)
            {
                  System.out.println(productNames.get(i) + ": " + amounts[i] + " units");
            }

            System.out.println("is This correct? (y/n)");
            String ans = in.next().toLowerCase();

            if(ans.equals("y"))
            {
                  //TODO update the DB
                  System.out.println("order has been placed! Please check the invoice or report to view the order's details");
                  System.out.println("_______________________________________________________________");
            }
            else
            {
                  for(int i = 0; i < productNames.size(); i++)
                  {
                        System.out.println("\n");
                        for(int j = 0; j < productNames.size(); j++)
                        {
                              System.out.println(productNames.get(j) + ": " + amounts[j] + " units");
                        }
                        System.out.print("\nre-enter the name of the product (enter 'next' to skip this product)" + i + ": ");
                        String temp = in.next();
                        if(temp.equalsIgnoreCase("next"))
                        {
                              continue;
                        }
                        
                        productNames.set(i, temp);
                        System.out.print("Please re-enter the amount of " + productNames.get(i) + ": ");
                        amounts[i] = in.nextInt();  
                        //TODO update DB
                  }

            }
      }



      public static void editAccount(int id)
      {
            try 
            {
                  int choice = 0;          
                  ArrayList<Client> client = Client.getData(id);
                  do
                  {
                        System.out.println("What would you like to change (enter 0 to exit)?");
                        System.out.println("1. Email");
                        System.out.println("2. Phone");
                        System.out.println("3. Password");
                        System.out.print("Your choice? ");
                        choice = in.nextInt();
                        in.nextLine();
                        
                        if(choice == 1)
                        {
                              System.out.print("Enter your new email address: ");
                              String newEmail = in.nextLine();
                              Connection conn = DriverManager.getConnection(connectDB.getDburl());
                              PreparedStatement p = conn.prepareStatement("SELECT email FROM client WHERE email = ?");
                              p.setString(1, newEmail);
                              ResultSet rs = p.executeQuery();
                              boolean found = false;
                              while(rs.next())
                              {
                                    String foundEmail = rs.getString(1);
                                    if(foundEmail.equals(newEmail))
                                    {
                                          found = true;
                                          break;
                                    }
                              }
                              if(found)
                              {
                                    System.out.println("this email address is already in the database");
                                    continue;
                              }
                              else
                              {
                                    for(var i : client)
                                    {
                                          i.setEmail(newEmail);
                                    }
                              }
                              conn.close();
                        }
                        else if(choice == 2)
                        {
                              System.out.print("Enter your new phone number: ");
                              String newNumber = in.next();
                              Connection conn = DriverManager.getConnection(connectDB.getDburl());
                              PreparedStatement p = conn.prepareStatement("SELECT phone_number FROM phone_numbers WHERE phone_number = ?");
                              p.setString(1, newNumber);
                              ResultSet rs = p.executeQuery();
                              boolean found = false;
                              while(rs.next())
                              {
                                    String foundNumber = rs.getString(1);
                                    if(foundNumber.equals(newNumber))
                                    {
                                          found = true;
                                          break;
                                    }
                              }
                              if(found)
                              {
                                    System.out.println("this phone number is already in the database");
                                    continue;
                              }
                              else
                              {
                                    for(var i : client)
                                    {
                                          i.setPhone_no(newNumber);
                                    }
                              }
                              conn.close();
                        }
                        else if(choice == 3)
                        {
                              System.out.println("Please enter your password: ");
                              String password = in.next();
                              if(client.get(0).getPassword() == password)
                              {
                                    System.out.println("Please enter the new password:");
                                    String newPassword = in.next();
                                    client.get(0).setPassword(newPassword);
                                    System.out.println("Password updated successfully!");
                              }
                              break;
                        }
      
                  }while(choice != 0);
                  
                  for(Client c : client)
                  {
                        Client.updateData(c);
                  }
            } 
            catch (SQLException e) 
            {
                  e.printStackTrace();
            } 
      }


      public static void show()
      {
            int option = getOption();
            switch (option) 
            {
                  case 1:
                        createOrder();
                        break;
                  case 2:
                        break;
                  case 3:
                        break;
                        
                  case 4:
                        editAccount();
                        break;
                  case 5:
                        System.out.println("Goodbye!");
                        System.exit(0);
                        break;
                  default:
                        System.out.println("Invalid option. Please try again.");
                        break;
            }
      }
      public static void main()
      {            
            show();
            in.close();
      }
}
