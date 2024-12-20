
import java.util.*;

public class AdminINF {
    static Scanner input = new Scanner(System.in);

    public static void setVisible() {
        System.out.println("\t \t>>Admin interface loaded<<");
        System.out.println("CHOESE OPTION OR -1 TO CLOSE PROGRAM");
        System.out.print("1. Supplier Menu\n");
        System.out.print("2. Product Menu\n");
        System.out.print("Enter Choice :");
        int x = input.nextInt();
        input.nextLine();
        switch (x) {
            case -1:
                System.exit(0);
                break;
            case 1:
                supllierMenu();
                setVisible();
                break;
            case 2:
                productMenu();
                setVisible();
                break;
            default:
                System.out.println("\t-->Wrong input try later<--");
                break;
        }
    }

    // ###############################################################################################
    static void supllierMenu() {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Add Supplier");
        System.out.println("2. Delete Supplier");
        System.out.println("3. Add More Than One Supplier");
        System.out.println("4. Upate Supplier Email");
        System.out.println("5. Show Suppliers");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        input.nextLine();
        switch (choice) {
            case 1:
                addSupplier();
                break;
            case 2:
                deleteSupplier();
                break;
            case 3:
                System.out.println("Enter Number Of Suppliers:");
                int cntr = input.nextInt();
                input.nextLine();
                addSupplier(cntr);
                break;
            case 4:
                updateSupplierPhone();
                break;
            case 5:
                Supplier.showAll();
                break;
            case 6:
                setVisible();
                break;
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
        }
    }

    static void addSupplier(int n) {
        while (n > 0) {
            addSupplier();
            n--;
        }
    }

    static void deleteSupplier() {

        Supplier.showAll();

        System.out.print("Enter Supplier ID: ");
        int id = input.nextInt();
        input.nextLine();

        if (Supplier.checkSupllierId(id)) {

            if (Admin.deleteSupplier(id)) {
                System.out.println("Supplier has been Deleted");

            } else {
                System.out.println("Somthing Went Wrong");
            }
        } else
            System.out.println("No Such Supplier");
    }

    // UPATE SUPPLIER PHONE
    static void updateSupplierPhone() {
        Supplier.showAll();

        System.out.print("Enter Supplier ID: ");
        int id = input.nextInt();
        input.nextLine();

        if (Supplier.checkSupllierId(id)) {
            Supplier.show(id);
            System.out.print("Enter New Email: ");
            String email = input.nextLine();
            if (Admin.updateSupplierEmail(id, email)) {
                Supplier.show(id);
                System.out.println("DONE");

            } else {
                System.out.println("Somthing Went Wrong");
            }
        } else
            System.out.println("No Such Supplier");
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------

    // PROUCT MENU
    static void productMenu() {
        System.out.println("\nProduct Menu:");
        System.out.println("1. Add Product");
        System.out.println("2. Delete Product");
        System.out.println("3. Update Product");
        System.out.println("4. Display All Products");
        System.out.println("5. Exit to Main Menu");
        System.out.print("Enter your choice: ");

        int choice = input.nextInt();
        input.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                addProduct();
                // productMenu();
                break;
            case 2:
                deleteProduct();
                // productMenu();
                break;
            case 3:
                updateProduct();
                break;
            // productMenu();
            // break;
            case 4:
                Product.showAllforClient();
                break;
            // productMenu();
            // break;
            case 5:
                setVisible();
                break;
            default:
                System.out.println("\t-->Wrong input try later<--");
                break;
        }
    }

    // ADD PRODUCT
    private static void addProduct() {

        System.out.print("Enter Product Name: ");
        String name = input.nextLine();

        System.out.print("Enter Product Price: ");
        double price = input.nextDouble();

        System.out.print("Enter Product Quantity: ");
        int quantity = input.nextInt();
        input.nextLine();

        System.out.print("Enter Product Category: ");
        String category = input.nextLine();

        System.out.print("Enter Supplier ID: ");
        int supplierID = input.nextInt();
        input.nextLine();

        System.out.print("Enter Profit ratio ");
        int ProfitRatio = input.nextInt();
        input.nextLine();
        if (!Supplier.checkSupllierId(supplierID)) {
            System.out.println("Supplier id not found");
            System.exit(1);
        }

        System.out.print("Enter Expiration Date (dd-mm-yyyy): ");
        String expireDateStr = input.nextLine();

        System.out.print("Enter Production Date (dd-mm-yyyy): ");
        String productionDateStr = input.nextLine();
        Product product = new Product(name, price, quantity, supplierID, category, expireDateStr,
                productionDateStr, ProfitRatio);
        if (Admin.insertProduct(product)) {
            System.out.println("Product " + name + " has been Added");

        } else {
            System.out.println("Somthing Went Wrong");
        }
    }

    // DELETE PRODUCT
    static void deleteProduct() {

        System.out.println("----------------------------------");
        Product.showAll();
        System.out.println("----------------------------------");

        System.out.print("Enter Product ID to delete: ");
        int id = input.nextInt();
        input.nextLine();

        Product.showProuct(id);
        if (Admin.deleteProduct(id)) {
            System.out.println("Product has been deleted.");
        } else {
            System.out.println("Something went wrong or Product ID not found.");
        }
    }

    // UPDATE PRODUCT
    static void updateProduct() {

        System.out.println("----------------------------------");
        Product.showAll();
        System.out.println("----------------------------------");

        System.out.print("Enter Product ID to update: ");
        int id = input.nextInt();
        input.nextLine();

        Product.showProuct(id);

        System.out.print("\n1. PRICE\n2. QUANTITY \nEnter Choice:");
        int x = input.nextInt();
        input.nextLine();

        String option;
        switch (x) {
            case 1:
                option = "price";
                break;
            case 2:
                option = "quantity";
                break;
            default:
                System.out.println("Invalid option. Please choose 1 for price or 2 for quantity.");
                return;
        }

        System.out.print("Enter new " + option + ": ");
        int value = input.nextInt();
        input.nextLine();
        Admin.updateProduct(id, value, option);
        Product.showProuct(id);
    }

    public static void main() {
        AdminINF.setVisible();
    }
}
