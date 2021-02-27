/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblDemo;

import giangnt.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class TblDemoDAO implements Serializable {
    public boolean checkLogin(String username, String password)
        throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            //1. Connect DB.
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create SQL String.
                String sql = "Select username "
                            + "From TblDemo "
                            + "Where username = ? And password = ?";
                //3. Create Statement and assign Parameter(s) if any
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                
                //4. Execute Query.
                rs = stm.executeQuery();
                
                //5. Process resultSet.
                if (rs.next()) {
                    return true;
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
        return false;
    }
    
    private List<TblDemoDTO> accountList;

    public List<TblDemoDTO> getAccountList() {
        return accountList;
    }
    
    public void searchLastname(String searchValue)
        throws SQLException, NamingException {
        //0. Connect Database.
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            //1. Connect DB.
            con = DBHelper.makeConnection();
            //2. Create SQL String.
            String sql = "Select username, password, lastname, isAdmin "
                        + "From TblDemo "
                        + "Where lastname Like ?";
            //3.1. Create Statement & assign value to parameter.
            stm = con.prepareStatement(sql);
            //3.2. Find String has searchValue.
            stm.setString(1, "%" + searchValue + "%");
            
            //4. Execute Query.
            rs = stm.executeQuery();
            
            //5. Process Result.
            while (rs.next()) {
                //5.2. Get Data from Database.
                String username = rs.getString("username");
                String password = rs.getString("password");
                String fullname = rs.getNString("lastname");
                boolean role = rs.getBoolean("isAdmin");
                
                //5.3. Add data into an object.
                TblDemoDTO dto = new TblDemoDTO(
                        username, password, fullname, role);
                
                //5.4.
                if (this.accountList == null) {
                    this.accountList = new ArrayList<>();
                }//end if listAccount is not existed.
                //
                this.accountList.add(dto);
            }
        }
        finally {
            if (stm != null)
                stm.close();
            if (con != null)
                con.close();
        }
    }
    
    public boolean deleteAccount(String username)
                throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            //1. Connect DB.
            con = DBHelper.makeConnection();
            //2. Create SQL String.
            String sql = "Delete From TblDemo "
                        + "Where username = ?";
            //3.1. Create Statement & assign value to parameter.
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            //4. Execute Query.
            int row = stm.executeUpdate();
            //5. Process Result.
            if (row > 0) {
                return true;
            }
        }
        finally {
            if (stm != null)
                stm.close();
            if (con != null)
                con.close();
        }
        return false;
    }
    
    public boolean updateAccount(String username, String password, String role)
                throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean isAdmin;
        try {
            //1. Connect DB.
            con = DBHelper.makeConnection();
            //2. Create SQL String.
            String sql = "Update TblDemo "
                        + "Set password = ?, isAdmin = ? "
                        + "Where username = ?";
            //3. Create Statement & assign value to parameter.
            stm = con.prepareStatement(sql);
            stm.setString(1, password);
            if (role != null)
                isAdmin = role.equals("ON");
            else
                isAdmin = false;
            stm.setBoolean(2, isAdmin);
            stm.setString(3, username);
            //4. Execute Query.
            int row = stm.executeUpdate();
            //5. Process Result.
            if (row > 0) {
                return true;
            }
        }
        finally {
            if (stm != null)
                stm.close();
            if (con != null)
                con.close();
        }
        return false;
    }

}
