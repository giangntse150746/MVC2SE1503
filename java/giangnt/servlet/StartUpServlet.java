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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import giangnt.tblDemo.TblDemoDAO;
import giangnt.tblDemo.TblDemoDTO;
import java.util.Map;
import javax.servlet.ServletContext;

/**
 *
 * @author Admin
 */
@WebServlet(name = "StartUpServlet", urlPatterns = {"/StartUpServlet"})
public class StartUpServlet extends HttpServlet {
    private final String LOGIN_PAGE = "loginPage";
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
        
        String url = siteMap.get(LOGIN_PAGE);
        
        try {
            System.out.println("[StartUpServlet] starting up.");
            //1. Check Cookies are existed.
            Cookie[] cookies = request.getCookies();
            HttpSession session = request.getSession();
            
            if (cookies != null) {
                System.out.println("[StartUpServlet] cookie found.");
                //2. Get username and password.
                Cookie lastCookie = cookies[cookies.length-1];
                String username = lastCookie.getName();
                String password = lastCookie.getValue();
                
                //3. Check username and password is correct.
                TblDemoDAO dao = new TblDemoDAO();
                TblDemoDTO result = dao.checkLogin(username, password);
                
                if (result != null) {
                    //4. Skip LOGIN_PAGE if the username and password is matched.
                    session = request.getSession();
                    session.setAttribute("CURRENT_USER", result);
                    System.out.println("[StartUpServlet] user session found.");
                    //4. Check if user is admin or customer
                    if (result.isRole()) {
                        url = siteMap.get(SEARCH_PAGE);
                    } else {
                        url = siteMap.get(LOAD_STORE_CONTROLLER);
                    }
                    
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                } else {
                    session.invalidate();
                    System.out.println("[StartUpServlet] user session found"
                                     + " but WRONG_INFO_RELEASE.");
                    response.sendRedirect(url);
                }
            }//cookies have existed.
            else {
                session.invalidate();
                System.out.println("[StartUpServlet] NO cookie found.");
                response.sendRedirect(url);
            }
        } catch(SQLException | NamingException ex) {
            ex.printStackTrace();
        } finally {
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
