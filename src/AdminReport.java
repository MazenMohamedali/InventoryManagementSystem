import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AdminReport {
    private int month;
    private int year;
    private int ProductsPurchasedLen;
    private String reportPath;


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

    public String getPath() {
        return reportPath;
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
        
    // will return {"id", "Quantity sold", "name", "price", "current Quantity", "Profit_ratio", "category", "sup_id"}; for Products purchased
    private String[] dataForProductsPurchased() {
        String[] ProductsPurchased = ProductAndQuantityOrderd();

        StringBuilder whereQuery = new StringBuilder("id IN (");
        for(int i=0; i<ProductsPurchasedLen; i++) {
            //  will return [product1_id quantity1, .......] from this then we can do that:
            whereQuery.append(ProductsPurchased[i].charAt(0));
            if(i != ProductsPurchasedLen-1)
                whereQuery.append(", ");
        }
        whereQuery.append(")");

        
        String[] col = {"name", "price", "quantity", "purchasePrecent", "category", "sup_id", };
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
    public boolean generateMonthlyReport() throws IOException {
        String filePath = "../Reports/" + month + "_" + year + ".text"; // Add file extension for clarity
        File file = new File(filePath);
        reportPath = file.getAbsolutePath();

        // Ensure the directory exists
        File reportDir = file.getParentFile();
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        // Create file if it doesn't exist
        if (!file.exists()) {
            file.createNewFile();
        }

        // Use try-with-resources to handle closing
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(String.format("%-60s\n", "---------------------------- Welcome ----------------------------"));
            bw.write(String.format("%-60s\n", "------------------ Report For Date: " + month + "/" + year + " ------------------"));
            bw.write(String.format("%-60s\n", "--------------------------------------------------------"));
            
            bw.write(String.format("%-10s %-15s %-20s %-10s %-15s %-15s %-20s %-15s\n",
                    "ProdID", "QuantitySold", "Name", "Price", "CurrQuantity", "Profit Ratio", "Category", "SupplierID"));
            bw.write(String.format("%-60s\n", "--------------------------------------------------------"));
            
            double totalSold = 0;
            double totalProfit = 0;
            String[] dataForMonth = dataForProductsPurchased();

            for (String dataRow : dataForMonth) {
                String[] row = dataRow.split(", ");
                double quantitySold = 0;
                double profitRatio = 0;
                double price = 0;

                for (int j = 0; j < row.length; j++) {
                    bw.write(String.format("%-15s", row[j]));
                    if (j == 1) 
                        quantitySold = Double.parseDouble(row[j]); 
                    if (j == 3) 
                        price = Double.parseDouble(row[j]); 
                    if (j == 5) 
                        profitRatio = Double.parseDouble(row[j]); 
                }

                totalSold += quantitySold;
                totalProfit += (quantitySold * price * profitRatio) / 100; 
                bw.write("\n");
            }

            // Write totals
            bw.write(String.format("%-60s\n", "--------------------------------------------------------"));
            bw.write(String.format("Total Profit: %s %-20s Total Sold: %s\n", totalProfit, "", totalSold));


            return true; 
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}


