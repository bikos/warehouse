
import java.util.*;
import java.io.*;

public class Invoice implements Serializable {

    Client client;
    Product product;
    Integer quantity;
    Date date;
    Double sum;
    String invoiceStr;

    public Invoice(Product product, Client client, Integer quantity, Date date) {
        this.client = client;
        this.product = product;
        this.date = date;
        this.quantity = quantity;
        this.sum = product.getPrice() * quantity;

        invoiceStr = ("Customer: " + client.getName() + " Product: " + product.getProductName() + " price: " + product.getPrice() + " quantity: " + quantity + " grand_total " + sum + " on Date:"+date.toString());

    }

    Invoice(Product product, Client client, Integer quantity, Date date, Integer duummy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getProductId() {
        return product.getId();
    }

    public String getclienttId() {
        return client.getId();
    }

    public String getInvoiceString() {
        return invoiceStr;
    }

}
