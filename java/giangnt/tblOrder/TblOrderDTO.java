/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.tblOrder;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class TblOrderDTO implements Serializable {
    private int orderid;
    private String username;
    private int quantity;

    public TblOrderDTO() {
        
    }
    
    public TblOrderDTO(int orderid, String username, int quantity) {
        this.orderid = orderid;
        this.username = username;
        this.quantity = quantity;
    }

    /**
     * @return the orderid
     */
    public int getOrderid() {
        return orderid;
    }

    /**
     * @param orderid the orderid to set
     */
    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
