/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Admin
 */
public class DBHelper implements Serializable {
    public static Connection makeConnection() 
        throws NamingException/*ClassNotFoundException*/, SQLException {
        
        Context currentContext = new InitialContext();
        Context tomcatContext = (Context)currentContext.lookup("java:comp/env");
        //add Datasource of java.sql
        DataSource ds = (DataSource)tomcatContext.lookup("DS007");
        Connection con = ds.getConnection();
        
        return con;
        /*
        //1. Load Driver(s). --> add Driver(s) into project.
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //2. Create Connection String to determine Container address.
        String url = "jdbc:sqlserver://localhost:1433;databaseName=DataDemoDAO";
        //3. Open Connection.
        Connection con = DriverManager.getConnection(url, "sa", "123456");
        //Return Connection.
        return con;
        */
    }
}
