package giangnt.utils;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class cartObj implements Serializable {
    private Map<String, Integer> items;

    public cartObj() {
        this.items = new HashMap<>();
    }

    public Map<String, Integer> getItems() {
        return items;
    }
    
    public void addBookToCart(String title, int quantity) {
        int totalQuantity;
        //1. Check if the cart is existed.
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        //2. Check existed book.
        if (this.items.containsKey(title)) {
            totalQuantity = this.items.get(title) + quantity;
        } else {
            totalQuantity = quantity;
        }
        //3. Update cart.
        this.items.put(title, totalQuantity);
    }
    
    public void removeBookFromCart(String title) {
        //1. Check if the cart is existed.
        if (this.items == null) {
            return;
        }
        //2. Check existed book.
        if (this.items.containsKey(title)) {
            this.items.remove(title);
            //3. Check if cart empty
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }
    
    public void put(String title, int quantity) {
        this.items.put(title, quantity);
    }
}
