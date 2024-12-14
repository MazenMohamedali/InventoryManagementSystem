import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.classfile.BufWriter;
import java.nio.file.FileAlreadyExistsException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminReport {
    private int month;
    private int year;
    private int ProductsPurchasedLen;


    public AdminReport(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) throws IllegalArgumentException {
        if (month > 12 || month < 1)
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // make fun convert array of String to Array of int
    // First, we need in spacific month what is orders 
    // ,then we return order id to use for bring quantity and product id
    private int[] ordersIDInSpacificMonth() {
        String monthFormated = String.format("%02d", month);

        String sqlQuary = "arrival_date LIKE '%-" + monthFormated + "-" + year + "'";
        String[] columns = { "id" };
        String[] oldRes = connectDB.generlizeSelect(columns, "orders", sqlQuary);

        int len = oldRes.length;
        ProductsPurchasedLen = len;
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            res[i] = Integer.parseInt(oldRes[i]);
        }
        return res;
    }



    // Second, for this orders we will return each product and their quentity for specific month
    // will return [product1_id, quantity1, .......]
    private String[] ProductAndQuantityOrderd() {
        int[] orders = ordersIDInSpacificMonth();
        int len = orders.length;


        if(len == 0)
            return new String[0];

        StringBuilder whereQuery = new StringBuilder("prod_id IN (");
        for(int i=0; i<orders.length; i++) {
            whereQuery.append(orders[i]);

            if(i != len-1)
                whereQuery.append(", ");
        }
        whereQuery.append(")");
        
        String[] col = {"prod_id", "quantity"};
        return connectDB.generlizeSelect(col, "order_prod", whereQuery.toString());
    }



    // Third, for this product we need to know what is price , Profit ratio, name
        
    // will return {"id", "Quantity sold", "name", "price", "current Quantity", "category", "sup_id"}; for Products purchased
    public String[] dataForProductsPurchased() {
        String[] ProductsPurchased = ProductAndQuantityOrderd();

        StringBuilder whereQuery = new StringBuilder("id IN (");
        for(int i=0; i<ProductsPurchasedLen; i++) {
            //  will return [product1_id quantity1, .......] from this then we can do that:
            whereQuery.append(ProductsPurchased[i].charAt(0));
            if(i != ProductsPurchasedLen-1)
                whereQuery.append(", ");

            

        }
        whereQuery.append(")");

        
        String[] col = {"name", "price", "quantity", "purchasePrecent", "category", "sup_id"};
        String[] result = connectDB.generlizeSelect(col, "product", whereQuery.toString());
        
        
        // add id and  for quantity each product in result array
        int lenRes = result.length;
        for(int i=0; i<lenRes; i++) {
            StringBuilder finalRes = new StringBuilder(ProductsPurchased[i] + ", ");
            finalRes.append(result[i]);
            result[i] = finalRes.toString();
        }
        
        return result;
    }


    // Finaly, we have: products are purches for spacific month, quantity, price for each product, and Profit ratio
    // then, we can make Profit report for each month
    // ,put each Profit in file with name is month and year for profit
    /**
     * @return
     */
    public boolean GenerateMonthlyRepor() throws IOException {
        String filePath = "../Reports/" + month + "_" + year;
        File file = new File(filePath);
        try {
            if(!file.exists()) {
                file.createNewFile(filePath);
            }
        }
        FileWriter report = new FileWriter(file, false);
        BufferedWriter bw = new BufferedWriter (report);
        bw.write("----------------------------Wlcome----------------------------\n");
        bw.write("----------------------------This Report For Date "+ month + "/" + year +" ----------------------------\n");
        bw.write("--------------------------------------------------------\n");
    // will return {"id", "Quantity sold", "name", "price", "current Quantity", "category", "sup_id"}; for Products purchased
        
        bw.write("Product ID\t\tQuantity sold\t\tName\t\tPrice\t\tcurrent Quantity\t\tCategory\t\tsup_id\n");





        

        
        
        // file up to date , file is created
         return true;
        

    }
    public static void main(String[] args) {
        AdminReport test = new AdminReport(12, 2024);
        String[] tesData = test.dataForProductsPurchased();
        for(String S : tesData) {
            System.out.println(S);
        }
    }
}


