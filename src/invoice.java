import java.io.*;
import java.time.LocalDate;

public class invoice extends order {
    int invoiceID;                     
     int[] amount;            
     int[] pricePerproduct;       
     double totalPrice;       
     String clientAddress;    
     LocalDate orderDate;      
     LocalDate arrivalDate;  
public void invoice(Order order){
    this.amount = order.getAmount();
    this.invoiceID = order.getOrderID();
    this.pricePerproduct = order.getPricePerID();
    this.totalPrice = order.getTotalPrice();
    this.clientAddress = order.getClientAddress();
    this.orderDate = order.getOrderDate();
    this.arrivalDate = order.getArrivalDate();
}

public void printinvoice(invoice invoice) {
   String filename = "invoice"+this.invoiceID;
PrintWriter writer = null;
try {
    File file = new File(filename);
    writer = new PrintWriter(file);
    writer.println(this.invoiceID);
    for(int i = 0;i < this.amount.length;i++){
     writer.println(this.amount[i] +" "+this.pricePerproduct[i]);
    }
    writer.println(this.totalPrice);
     writer.println(this.clientAddress);
     writer.println(this.orderDate);
     writer.println(this.arrivalDate);
    
} catch (IOException e) {
    System.err.println("An error occurred while writing to the file: " + e.getMessage());
}finally {
    if (writer != null) {
        writer.close();
    }


}



}
}
