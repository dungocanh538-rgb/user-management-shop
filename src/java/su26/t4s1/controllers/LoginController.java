
package su26.t4s1.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import su26.t4s1.user.UserDAO;
import su26.t4s1.user.UserDTO;
import su26.t4s1.utils.RecaptchaUtils;


@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String AD = "AD";
    private static final String US = "US";
    private static final String USER_PAGE = "user.jsp";
    private static final String ADMIN_PAGE = "admin.jsp";
    private static final String INCORRECT_MSG = "Incorrect UID or Password";
    private static final String NOT_SUPPROT_MSG = "Your role is not supported yet!!";

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
            String pwd = request.getParameter("password");

            String recaptchaResponse = request.getParameter("g-recaptcha-response");
            boolean isHuman = RecaptchaUtils.verify(recaptchaResponse);
            if (!isHuman) {
                request.setAttribute("ERROR", "Please verify you are not a robot");
                url = ERROR;
            } else {
                UserDAO dao = new UserDAO();
                UserDTO dto = dao.checkLogin(uID, pwd);
                if (dto != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("LOGIN_USER", dto);
                    String roleID = dto.getRoleID();
                    if (AD.equals(roleID)) {
                        url = ADMIN_PAGE;
                    } else if (US.equals(roleID)) {
                        url = USER_PAGE;
                    } else {
                        request.setAttribute("ERROR", NOT_SUPPROT_MSG);
                    }
                } else {
                    request.setAttribute("ERROR", INCORRECT_MSG);
                }
            }
            
        } catch (Exception e) {
            log("Error at LoginController" + e.toString());
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
