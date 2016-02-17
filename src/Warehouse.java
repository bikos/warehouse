
import java.util.*;
import java.io.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.math.BigDecimal;

public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int PRODUCT_NOT_FOUND = 1;
    public static final int PRODUCT_NOT_ISSUED = 2;
    public static final int PRODUCT_HAS_HOLD = 3;
    public static final int PRODUCT_ISSUED = 4;
    public static final int HOLD_PLACED = 5;
    public static final int NO_HOLD_FOUND = 6;
    public static final int OPERATION_COMPLETED = 7;
    public static final int OPERATION_FAILED = 8;
    public static final int NO_SUCH_CLIENT = 9;
    private ProductList ProductList;
    private ClientList clientList;
    private InvoiceList invoiceList;
    private WaititemList waitList;
    private static Warehouse warehouse;
    private List transactions = new LinkedList(); //product transaction
    protected Double sumProduct = 0.0;

    private Warehouse() {
        ProductList = ProductList.instance();
        clientList = ClientList.instance();
        invoiceList = InvoiceList.instance();
        waitList = WaititemList.instance();
    }

    public static Warehouse instance() {
        if (warehouse == null) {
            ClientIdServer.instance(); // instantiate all singletons
            return (warehouse = new Warehouse());
        } else {
            return warehouse;
        }
    }

    public Product addProduct(String name, String manufacturer, String id, Double price, Integer quantity) {
        Product product = new Product(name, manufacturer, id, price, quantity);
        if (ProductList.insertProduct(product)) {
            return (product);
        }
        return null;
    }

//    public Invoice addInvoice(Product product, Client client, Integer quantity, Date date) {
//        Invoice invoice = new Invoice(product, client, quantity, date);
//        if (invoiceList.insertInvoices(invoice)) {
//
//            return (invoice);
//        } else {
//            return null;
//        }
//    }
    public Client addClient(String name, String address, String phone) {
        Client client = new Client(name, address, phone);
        if (clientList.insertClient(client)) {
            return (client);
        }
        return null;
    }

    public Iterator getProducts() {
        return ProductList.getProducts();
    }

    public Iterator getClients() {
        return clientList.getClients();
    }

    public String showInvoices(String clientID) {
        //
        String cat = "";
        InvoiceList testInvoice = InvoiceList.instance();
        Iterator testIterator = testInvoice.getInvoices();
        while (testIterator.hasNext()) {
            Invoice tempTest = (Invoice) testIterator.next();
            tempTest = testInvoice.checkInvoice(clientID);
            if (tempTest != null) {
                cat = cat + (tempTest.getInvoiceString() + "\n");
            }
        }
        return cat;
    }
    
    public Iterator getWaitlist() {
    return waitList.getWaititemList();
    }

