/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.tblUserCart;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class TblUserCartDTO implements Serializable {
    private String username;
    private String productid;
    private int quantity;

    public TblUserCartDTO(String username, String productid, int quantity) {
        this.username = username;
        this.productid = productid;
        this.quantity = quantity;
    }
    
    public TblUserCartDTO() {
        
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
     * @return the productid
     */
    public String getProductid() {
        return productid;
    }

    /**
     * @param productid the productid to set
     */
    public void setProductid(String productid) {
        this.productid = productid;
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
