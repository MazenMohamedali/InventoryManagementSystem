import java.time.LocalDate;

public class Order {
    private static int idCounter = 1; 
    private final int orderID;       
    private int[] prodID;            
    private int[] amount;            
    private int[] pricePerID;       
    private double totalPrice;       
    private String clientAddress;    
    private LocalDate orderDate;      
    private LocalDate arrivalDate;    

    
    public Order(int[] prodID, int[] amount, int[] pricePerID, String clientAddress, LocalDate orderDate, LocalDate arrivalDate) {
        this.orderID = idCounter++;  
        this.prodID = prodID;
        this.amount = amount;
        this.pricePerID = pricePerID;
        this.clientAddress = clientAddress;
        this.orderDate = orderDate;
        this.arrivalDate = arrivalDate;
        calculateTotalPrice();
    }

    
    public int getOrderID() {
        return orderID;
    }

    
    public int[] getProdID() {
        return prodID;
    }

    public void setProdID(int[] prodID) {
        this.prodID = prodID;
        calculateTotalPrice();
    }

    
    public int[] getAmount() {
        return amount;
    }

    public void setAmount(int[] amount) {
        this.amount = amount;
        calculateTotalPrice();
    }

    
    public int[] getPricePerID() {
        return pricePerID;
    }

    public void setPricePerID(int[] pricePerID) {
        this.pricePerID = pricePerID;
        calculateTotalPrice();
    }

    
    public double getTotalPrice() {
        return totalPrice;
    }

    
    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    
    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    
    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

   
    private void calculateTotalPrice() {
        totalPrice = 0;
        if (prodID != null && amount != null && pricePerID != null) {
            for (int i = 0; i < prodID.length; i++) {
                totalPrice += amount[i] * pricePerID[i];
            }
        }
    }

    //Display order details
    public void displayOrder() {
        System.out.println("Order Details:");
        System.out.println("Order ID: " + orderID);
        System.out.println("Client Address: " + clientAddress);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Expected Arrival Date: " + arrivalDate);
        System.out.println("Products:");
        for (int i = 0; i < prodID.length; i++) {
            System.out.println("Product ID: " + prodID[i] + ", Amount: " + amount[i] + ", Price per ID: " + pricePerID[i]);
        }
        System.out.println("Total Price: " + totalPrice);
    }
}
