
package su26.t4s1.shopping;


public class CartItemDTO {
    private String pID;
    private String pName;
    private String uID;
    private int quantity;
    private double price; 

    public CartItemDTO( String pID, String pName,String uID, int quantity, double price) {
        this.pID = pID;
        this.uID = uID;
        this.pName = pName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String name) {
        this.pName = name;
    }

}
