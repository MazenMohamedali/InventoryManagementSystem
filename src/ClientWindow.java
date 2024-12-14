import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientWindow //TODO shit spazes out when you enter a string instead of a number
{
      static Scanner in = new Scanner(System.in);
      public static int getOption(int id)
      {
            int option = -1;
            try
            {
                  Client c = Client.getData(id);
                  System.out.println("WELCOME: " + c.getName());
                  System.out.println("please enter a number corresponding to an option:");
                  System.out.println("1. Create an order");
                  System.out.println("2. View your report");
                  System.out.println("3. View products");
                  System.out.println("4. Edit account");
                  System.out.println("5. Log out");
                  System.out.print("Your Choice? ");
                  option = in.nextInt();
                  printSeparator();
            }
            catch(Exception e)
            {
                  e.printStackTrace();
            }
            return option;
      }


      public static void viewProducts()//TODO "Warning: products such and such are nearing the expiry date
      {
            Product.showAllforClient();
            printSeparator();
      } 

      

      public static void createOrder()//TODO finish prompts
      {
            
            ArrayList<String> productNames = new ArrayList<String>();

            String temp = "";
            while(!temp.equalsIgnoreCase("exit"))
            {
                  System.out.print("Please enter a product's name (enter 'exit' to finish): ");
                  temp = in.next();

                  
                  if(!temp.equalsIgnoreCase("exit") & !productNames.contains(temp))
                  {
                        productNames.add(temp);
                  }
                  else if(productNames.contains(temp))
                  {
                        printSeparator();
                        System.out.println("this product has already been enterd");
                        printSeparator();
                  }
            }
            
            int[] amounts = new int[productNames.size()];
            printSeparator();
            System.out.println("Please enter the amount of each product:");
            for(int i = 0; i < productNames.size(); i++)
            {
                  System.out.print(i + ": " + productNames.get(i) + ": ");
                  
                  amounts[i] = in.nextInt();
            }
            printSeparator();
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
                  printSeparator();
            }
            else
            {
                  for(int i = 0; i < productNames.size(); i++)
                  {
                        printSeparator();
                        for(int j = 0; j < productNames.size(); j++)
                        {
                              System.out.println(j + ": " + productNames.get(j) + ": " + amounts[j] + " units");
                        }
                        System.out.print("\nre-enter the name of the product (enter 'next' to skip this product)\n" + i + ": ");
                        temp = in.next();
                        printSeparator();
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

      public static void addAccount()
      {
            printSeparator();
            System.out.print("Enter your name: ");
            String name = in.next();
            

            String email = "";
            boolean exists = true;
            while(exists)
            {
                  System.out.print("Enter your email: ");
                  email = in.next();
                  exists = Client.exists("client", "email", email);
                  if(exists)
                  {
                        System.out.println("Email already exists, enter a different one");
                        printSeparator();
                  }
                  else
                  {
                        exists = false;
                  }
            }   
            
            
            String phone = "";
            exists = true;
            while(exists)
            {
                  System.out.print("Enter your phone number: ");
                  phone = in.next();
                  exists = Client.exists("phone_numbers", "phone_number", phone);
                  if(exists)
                  {
                        System.out.println("Phone number already exists, enter a different one");
                        printSeparator();
                  }
                  else
                  {
                        exists = false;
                  }
            }
            
            
            String password = "";
            boolean confirmed = false;
            while(!confirmed)
            {
                  System.out.print("Enter your password: ");
                  password = in.next();
                  System.out.print("Confirm your password: ");
                  String confirmPassword = in.next();
                  if(confirmPassword.equals(password))
                  {
                        confirmed = true;
                  }
                  else
                  {
                        System.out.println("Passwords do not match");
                  }
            }


            System.out.println("Enter your address");
            String address = in.next();

            Client c = new Client(name, email, password, address, phone, 0);
            Client.addToClientTable(c);
            printSeparator();
            System.out.println("You have been added successfully!!");
            show(Client.getID(email));

            
            System.out.print("Enter your address: ");
      }

      public static void editAccount(int id)
      {
            try 
            {
                  int choice = 0;          
                  Client client = Client.getData(id);
                  do
                  {
                        System.out.println("id: " + client.id);
                        System.out.println("Name: " + client.getName());
                        System.out.println("Email: " + client.getEmail());
                        System.out.println("Phone numbers: ");
                        int count = 0;
                        for(String phone : client.phoneNumbers)
                        {
                              System.out.print(count + 1 + phone + "\n");
                              count++;
                        }
                        System.out.println("Address: " + client.getAddress());
                        System.out.println("Balance: " + client.getBalance());
                        printSeparator();
                        System.out.println("What would you like to change? enter a number corresponding to a choice (enter 0 to exit)");
                        System.out.println("1.Change Email");
                        System.out.println("2.Change Phone");
                        System.out.println("3.Change Password");
                        System.out.println("4.Add phone number");
                        System.out.println("5.Add balance");
                        System.out.print("Your choice? ");
                        choice = in.nextInt();
                        printSeparator();
                        in.nextLine();
                        
                        if(choice == 1)//email
                        {
                              System.out.print("Enter your new email address: ");
                              String newEmail = in.next();
                              boolean found;
                              found = Client.exists("client", "email", newEmail);
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

                              System.out.println("\nWhich phone number would you like to change(enter the number before the ':')? ");
                              int ans = in.nextInt();

                              boolean found = Client.exists("phone_numbers", "phone_number", newNumber); 
                              if(found)
                              {
                                    System.out.println("this phone number is already in the database");
                                    continue;
                              }
                              else
                              {
                                    Client.updatePhoneNumber(client.id, phoneNumbers.get(ans), newNumber);                                
                                    System.out.println("phone number updated successfully!!");
                              }
                        }
                        else if(choice == 3)//password
                        {
                              System.out.println("Please enter your password: ");
                              String password = in.next();
                              if(client.getPassword().equals(password))
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
                        }
                        else if(choice == 4)//add phone
                        {
                              System.out.print("Please enter the phone number you wish to add: ");
                              String newNumber = in.next();
                              
                              boolean found = Client.exists("phone_numbers", "phone_number", newNumber);
                              if(found == false)
                              {
                                    client.phoneNumbers.add(newNumber);
                                    try(Connection conn = DriverManager.getConnection(connectDB.getDburl()))
                                    {
                                          Client.addPhoneNumber(conn, id, newNumber);
                                    }
                              }
                              else
                              {
                                    System.out.println("Number already exists");
                              }
                        }
                        else if(choice == 5)//add balance
                        {
                              System.out.print("Please enter the amount to add: ");
                              double amount = in.nextDouble();
                              client.balance += amount;
                        }
      
                  }while(choice != 0);
                  
                  Client.updateDatabase(client);
                  printSeparator();

            } 
            catch (SQLException e) 
            {
                  e.printStackTrace();
            } 
      }


      public static void show(int id)
      {
            boolean exit = false;
            while(exit != true)
            {

                  int option = getOption(id);
                  switch (option) 
                  {
                        case 1://create order
                              createOrder();
                              break;
                        case 2://view report
                              ClientReport.genReport(id);
                              break;
                        case 3://view products
                              viewProducts();
                              break;                             
                        case 4://edit account
                              editAccount(id);
                              break;
                        case 5://log out
                              System.out.println("Goodbye!");
                              exit = true;
                              break;
                        default:
                              System.out.println("Invalid option. Please try again.");
                              break;
                  }
            }
      }

      public static void printSeparator()
      {
            System.out.println();
            System.out.println("_______________________________");
            System.out.println();
      }


      public static void main()
      {
            show(10102);
      }

      
}
