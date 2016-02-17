import java.util.*;
import java.lang.*;
import java.io.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.math.BigDecimal;

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    private String productName;
    private String manufacturer;
    private String id;
    private Double price;
    protected Integer quantity;
    protected Client productClient;
    protected Date soldDate;

    public Product(String name, String manufacturer, String id, Double price, Integer quantity) {
        this.productName = name;
        this.manufacturer = manufacturer;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Double getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return "Product: " + productName + ", manufacturer: " + manufacturer + ", id: " + id + ", price: " + price +", quantity: "+quantity;
    }

//    public Client Borrower(Client Client, Date date) {
//        productClient = Client;
//        soldDate = date;
//        return productClient;
//    }
//    
    public void decQuantity(Integer value){
        quantity= quantity - value;
        System.out.println("quantity remaining "+ quantity + "\n");
    }

}