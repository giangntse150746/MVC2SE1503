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
@WebServlet(name = "RemoveItemServlet", urlPatterns = {"/RemoveItemServlet"})
public class RemoveItemServlet extends HttpServlet {
    private final String VIEWCART_CONTROLLER = "viewCartAction";
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
        
        String url = siteMap.get(VIEWCART_CONTROLLER);
        
        try {
            //1. Customer goes to his/her cart's place.
              //Chọn 'false' để kiểm tra vùng nhớ còn hay hết (có thể mất do timeout).
            HttpSession session = request.getSession(false);
            if (session != null) {
                //2. Customer takes his/her cart.
                cartObj cart = (cartObj) session.getAttribute("CURRENT_CART_HIDDEN");
                if (cart != null) {
                    //3. Customer get items in cart.
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
                        //4. Customer selects/chooses item(s) to remove.
                        String[] selectedItems = request.getParameterValues("chkItem");
                          //check if list of request is null or has parameters.
                        if (selectedItems != null) {
                            System.out.println("[RemoveItemServlet] removing item(s)...");
                            
                            for (String productid : selectedItems) {
                                System.out.println(productid);
                                cart.removeBookFromCart(productid);
                            }//end for title
                              //update on Scope (server-side).
                            session.setAttribute("CURRENT_CART_HIDDEN", cart);
                        }//end if at least 1 item is selected.
                    }//end if cart is not empty.
                }//end if cart is existed.
            }//end if session has existed.
            //6. Re-call View Cart function.
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
