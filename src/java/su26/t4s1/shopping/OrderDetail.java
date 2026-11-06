
package su26.t4s1.shopping;


public class OrderDetail {
    private String oID;
    private String pID;
    // set in the buying time
    // if the new price is assigned after the order is shipped,
    // this price is not updated.
    private double price; 
    private int quantity;
}
