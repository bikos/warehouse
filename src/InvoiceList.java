import java.util.*;
import java.lang.*;
import java.io.*;
public class InvoiceList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List invoices = new LinkedList();
  private static InvoiceList invoiceList;
  private InvoiceList() {
  }
  public static InvoiceList instance() {
    if (invoiceList == null) {
      return (invoiceList = new InvoiceList());
    } else {
      return invoiceList;
    }
  }
  
  public boolean insertInvoices(Invoice invoice) {
    invoices.add(invoice);
    return true;
  }
  public Iterator getInvoices() {
    return invoices.iterator();
  }
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(invoiceList);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (invoiceList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (invoiceList == null) {
          invoiceList = (InvoiceList) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch(IOException ioe) {
      System.out.println("in ProductList readObject \n" + ioe);
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
  public String toString() {
    return invoiceList.toString();
  }
  
  
  // publish invoices of the clients
  
  public Invoice checkInvoice(String clientID) {
        Invoice tempInvoice = null;
        InvoiceList tempInvList = invoiceList.instance();
        Iterator catList = tempInvList.getInvoices();
        while (catList.hasNext()) {
            Invoice invCheck = (Invoice) catList.next();
            if (clientID.equals(invCheck.getclienttId())) {
                tempInvoice = invCheck;
                break;
            }
        }
        return tempInvoice;

    }
  
  
  
}
