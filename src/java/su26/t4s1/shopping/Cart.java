
package su26.t4s1.shopping;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<String, Product> cart;

    public Cart() {
    }

    public Cart(Map<String, Product> cart) {
        this.cart = cart;
    }

    public Map<String, Product> getCart() {
        return cart;
    }

    public void setCart(Map<String, Product> cart) {
        this.cart = cart;
    }

    public boolean add(Product p) {
        boolean check = false;
        try {
            if (cart == null) {
                cart = new HashMap<>();
            }
            if (cart.containsKey(p.getId())) {
                int currentQuantity = cart.get(p.getId()).getQuantity();
                p.setQuantity(currentQuantity + p.getQuantity());
            }
            cart.put(p.getId(), p);
            check = true;
        } catch (Exception e) {
        }
        return check;
    }

    public boolean edit(String id, int quantity) {
        boolean check = false;
        try {
            if (cart != null) {
                if (cart.containsKey(id)) {
                    Product p = cart.get(id);
                    p.setQuantity(quantity);
                    cart.replace(id, p);
                    check = true;
                }
            }
        } catch (Exception e) {
        }
        return check;
    }

    public boolean remove(String id) {
        boolean check = false;
        try {
            if (cart != null) {
                if (cart.containsKey(id)) {
                    cart.remove(id);
                    check = true;
                }
            }
        } catch (Exception e) {
        }
        return check;
    }
}
