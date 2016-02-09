package src;

import java.util.*;
import java.lang.*;
import java.io.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  private String title;
  private String author;
  private String id;
  protected Client borrower;
  protected Date dueDate;
  


  public Product(String title, String author, String id) {
    this.title = title;
    this.author = author;
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }
  public String getTitle() {
    return title;
  }
  public String getId() {
    return id;
  }

  public String toString() {
      return "title " + title + " author " + author + " id " + id;
  }
  
  public Client Borrower(Client Client, Date date){
  borrower = Client;
  dueDate = date;
  return borrower;
  }
  

}

