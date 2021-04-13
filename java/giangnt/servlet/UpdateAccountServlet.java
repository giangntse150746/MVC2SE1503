/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.servlet;

import giangnt.tblDemo.TblDemoCreateError;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import giangnt.tblDemo.TblDemoDAO;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateAccountServlet", urlPatterns = {"/UpdateAccountServlet"})
public class UpdateAccountServlet extends HttpServlet {
    private final String ERROR_PAGE = "errorPage";
    private final String SEARCH_CONTROLLER = "searchAction";
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
        
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String role = request.getParameter("chkAdmin");
        String searchValue = request.getParameter("lastSearchValue");
        
        String url = siteMap.get(ERROR_PAGE);
        boolean foundErr = false;
        
        try {
                        /***BẮT LỖI CÓ VẤN ĐỀ***/
            //0. Create a list errors message to catch and show errors.
            TblDemoCreateError errors = new TblDemoCreateError();
            //1. Check if Update is Valid.
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                foundErr = true;
                errors.setPasswordLengthErr("Password must in range of 6-30 characters.");
            }
            if (foundErr) {
                request.setAttribute("CREATE_ERROR", errors);
                System.out.println("[UpdateAccoutnServlet] error(s) created.");
            } else {
                //2. Call DAO
                TblDemoDAO dao = new TblDemoDAO();
                boolean result = dao.updateAccount(username, password, role);
                if (result) {
                    System.out.println("[UpdateAccoutnServlet] (username=" 
                            + username + ";password=" + password + ") updated.");
                }
            }
            //3. Re-Sreach (refresh the search page)
            url = siteMap.get(SEARCH_CONTROLLER);
            
            url += "?txtSearchValue=" + searchValue;
            request.setAttribute("UPDATE_INFO", username);
        } catch (NamingException ex) {
            response.sendError(500);
            String errMsg = ex.getMessage();
            log("UpdateAccountServlet_Naming: " + errMsg);
        } catch (SQLException ex) {
            response.sendError(500);
            String errMsg = ex.getMessage();
            log("UpdateAccountServlet_SQL: " + errMsg);
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
