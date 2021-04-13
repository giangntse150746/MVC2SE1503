package giangnt.tblStore;

import giangnt.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class TblStoreDAO implements Serializable {
    private List<TblStoreDTO> productList;
    
    public List<TblStoreDTO> showStore()
                throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            //1. Open a connection
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create Query String
                String sql = "Select productid, productname, price, quantity "
                            + "From TblStore";
                
                //3. Add/assign parameter if any.
                stm = con.prepareStatement(sql);
                //4. Execute Query.
                rs = stm.executeQuery();
                //5. Process Result
                while (rs.next()) {
                    String productid = rs.getString("productid");
                    String productname = rs.getNString("productname");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    
                    //Add all properties to an object.
                    TblStoreDTO dto = new TblStoreDTO(
                                productid, productname, price, quantity);
                    //Check if an ArrayList has existed.
                    if (this.productList == null) {
                        this.productList = new ArrayList<>();
                    }
                    //Add product into Store
                    this.productList.add(dto);
                }
                return productList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
        return null;
    }
    public List<TblStoreDTO> showCart()
                throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            //1. Open a connection
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create Query String
                String sql = "Select productid, productname, price, quantity "
                            + "From TblStore";
                
                //3. Add/assign parameter if any.
                stm = con.prepareStatement(sql);
                //4. Execute Query.
                rs = stm.executeQuery();
                //5. Process Result
                while (rs.next()) {
                    String productid = rs.getString("productid");
                    String productname = rs.getNString("productname");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    
                    //Add all properties to an object.
                    TblStoreDTO dto = new TblStoreDTO(
                                productid, productname, price, quantity);
                    //Check if an ArrayList has existed.
                    if (this.productList == null) {
                        this.productList = new ArrayList<>();
                    }
                    //Add product into Store
                    this.productList.add(dto);
                }
                return productList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
        return null;
    }
    public TblStoreDTO getProductData(String productid, int quantity) 
                throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            //1. Open a connection
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create Query String
                String sql = "Select productname, price "
                            + "From TblStore "
                            + "Where productid = ?";
                
                //3. Add/assign parameter if any.
                stm = con.prepareStatement(sql);
                stm.setString(1, productid);
                //4. Execute Query.
                rs = stm.executeQuery();
                //5. Process Result
                if (rs.next()) {
                    String productname = rs.getNString("productname");
                    float price = rs.getFloat("price");
                    
                    //Add all properties to an object.
                    TblStoreDTO dto = new TblStoreDTO(
                                productid, productname, price, quantity);
                return dto;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
        return null;
    }
}
