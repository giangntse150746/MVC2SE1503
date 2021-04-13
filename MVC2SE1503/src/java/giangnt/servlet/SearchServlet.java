/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import giangnt.tblDemo.TblDemoDAO;
import giangnt.tblDemo.TblDemoDTO;
import java.util.Map;
import javax.servlet.ServletContext;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SearchLastnameServlet", urlPatterns = {"/SearchLastnameServlet"})
public class SearchServlet extends HttpServlet {
//    private final String SEARCH_PAGE = "search.html";
    private final String SEARCH_PAGE = "searchPageJSP";
    private final String SEARCH_PAGE_RESULT = "searchPageJSP";

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
        
        String searchValue = request.getParameter("txtSearchValue");
        String url = siteMap.get(SEARCH_PAGE);
        
        try {
            //Check if search value is not an empty string
            if (searchValue.trim().length() > 0) {
                //1. Initialize DAO
                TblDemoDAO dao = new TblDemoDAO();
                dao.searchLastname(searchValue);
                
                List<TblDemoDTO> result = dao.getAccountList();
                
                //Send to SEARCH_RESULT_PAGE
                request.setAttribute("SEARCH_RESULT", result);
                System.out.println("[SearchServlet] search result received.");
                url = siteMap.get(SEARCH_PAGE_RESULT);
                request.setAttribute("lastSearchValue", searchValue);
            }
        } catch (SQLException | NamingException ex) {
            response.sendError(502); //many Connection
        }
        finally {
            //Qua trang khác nên phải giữ request object để trả về.
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
