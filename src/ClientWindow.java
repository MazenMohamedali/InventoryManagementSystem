import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
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
                  System.err.println("Error: " + e.getMessage());
                  return 0;
            }
            return option;
      }


      public static void viewProducts()//TODO "Warning: products such and such are nearing the expiry date
      {
            String pattern = "dd-MM-yyyy";
            String dateInString = new SimpleDateFormat(pattern).format(new Date());
            System.out.println("date: "+ dateInString);
            Product.showAllforClient();
            printSeparator();
      } 



      public static void createOrder(int id)//TODO finish prompts
      {
            viewProducts();
            
            ArrayList<String> productNames = new ArrayList<String>();

            String temp = "";
            while(!temp.equalsIgnoreCase("exit"))
            {
                  try
                  {
                        System.out.print("Please enter a product's name (enter 'exit' to finish, 'cancel' to return to menu): ");
                        temp = in.next();
                      
                        if(temp.equalsIgnoreCase("exit"))
                        {
                              break;
                        }

                        if(temp.equalsIgnoreCase("cancel"))
                        {
                              return;
                        }


                        boolean found = Client.exists("product","name",temp);
                        if(!productNames.contains(temp) & found)
                        {
                              productNames.add(temp);
                        }
                        else if(!found)
                        {
                              printSeparator();
                              System.err.println("This product is not on the list");
                              printSeparator();
                              continue;
                        }
                        


                        else if(productNames.contains(temp))
                        {
                              printSeparator();
                              System.out.println("this product has already been entered");
                              printSeparator();
                        }
                  }
                  catch(Exception e)
                  {
                        System.err.println("Error: " + e.getMessage());
                  }
            }
            
            int[] amounts = new int[productNames.size()];
            printSeparator();
            System.out.println("Please enter the amount of each product:");
            for(int i = 0; i < productNames.size(); i++)
            {
                  System.out.print(i + ": " + productNames.get(i) + ": ");
                  int amount = in.nextInt();
                  if(amount <= 0)
                  {
                        printSeparator();
                        System.out.println("Invalid amount. Please enter a positive integer");
                        i--;
                        printSeparator();
                        continue;
                  }

                  amounts[i] = amount;
            }
            printSeparator();
            System.out.println("You Entered:");
            for(int i = 0; i < productNames.size(); i++)
            {
                  System.out.println(productNames.get(i) + ": " + amounts[i] + " units");
            }

            System.out.println("is This correct? (y/n)");
            String ans = in.next().toLowerCase();

            boolean validInput = false;
            do
            {
                  if(ans.equals("y"))
                  {
                        
                        try 
                        {
                              Client c = Client.getData(id);
                              validInput = true;
                              String[] products = new String[productNames.size()];
                              for(int i = 0; i < productNames.size(); i++)
                              {
                                    products[i] = productNames.get(i);
                              }
      
                              Order.processOrder(products, amounts, c);
                              
                              printSeparator();
                              System.out.println("order has been placed! Please check the invoice or report to view the order's details");
                              printSeparator();
                        } 
                        catch (SQLException e) 
                        {
                              printSeparator();
                              System.err.println("Error: " + e.getMessage());
                              printSeparator();
                        }
                  }
                  else if(ans.equals("n"))
                  {
                        validInput = true;
                        for(int i = 0; i < productNames.size(); i++)
                        {
                              printSeparator();
                              for(int j = 0; j < productNames.size(); j++)
                              {
                                    System.out.println(j + ": " + productNames.get(j) + ": " + amounts[j] + " units");
                              }
                              System.out.print("\nre-enter the name of the product (enter 'skip' to skip this product and 'exit' to finish)\n" + i + ": ");
                              temp = in.next();
                              boolean found = Client.exists("product", "name", temp);

                              printSeparator();
                              if(temp.equalsIgnoreCase("skip"))
                              {
                                    continue;
                              }
                              if (temp.equalsIgnoreCase("exit")) 
                              {
                                    break;      
                              }
                              if(!found)
                              {
                                    printSeparator();
                                    System.err.println("This product is not on the list");
                                    continue;
                              }
                              productNames.set(i, temp);
                              System.out.print("Please re-enter the amount of " + productNames.get(i) + ": ");
                              amounts[i] = in.nextInt();

                              ans = "y";
                              continue;
                        }
                        printSeparator();
                        System.out.println("order has been placed! Please check the invoice or report to view the order's details");
                        printSeparator();
                  }
                  else
                  {
                        validInput = false;
                        printSeparator();
                        System.err.println("invalid input");
                        printSeparator();
                  }
            }while(!validInput);
      }



      public static void editAccount(int id)
      {
            try 
            {
                  int choice = 0;          
                  Client client = Client.getData(id);
                  do
                  {
                        printSeparator();
                        System.out.println("id: " + client.id);
                        System.out.println("Name: " + client.getName());
                        System.out.println("Email: " + client.getEmail());
                        System.out.println("Phone numbers: ");
                        int count = 0;
                        for(String phone : client.phoneNumbers)
                        {
                              System.out.print(count + 1 + ": " +  phone + "\n");
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
            try
            {
                  while(exit != true)
                  {
                        int option = getOption(id);

                        if(option == 1)//create order
                        {
                              createOrder(id);
                        }
                        else if(option == 2)//view report
                        {
                              ClientReport.genReport(id);
                        }
                        else if(option == 3)//view products
                        {
                              viewProducts();
                        }                                  
                        else if(option == 4)//edit account
                        {
                              editAccount(id);
                        }
                        else if(option == 5)//log out
                        {
                              printSeparator();
                              System.out.println("Goodbye!");
                              printSeparator();
                              exit = true;
                              break;
                        }    
                  }
            }
            catch(InputMismatchException e)
            {
                  System.err.println("Invalid input. Please enter a number.");
                  in.nextLine();
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
            show(1);
      }

      
}
