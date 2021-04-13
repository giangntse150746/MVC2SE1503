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
@WebServlet(name = "AddBookToCartServlet", urlPatterns = {"/AddBookToCartServlet"})
public class AddBookToCartServlet extends HttpServlet {
    private  final String SHOPING_CONTROLLER = "loadStoreAction";
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
        
        String url = siteMap.get(SHOPING_CONTROLLER);
        
        String button = request.getParameter("btnAction");
        
        try {
            //1. Customer goes to Cart's place.
              //set true because always need session.
            HttpSession session = request.getSession();
            
            //2. Customer takes a cart.
            cartObj cart = (cartObj) session.getAttribute("CURRENT_CART");
            if (cart == null) {
                cart = new cartObj();
            }//end if cart is not existed.
            cartObj cartHidden = 
                        (cartObj) session.getAttribute("CURRENT_CART_HIDDEN");
            if (cartHidden == null) {
                cartHidden = new cartObj();
            }//end if cart is not existed.
            
            //3. Check if user add one or more than one item:
            if (button.equals("ADD")) {
                //4. Customer selects/chooses product.
                String productId = request.getParameter("txtProductid");
                String txtQuantity = request.getParameter("txtQuantity");
                int quantity = Integer.parseInt(txtQuantity);
                
                TblStoreDAO dao = new TblStoreDAO();
                TblStoreDTO product = dao.getProductData(productId, quantity);
                //4.1. Customer drops product to cart.
                cart.addBookToCart(product.getProductname(), quantity);//needless :>
                cartHidden.addBookToCart(productId, quantity);
                //hiện tại còn ở servlet, đang chưa lưu vào vùng nhớ.
                
                //4.2. thực hiện lưu vào vùng nhớ
                session.setAttribute("CURRENT_CART", cart);
                session.setAttribute("CURRENT_CART_HIDDEN", cartHidden);
                
                System.out.println("[AddBookToCartServlet] [product:"
                            + productId + "] added.");
                url += "?addStatus=true&productAdded=" + product.getProductname();
            }//end if added an element
//            else if (button.equals("Add All Checked Product To Cart")) {
//                String[] selectedList = request.getParameterValues("chkItem");
//                
//                if (selectedList != null) {
//                    //5. Add all selected Item(s).
//                      //before checkout, let customer confirm the last time.
//                      //keep the cart for user if they cancel the process.
//                    Map<String, Integer> items = cart.getItems();
//                    for (String selectedItem : selectedList) {
//                        cart.addBookToCart(selectedItem, 1);
//                    }
//                    session.setAttribute("CURRENT_CART", cart);
//                }//end if has item(s) selected.
//            }
            
            //5. Customer continously goes shopping.
            response.sendRedirect(url);
        } catch (NamingException | SQLException ex) {
            response.sendError(500);
            Logger.getLogger(AddBookToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
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
