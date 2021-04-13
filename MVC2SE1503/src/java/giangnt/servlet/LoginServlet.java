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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import giangnt.tblDemo.TblDemoDAO;
import giangnt.tblDemo.TblDemoDTO;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 *
 * @author Admin
 */
public class LoginServlet extends HttpServlet {
    private final String INVALID_LOGIN_PAGE = "loginPageJSP";
    private final String SEARCH_PAGE = "searchPageJSP";
    private final String LOAD_STORE_CONTROLLER = "loadStoreAction";
    
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
        
        String url = siteMap.get(INVALID_LOGIN_PAGE);
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String remember = request.getParameter("chkCookie");
        
        TblDemoDTO result;
        TblDemoCreateError errors = new TblDemoCreateError();

        try {
            //1. Initialize DAO.
            TblDemoDAO dao = new TblDemoDAO();
            result = dao.checkLogin(username, password);
            //2. Process to response the Request
            if (result != null) {
                if (remember != null) {
                    Cookie cookie = new Cookie(username, password);
                    cookie.setMaxAge(60*3);
                    response.addCookie(cookie);
                    System.out.println("[LoginServlet] cookie(s) saved.");
                }
                //3. set attribute current user info.
                HttpSession session = request.getSession();
                session.setAttribute("CURRENT_USER", result);
                
                //4. Check if user is admin or customer
                if (result.isRole()) {
                    url = siteMap.get(SEARCH_PAGE);
                } else {
                    url = siteMap.get(LOAD_STORE_CONTROLLER);
                }
            }//end if result is returned fullname.
            else {
                //if there is an invalid username/password, printout it.
                errors.setLoginInfoNotMatch("Username or Password is incorrect.");
                request.setAttribute("CREATE_ERROR", errors);
            }
        } catch(SQLException | NamingException ex) {
            log(ex.getLocalizedMessage());
            response.sendError(500);
        }
        finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
//            response.sendRedirect(url);
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
