
package su26.t4s1.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import su26.t4s1.user.UserDAO;
import su26.t4s1.user.UserDTO;


@WebServlet(name = "UpdateController", urlPatterns = {"/UpdateController"})
public class UpdateController extends HttpServlet {

    private static final String ERROR = "admin.jsp";
    private static final String SUCCESS = "admin.jsp";

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
            String uID = request.getParameter("uID");
            String roleID = request.getParameter("roleID");
            String name = request.getParameter("name");
            UserDTO dto = new UserDTO();
            dto.setName(name);
            dto.setuID(uID);
            dto.setRoleID(roleID);
            log(name);
            UserDAO dao = new UserDAO();
            boolean check = dao.update(dto);
            if (check) {
                HttpSession session = request.getSession();
                UserDTO login = (UserDTO) session.getAttribute("LOGIN_USER");
                if (login.getuID().equals(uID)) {
                    login.setName(name);
                    login.setRoleID(roleID);
                    session.setAttribute("LOGIN_USER", login);
                };
                
                List<UserDTO> dto1 = dao.getListUser("");
                request.setAttribute("LIST_USER", dto1);
                request.setAttribute("search", name);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error occur at UpdateController " + e.toString());
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
