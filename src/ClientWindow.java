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
            System.out.println("2. View your report");
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
                  Client client = Client.getData(id);
                  do
                  {
                        System.out.println("What would you like to change (enter 0 to exit)?");
                        System.out.println("1. Email");
                        System.out.println("2. Phone");
                        System.out.println("3. Password");
                        System.out.print("Your choice? ");
                        choice = in.nextInt();
                        in.nextLine();
                        
                        if(choice == 1)//email
                        {
                              System.out.print("Enter your new email address: ");
                              String newEmail = in.next();
                              boolean found;
                              found = Client.exists("client", "email", client.getEmail());
                              if(found)
                              {
                                    System.out.println("this email address is already in the database");
                                    continue;
                              }
                              else
                              {
                                    client.setEmail(newEmail);                                    
                                    System.out.println("email updated successfully!!");
                              }
                        }
                        else if(choice == 2)//phone
                        {
                              System.out.print("Enter your new phone number: ");
                              String newNumber = in.next();
                              System.out.println("");

                              var phoneNumbers = Client.getPhoneNumbers(id);
                              System.out.println("the phone numbers you currently have:");
                              for(int i = 0; i < phoneNumbers.size(); i++)
                              {
                                    System.out.println(i + ": " + phoneNumbers.get(i));
                              }
                              System.out.println("\n Which phone number would you like to change(enter the number before the ':')? ");
                              int ans = in.nextInt();
                              boolean found = Client.exists("phone_numbers", "phone_number", newNumber); 
                              if(found)
                              {
                                    System.out.println("this phone number is already in the database");
                                    continue;
                              }
                              else
                              {
                                    client.setPhone_no(newNumber);
                              }
                        }
                        else if(choice == 3)//password
                        {
                              System.out.println("Please enter your password: ");
                              String password = in.next();
                              if(client.getPassword() == password)
                              {
                                    System.out.println("Please enter the new password:");
                                    String newPassword = in.next();
                                    client.setPassword(newPassword);
                                    System.out.println("Password updated successfully!");
                              }
                              else
                              {
                                    System.out.println("Incorrect password");
                                    continue;
                              }
                              break;
                        }
      
                  }while(choice != 0);
                  
                  Client.updateData(client);
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
