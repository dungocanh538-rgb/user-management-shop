
package su26.t4s1.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import su26.t4s1.user.UserDAO;
import su26.t4s1.user.UserDTO;
import su26.t4s1.user.UserError;


@WebServlet(name = "CreateController", urlPatterns = {"/CreateController"})
public class CreateController extends HttpServlet {

    private static final String ERROR = "createUser.jsp";
    private static final String SUCCESS = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        UserError userError = new UserError();
        UserDAO dao = new UserDAO();
        try {
            boolean check = true;
            String uID = request.getParameter("uID");
            String name = request.getParameter("name");
            String roleID = request.getParameter("roleID");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm");

            if (uID.length() < 2 || uID.length() > 10) {
                userError.setuID("uID mus be in [2,10]");
                check = false;
            }

            boolean checkDuplicate = dao.checkDuplicate(uID);
            if (checkDuplicate) {
                userError.setuID("Duplicate uID....!");
                check = false;
            }

            if (name.length() < 5 || name.length() > 20) {
                userError.setName("Namet must be in [5,20]");
                check = false;
            }
            if (!password.equals(confirm)) {
                userError.setConfirm("Hai password ko giong nhau !!!!");
                check = false;
            }
            if (check) {
                UserDTO user = new UserDTO(uID, password, roleID, name, true);
                boolean checkInsert = dao.insert(user);
                if (checkInsert) {
                    url = SUCCESS;
                } else {
                    userError.setError("Unknow Error!!!!");
                    request.setAttribute("USER_ERROR", userError);
                }
            } else {
                request.setAttribute("USER_ERROR", userError);
            }

        } catch (Exception e) {
            log("Error at CreateController: " + e.toString());
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
