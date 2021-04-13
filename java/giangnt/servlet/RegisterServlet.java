/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import giangnt.tblDemo.TblDemoCreateError;
import giangnt.tblDemo.TblDemoDAO;
import java.util.Map;
import javax.servlet.ServletContext;

/**
 *
 * @author Admin
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
    private final String INVALID_REGISTER_PAGE = "registerPageJSP";
    private final String SUCCESS_REGISTER_PAGE = "registerSuccessPage";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //get Servlet Context to use ContextListener
        ServletContext sc = request.getServletContext();
        //get the siteMap
        Map<String, String> siteMap = 
                    (Map<String, String>) sc.getAttribute("SITE_MAP");
        
        String url = siteMap.get(INVALID_REGISTER_PAGE);
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");
        
        TblDemoCreateError errors = new TblDemoCreateError();
        boolean foundErr = false;
        
        try {
            //1. Check if there is a new Register (check valid user's input)
                //Create an object in the same package with DAO to catch and store input
            if (username.trim().length() < 6 || username.trim().length() > 30) {
                foundErr = true;
                errors.setUsernameLengthErr("Username requires input between 6 and 30 characters.");
            }
            if (password.trim().length() < 6 || password.trim().length() > 20) {
                foundErr = true;
                errors.setPasswordLengthErr("Password requires input between 6 and 20 characters.");
            }
            if (!confirm.trim().equals(password.trim())) {
                foundErr = true;
                errors.setConfirmNotMatch("Confirm requires matching with Password.");
            }
            if (fullname.trim().length() < 2 || fullname.trim().length() > 50) {
                foundErr = true;
                errors.setFullnameLengthErr("Fullname requires input between 2 and 50 characters.");
            }
            //if any error found.
            if (foundErr) {
                request.setAttribute("CREATE_ERROR", errors);
            } else {
                //2. call DAO if no error found.
                TblDemoDAO dao = new TblDemoDAO();
                boolean result = dao.newRegister(username, password, fullname, false);
                if (result) {
                    url = siteMap.get(SUCCESS_REGISTER_PAGE);
                }
            }
        } catch (SQLException ex) {
            String errMsg = ex.getMessage();
            log("\nRegisterServlet_SQL: " + errMsg);
            //if primary key is duplicated, SQL will throws error here.
            if (errMsg.contains("duplicate")) {
                errors.setUsernameIsExisted(username + " is existed.");
                request.setAttribute("CREATE_ERROR", errors);
            }
        } catch (NamingException ex) {
            String errMsg = ex.getMessage();
            log("RegisterServlet_Naming: " + errMsg);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
