    import java.util.*;
    import java.lang.*;
    import java.io.*;
    import static java.lang.Boolean.FALSE;
    import static java.lang.Boolean.TRUE;
    import java.math.BigDecimal;

    public class Waititem implements Serializable {

        private static final long serialVersionUID = 1L;
        private Client client;
        private Product product;
        protected Integer quantity;
        
       public Waititem(Client client, Product product, Integer quantity) {
            this.product = product;
            this.client = client;
          
            this.quantity = quantity;
        }

        public Product getWaititemName() {
            return product;
        }

        
        public Integer waitQuantity(){
        return quantity;
        }

        public Client waitClient() {
        
        return client;
        }
      

        public String toString() {
            return "Waititem: " + product.getProductName() + ", manufacturername: " + product.getManufacturer() + ", quantity: "+quantity + "Order Placed by: "+ client.getName();
        }

        
        
        public void decQuantity(Integer value){
            quantity-=value;
        }

    }


