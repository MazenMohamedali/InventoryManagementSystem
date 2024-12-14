import java.lang.classfile.instruction.SwitchCase;
import java.util.Scanner;

public class login {

    public static void separator() {
        System.out.println("------------------------------------------------------------------------");
    }
    
    public static void AdminLog() {
        Scanner inpuScanner = new Scanner(System.in);
        int yourTry = 3;
        String usr;
        String pass;
        while (yourTry-- != 0) {
            System.out.print("Enter Admin User Name: ");
            usr = inpuScanner.nextLine();
            System.out.print("Enter Admin Password: ");
            pass = inpuScanner.nextLine();
            if(Passwords.CheckAdmin(usr, pass)) {
                AdminINF.setVisible();
                separator();
            }
            else  {
                System.out.println("ERROR Your Username or Password Wrong you have " + yourTry + " tryes");
                separator();
            }
        }
    }
    


    public static void clientRegister()
    {
        Client c = new Client();
        Scanner in = new Scanner(System.in);
        separator();
        System.out.println("----Client Registration----");
        System.out.print("Enter your Name: ");
        c.setName(in.nextLine());
        System.out.print("Enter your email: ");
        c.setEmail(in.next());
        System.out.println("----Please bear in mind that you will login into the system with you email----");
        System.out.print("Enter your Address: ");
        c.setAddress(in.next());
        
        String password = "";
        String confirmPassword = "";
        boolean isEqual = false;
        while (!isEqual)
        {
            System.out.print("Enter your Password: ");
            password = in.next();
            System.out.print("Confirm your password: ");
            confirmPassword = in.next();
            isEqual = password.equals(confirmPassword);
            if(!isEqual)
            {
                System.err.println("passwords do not match");
            }
        }

        c.setPassword(password);
        System.out.print("Enter your Phone: ");
        c.phoneNumbers.add(in.next());
        Boolean found = Client.exists("client", "email", c.getEmail());
        if(found)
        {
            System.out.println("this email address is already in the database");
            System.out.println("you will be switched to the login page");
            ClientLog();
            return;
        }
        Client.addToClientTable(c);
        if(Client.exists("client", "email", c.getEmail()))
        {
            separator();
            System.out.println("You have been added Successfully!!");
            separator();
            ClientWindow.show(Client.getID(c.getEmail()));
        }
        else
        {
            System.out.println("Something went wrong");
        }
    }


    
    public static void ClientLog()
    {
        Scanner inpuScanner = new Scanner(System.in);
        int yourTry = 3;
        int emailNotInDBCount = 0;
        while (yourTry != 0) 
        {  
            if(emailNotInDBCount > 1)
            {
                System.out.print("Would you like to sign up(y/n)? ");
                String ans = inpuScanner.next().toLowerCase();
                if(ans.equals("y"))
                {
                    clientRegister();
                    return;
                }
            }          
            System.out.print("Enter your Email: ");
            String email = inpuScanner.next();
            boolean found = Client.exists("client", "email", email);
            if(found)
            {
                try
                {
                    int id = Client.getID(email);
                    Client c = Client.getData(id);
                    boolean isEqual = false;
                    do
                    {
                        System.out.print("Enter your password: ");
                        String password = c.getPassword();
                        String inPass = inpuScanner.next();
                        isEqual = inPass.equals(password);
                        if(isEqual)
                        {
                            ClientWindow.printSeparator();
                            ClientWindow.show(id);
                            return;
                        }
                        else
                        {
                            System.err.println("Password is incorrect, you have " + yourTry + " tryes");
                            yourTry--;
                            continue;
                        }
                    }while(!isEqual);
                }
                catch (Exception e)
                {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            else
            {
                System.out.println("This email is not in the database");
                emailNotInDBCount++;
                continue;
            }
            separator();
        }
    }
    
    public static void main() {
        try (Scanner inpuScanner = new Scanner(System.in)) {
            int Selection = -1;
            
            System.out.println("1-You are Admin\n2-You are Client\nyour Selection:");
            boolean falseSelction = true;
            while (falseSelction) {
                Selection = inpuScanner.nextInt();
                if(Selection == 1 || Selection == 2)
                    falseSelction = false;
            }
            separator();

            if(Selection == 1) {
                AdminLog();
            } else {
                separator();
                System.out.println("would you like to login(1) or sign-up(2)? enter a number: ");
                int choice = inpuScanner.nextInt();
                if (choice == 1) 
                {
                    ClientLog();    
                }
                else if (choice == 2)
                {
                    clientRegister();
                }
                else
                {
                    System.out.println("Invalid Selection");
                    separator();
                    main();
                }
            }
        }
    }
}
