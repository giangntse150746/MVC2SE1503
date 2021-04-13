/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Admin
 */
public class ContextListener implements ServletContextListener {
    private final String SITE_MAP_TXT = "/CONF-INF/mapping.txt";
    
    private static Map<String, String> getSiteMap(String urlPatterns)
                throws IOException {
        File file = new File(urlPatterns);
        FileReader f = null;
        BufferedReader br = null;
        
        Map<String, String> siteMap;
        try {
            if (file.exists()) {
                System.out.println("[ContextListener] file can read.");
                f = new FileReader(urlPatterns);
                br = new BufferedReader(f);
                siteMap = new HashMap<>();
                
                while(br.ready()) {
                    String line = br.readLine();
                    String[] entry = line.split("=", 2);
                    
                    //put(name, value) into Map
                    siteMap.put(entry[0].trim(), entry[1].trim());
                }
                return siteMap;
            }
        } finally {
            if (br != null)
                br.close();
            if (f != null)
                f.close();
        }
        
        return null;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //1. assign a hashmap to read siteMapping file.
        Map<String, String> siteMap = null;
        ServletContext application = sce.getServletContext();
        String filepath = null;
        
        try {
            if (application != null) {
                filepath = application.getRealPath(SITE_MAP_TXT);
                siteMap = getSiteMap(filepath);
            }
            System.out.println("filepath: " + filepath);
            if (siteMap != null) {
                System.out.println("[ContextListener] mapping file read successful.");
            } else {
                siteMap = new HashMap<>();
                //Page
                siteMap.put("loginPage", "login.html");
                siteMap.put("loginPageJSP", "login.jsp");
                siteMap.put("registerPage", "register.html");
                siteMap.put("registerPageJSP", "register.jsp");
                siteMap.put("registerSuccessPage", "registerSuccessful.html");
                siteMap.put("errorPage", "error.html");
                siteMap.put("searchPageJSP", "search.jsp");
                siteMap.put("invalidPage", "invalid.html");
                siteMap.put("shopPage", "bookStore.html");
                siteMap.put("viewCartPageJSP", "viewCart.jsp");
                //Servlet
                siteMap.put("", "StartUpServlet");
                siteMap.put("loginAction", "LoginServlet");
                siteMap.put("registerAction", "RegisterServlet");
                siteMap.put("searchAction", "SearchServlet");
                siteMap.put("logoutAction", "LogoutServlet");
                siteMap.put("deleteAction", "DeleteAccountServlet");
                siteMap.put("updateAction", "UpdateAccountServlet");
                siteMap.put("addToCartAction", "AddBookToCartServlet");
                
                System.out.println("[ContextListener] mapping file read failed."
                                 + " Changing to built-in siteMap...");
            }
            ServletContext context = sce.getServletContext();
            context.setAttribute("SITE_MAP", siteMap);
        } catch (IOException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
