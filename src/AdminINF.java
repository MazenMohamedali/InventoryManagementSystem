
import java.util.*;

public class AdminINF {
    static Scanner input = new Scanner(System.in);

    public static void setVisible() {
        System.out.println("\t \t>>Admin interface loaded<<");
        System.out.println("CHOESE OPTION OR -1 TO CLOSE PROGRAM");
        System.out.print("1. Supplier Menu\n");
        System.out.print("Enter Choice :");
        int x = input.nextInt();
        input.nextLine();
        switch (x) {
            case -1:
                System.exit(0);
                break;
            case 1:
                supllierMenu();
            default:
                System.out.println("\t-->Wrong input try later<--");
                break;
        }
    }

    static void supllierMenu() {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Add Supplier");
        System.out.println("2. Delete Supplier");
        System.out.println("3. Add More Than One Supplier");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        input.nextLine();
        switch (choice) {
            case 1:
                addSupplier();
                supllierMenu();
                break;
            case 2:
                deleteSupplier();
                supllierMenu();
                break;
            case 3:
                System.out.println("Enter Number Of Suppliers:");
                int cntr = input.nextInt();
                input.nextLine();
                addSupplier(cntr);
            default:
                System.out.println("\t-->Wrong input try later<--");
                break;
        }
    }

    static void addSupplier() {
        System.out.print("Enter Supplier Name: ");
        String name = input.nextLine();

        System.out.print("Enter Supplier Email: ");
        String email = input.nextLine();

        System.out.print("Enter Supplier Phone: ");
        String phone = input.nextLine();

        Supplier supplier = new Supplier(name, email, phone);
        if (Admin.addSupplier(supplier)) {
            System.out.println("Supplier " + name + " has been Added");

        } else {
            System.out.println("Somthing Went Wrong");
            System.exit(1);
        }
    }

    static void addSupplier(int n) {
        while (n > 0) {
            addSupplier();
            n--;
        }
    }

    static void deleteSupplier() {

        System.out.print("Enter Supplier ID: ");
        int id = input.nextInt();
        input.nextLine();

        if (Admin.deleteSupplier(id)) {
            System.out.println("Supplier has been Deleted");

        } else {
            System.out.println("Somthing Went Wrong");
        }
    }

    public static void main() {
        AdminINF.setVisible();
    }
}
