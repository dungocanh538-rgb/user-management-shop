
package su26.t4s1.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import su26.t4s1.shopping.Cart;
import su26.t4s1.shopping.Product;
import su26.t4s1.shopping.ProductDAO;


@WebServlet(name = "ViewProductController", urlPatterns = {"/ViewProductController"})
public class ViewProductController extends HttpServlet {

    private final String SUCCESS = "shopping.jsp";
    private final String ERROR = "shopping.jsp";

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
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            if (session != null) {
                ProductDAO dao = new ProductDAO();

                // Read page param (default page = 1)
                String pageStr = request.getParameter("page");
                int page = 1;
                if (pageStr != null && !pageStr.isEmpty()) {
                    page = Integer.parseInt(pageStr);
                }

                List<Product> list = dao.getListPagination(page);
                int totalProducts = dao.getTotalProductCount();
                int totalPages = (int) Math.ceil((double) totalProducts / 5);

                request.setAttribute("LIST_PRODUCT", list);
                request.setAttribute("CURRENT_PAGE", page);
                request.setAttribute("TOTAL_PAGES", totalPages);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at ViewProductController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
