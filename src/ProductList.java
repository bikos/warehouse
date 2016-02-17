import java.util.*;
import java.lang.*;
import java.io.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
public class ProductList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List products = new LinkedList();
  private static ProductList ProductList;
  private ProductList() {
  }
  public static ProductList instance() {
    if (ProductList == null) {
      return (ProductList = new ProductList());
    } else {
      return ProductList;
    }
  }
  
  public boolean insertProduct(Product product) {
    products.add(product);
    return true;
  }
  public Iterator getProducts() {
    return products.iterator();
  }
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(ProductList);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (ProductList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (ProductList == null) {
          ProductList = (ProductList) input.readObject();
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
    return products.toString();
  }
  
  public Product checkProduct(String productID) {
        Product tempProduct = null;
        ProductList productList = ProductList.instance();
        Iterator catList = productList.getProducts();
        while (catList.hasNext()) {
            Product productCheck = (Product) catList.next();
            if (productID.equals(productCheck.getId())) {
                tempProduct = productCheck;
                break;
            }
        }
        return tempProduct;

    }
  
  
  
}
