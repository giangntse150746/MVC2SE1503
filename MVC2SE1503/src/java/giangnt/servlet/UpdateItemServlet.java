/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.servlet;

import giangnt.utils.cartObj;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
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
@WebServlet(name = "UpdateItemServlet", urlPatterns = {"/UpdateItemServlet"})
public class UpdateItemServlet extends HttpServlet {
    private final String VIEW_CART_CONTROLLER = "viewCartAction";
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
        String url = siteMap.get(VIEW_CART_CONTROLLER);
        
        try {
            HttpSession session = request.getSession(false);
            
            String productId = request.getParameter("txtProductid");
            String txtQuantity = request.getParameter("txtQuantity");
            int quantity = Integer.parseInt(txtQuantity);
            System.out.println(quantity);

            //1. Customer goes and takes the cart.
            cartObj cartHidden = 
                        (cartObj) session.getAttribute("CURRENT_CART_HIDDEN");

            //2.Customer check if this item is in his/her cart.
            System.out.println("[UpdateItemServlet] updating item(s)...");
            cartHidden.put(productId, quantity);
            session.setAttribute("CURRENT_CART_HIDDEN", cartHidden);
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
