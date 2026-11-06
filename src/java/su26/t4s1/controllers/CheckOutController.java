package su26.t4s1.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import su26.t4s1.shopping.Cart;
import su26.t4s1.shopping.CartDAO;
import su26.t4s1.shopping.CartItemDTO;
import su26.t4s1.shopping.OrderDAO;
import su26.t4s1.shopping.OrderDetailDAO;
import su26.t4s1.shopping.Product;
import su26.t4s1.shopping.ProductDAO;
import su26.t4s1.user.UserDTO;
import su26.t4s1.utils.DBUtils;
import su26.t4s1.utils.EmailUtils;

@WebServlet(name = "CheckoutController", urlPatterns = {"/CheckoutController"})
public class CheckOutController extends HttpServlet {

    private final String SUCCESS = "ViewCartController";
    private final String ERROR = "ViewCartController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            double total = Double.parseDouble(request.getParameter("total"));
            if (session != null) {
                UserDTO login = (UserDTO) session.getAttribute("LOGIN_USER");
                if (login != null) {
                    CartDAO dao = new CartDAO();
                    ProductDAO pDao = new ProductDAO();
                    List<CartItemDTO> cart = dao.get(login.getuID());
                    if (!cart.isEmpty()) {

                        String errors = "";

                        for (CartItemDTO item : cart) {
                            if (!pDao.checkQuantity(item.getpID(), item.getQuantity())) {
                                errors += "Not enough quantity for " + item.getPName() + "\n";
                            }
                        }

                        if (!errors.isEmpty()) {

                            request.setAttribute("ERROR", errors);

                        } else {

                            OrderDAO oDao = new OrderDAO();
                            OrderDetailDAO otDao = new OrderDetailDAO();

                            String orderID = oDao.insert(login.getuID(), total);

                            if (orderID != null) {

                                for (CartItemDTO item : cart) {
                                    otDao.insert(orderID, item.getpID(), item.getQuantity());
                                    pDao.reduceQuantity(item.getpID(), item.getQuantity());
                                }

                                dao.clear(login.getuID());

                                request.setAttribute("MESSAGE",
                                        "Order placed successfully! Thank you for your purchase.");

                               
                                try {
                                    System.out.println("Email = " + login.getEmail());
                                    EmailUtils.sendOrderConfirmation(
                                            login.getEmail(),
                                            login.getName(),
                                            orderID,
                                            total
                                    );

                                } catch (Exception e) {
                                    e.printStackTrace(); 
                                }

                            } else {

                                
                                request.setAttribute("ERROR",
                                        "Failed to create the order.");

                            }

                        }

                    } else {

                        request.setAttribute("ERROR",
                                "Your cart is empty.");

                    }
                }
            }
        } catch (Exception e) {
            log("Error at CheckoutController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Checkout Controller";
    }
}