//    public String showWailist() {
//
//        String cat = "";
//        WaititemList waitlist = WaititemList.instance();
//        Iterator testIterator = waitlist.getWaititemList();
//        while (testIterator.hasNext()) {
//            Waititem tempTest = (Waititem) testIterator.next();
//            cat = cat + (tempTest.toString() + "\n");
//        }
//        return cat;
//
//    }

    public static Warehouse retrieve() {
        try {
            FileInputStream file = new FileInputStream("WarehouseData");
            ObjectInputStream input = new ObjectInputStream(file);
            input.readObject();
            ClientIdServer.retrieve(input);
            return warehouse;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }

    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("WarehouseData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(warehouse);
            output.writeObject(ClientIdServer.instance());
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(warehouse);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            input.defaultReadObject();
            if (warehouse == null) {
                warehouse = (Warehouse) input.readObject();
            } else {
                input.readObject();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return ProductList + "\n" + clientList;
    }

    public Client testClient(String clientID) {
        ClientList clientlist = ClientList.instance();
        Client client = clientlist.checkClient(clientID);
        try {
            if (client != null) {
                //System.out.println("user " + client.getName() + " exists");
                return client;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Product testProduct(String productID) {
        ProductList catlist = ProductList.instance();
        Product product = catlist.checkProduct(productID);
        try {
            if (product != null) {
                //System.out.println("user " + client.getName() + " exists");
                return product;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean insertTransaction(Client client, Product product, Date date) {
        try {
            transactions.add(product.getProductName() + " sold on " + date.toString() + " to Client: " + client.getName() + " at price " + product.getPrice());
            return TRUE;
        } catch (Exception e) {
            return FALSE;
        }
    }

//    public void decQuantity(Product product) {
//        product.quantity -= 1;
//    }
    public List getTransactions() {
        return transactions;
    }

//    public List getInvoice() {
//        return invoice;
//    }
    public Double getSumProduct() {
        return sumProduct;
    }

//    public void flushInvoice() {
//        invoice.clear();
//    }
    public Boolean issueProduct(Client client, String productID, Integer quantity) {

        boolean status = FALSE;
        try {

            Product product = ProductList.checkProduct(productID);
            if (product != null) {
                //System.out.println("Product does not exist");
                if (quantity > product.quantity) {
                    // send to wait list
                    System.out.println("Less quantity, qunatity sent to wait list");
                    Waititem wtItem = new Waititem(client, product, quantity);
                    WaititemList wtItemList = WaititemList.instance();
                    if (wtItemList.insertWaititem(wtItem)) {
                        System.out.println("Order added to wait List, Please wait till shipment arrives for processing");
                    }

                } else {
                    Date date = new Date();
                    // issue product and write to the invoice
                    Invoice tempInvoice = new Invoice(product, client, quantity, date);
                    InvoiceList invoiceList = InvoiceList.instance();

                    if (invoiceList.insertInvoices(tempInvoice)) {
                        System.out.println(tempInvoice.getInvoiceString());
                        product.decQuantity(quantity);
                        
                        //adding to the balance of the client
                        
                        client.addBalance(tempInvoice.sum);
                      
                        
                        warehouse.insertTransaction(client, product, date);

                    }
                    //decrease the product quantity after sales

                }

                status = TRUE;
            } else {
                status = FALSE;
            }

        } catch (Exception e) {
            System.out.println(e);
            status = FALSE;
        }

        return status;
    }

    public Boolean receiveShipment(String manufacturerName, String productName, Integer quantity) {

        Boolean status = FALSE;
        try {

            ProductList productList = ProductList.instance();
            Iterator pdtList = productList.getProducts();
            while (pdtList.hasNext()) {
                Product pdtCheck = (Product) pdtList.next();
                if (manufacturerName.equals(pdtCheck.getManufacturer()) && productName.equals(pdtCheck.getProductName())) {
                    pdtCheck.quantity += quantity;
                   // System.out.println("new quantity " + pdtCheck.quantity);
                    status = TRUE;
                }

            }

            WaititemList waitItemList = WaititemList.instance();
            Iterator wtList = waitItemList.getWaititemList();
            while (wtList.hasNext()) {
                Waititem waitCheck = (Waititem) wtList.next();
                if (productName.equals(waitCheck.getWaititemName().getProductName()) && waitCheck.waitQuantity() < waitCheck.getWaititemName().quantity) {
                    System.out.println("Pending Order Found, Processing > > > \n");
                    Date date = new Date();
                    // issue product and write to the invoice
                    Invoice tempInvoice = new Invoice(waitCheck.getWaititemName(), waitCheck.waitClient(), waitCheck.waitQuantity(), date);
                    InvoiceList invoiceList = InvoiceList.instance();

                    if (invoiceList.insertInvoices(tempInvoice)) {
                        System.out.println("FILLING OUT THE ORDER FOR CLIENT: " + waitCheck.waitClient().getName() +"\n");
                        System.out.println(tempInvoice.getInvoiceString()+"\n");
                        System.out.println("* * * * * * * * * * *");
                        waitCheck.getWaititemName().decQuantity(waitCheck.waitQuantity());
                        waitCheck.waitClient().addBalance(tempInvoice.sum);
                        warehouse.insertTransaction(waitCheck.waitClient(), waitCheck.getWaititemName(), date);
                        wtList.remove();

                    }

                }
            }

        } catch (Exception e) {
            System.out.println(e);
            status = FALSE;
        }

        return status;
    }
    
   
    public Client makePayment(String clientID){
    
    Client client = warehouse.testClient(clientID);
    return client;
        
    }
    

}
