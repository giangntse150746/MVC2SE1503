/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.servlet;


import giangnt.tblDemo.TblDemoDTO;
import giangnt.tblStore.TblStoreDAO;
import giangnt.tblStore.TblStoreDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoadStoreServlet", urlPatterns = {"/LoadStoreServlet"})
public class LoadStoreServlet extends HttpServlet {
    private final String SHOP_PAGE = "shopPageJSP";
    private final String SEARCH_PAGE = "searchPageJSP";
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
        HttpSession session = request.getSession();
        //get Servlet Context to use ContextListener
        ServletContext sc = request.getServletContext();
        //get the siteMap
        Map<String, String> siteMap = 
                    (Map<String, String>) sc.getAttribute("SITE_MAP");
        
        String url = siteMap.get(SHOP_PAGE);
        
        List<TblStoreDTO> result;
        String loginStatus = request.getParameter("txtLoginStatus");
        
        
        try {
            //1. Initialize DAO
            TblStoreDAO dao = new TblStoreDAO();
            result = dao.showStore();
            
            if (loginStatus != null) {
                if (loginStatus.equals("noUser")) {
                    session.removeAttribute("CURRENT_USER");
                }
            }
            if (session.getAttribute("CURRENT_USER") == null) {
                TblDemoDTO guest = new TblDemoDTO("guest", "guest", "@GUEST", false);
                session.setAttribute("CURRENT_USER", guest);
            }
            
            //2.Process result
            request.setAttribute("PRODUCT_LIST", result);
            System.out.println("[LoadStoreServlet] store loaded.");
            
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            response.sendError(500);
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
