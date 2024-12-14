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
    
    public static void ClientLog() {
        Scanner inpuScanner = new Scanner(System.in);
        int yourTry = 3;
        String usr;
        String pass;
        while (yourTry-- != 0) {
            System.out.print("Enter Client Username: ");
            usr = inpuScanner.nextLine();
            System.out.print("Enter Client Password: ");
            pass = inpuScanner.nextLine();
            if(Passwords.CheckClient(usr, pass)) {
                separator();
                System.out.print("Enter your ID: ");
                int id = inpuScanner.nextInt();
                separator();
                ClientWindow.show(id);
            }
            else  {
                System.out.println("ERROR Your Username or Password Wrong you have " + yourTry + " tryes");
                separator();
            }
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
                ClientLog();
            }
        }
    }

}
