/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.servlet;

import giangnt.tblStore.TblStoreDAO;
import giangnt.tblStore.TblStoreDTO;
import giangnt.utils.cartObj;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "ViewOrderedServlet", urlPatterns = {"/ViewOrderedServlet"})
public class ViewOrderedServlet extends HttpServlet {
    private final String VIEW_ORDER_PAGE = "orderPage";
    
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
        
        String url = siteMap.get(VIEW_ORDER_PAGE);
        List<TblStoreDTO> productList;
        try {
            //ViewCartServlet To Generate Data from Map to List
            
            //1. Customer goes to Cart's place.
              //set true because always need session.
            HttpSession session = request.getSession();
            
            //2. Customer takes a cart.
            cartObj cart = (cartObj) request.getAttribute("ORDER_CART");
            
            productList = (ArrayList<TblStoreDTO>)request.getAttribute("SHOW_CART");
            if (productList == null) {
                productList = new ArrayList<>();
            }
            if (cart != null) {
                TblStoreDTO product;
                TblStoreDAO dao = new TblStoreDAO();
                
                //3.Iterator goes through all item(s) that added.
                for (String item : cart.getItems().keySet()) {
                    //key is productid.
                    String productId = item;
                    //value is quantity
                    int quantity = cart.getItems().get(item);
                    product = dao.getProductData(productId, quantity);
                    if (product != null) {
                        productList.add(product);
                    }
                }
                request.setAttribute("SHOW_CART", productList);
            } else {
                System.out.println("[ViewCartServlet]Bủh Bủh Dảk Dảk Lmao Lmao");
            }
        } catch (NamingException | SQLException ex) {
            response.sendError(500);
            Logger.getLogger(ViewCartServlet.class.getName()).log(Level.SEVERE, null, ex);
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
