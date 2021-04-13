/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.servlet;

import giangnt.tblOrder.TblStoreCreateError;
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
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {
    private final String VIEW_ORDER_CONTROLLER = "viewOrderedAction";
    private final String VIEW_CART_CONTROLLER = "ViewCartServlet";
    
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
        
        String custName = request.getParameter("txtCustomerName");
        String address = request.getParameter("txtAddress");
        
        try {
            //1. Customer goes to his/her cart.
            HttpSession session = request.getSession(false);
            if (session != null) {
                //2. Customer takes his/her cart.
                cartObj cart = (cartObj) session.getAttribute("CURRENT_CART_HIDDEN");
                if (cart != null) {
                    System.out.println("[CheckOutServlet] Checking out...");
                    //3. Customer check if item(s) existed.
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
                        //4. Customer selects/chooses what item to check-out.
                        String[] selectedItems = request.getParameterValues("chkItem");
                        if (selectedItems != null) {
                            System.out.println("item found");
                            //5. Check out and Print selected Item(s).
                              //before checkout, let customer confirm the last time.
                              //keep the cart for user if they cancel the process.
                            cartObj checked = new cartObj();
                            
                            for (String entry : selectedItems) {
                                String productid = entry;
                                int quantity = items.get(entry);
                                checked.getItems().put(productid, quantity);
                                //6. Remove ordered item(s) from Cart.
                                cart.removeBookFromCart(entry);
                            }
                            request.setAttribute("ORDER_CART", checked);
                            url = siteMap.get(VIEW_ORDER_CONTROLLER);
                        }//end if has item(s) selected.
                    }//end if has item(s).
                }//end if cart existed.
            }//end if session existed.
        } finally {
            //7. Customer goes to the ordered page if at least 1 selected.
            //must use RequestDispatcher if has name and adress to forward.
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
//CHECK OUT SERVLET WERE MADE TO LET CUSTOMER CONSIDERS WITH HIS/HER IDIOT CHOICES.