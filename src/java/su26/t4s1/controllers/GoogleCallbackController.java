
package su26.t4s1.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import su26.t4s1.user.UserDAO;
import su26.t4s1.user.UserDTO;
import su26.t4s1.utils.GoogleConfig;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


@WebServlet(name = "GoogleCallbackController", urlPatterns = {"/GoogleCallbackController"})
public class GoogleCallbackController extends HttpServlet {

    private final String SUCCESS = "login.jsp";
    private final String ERROR = "login.jsp";

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
        String code = request.getParameter("code");
        String url = "login.jsp";

        try {
            // --- Step 1: Exchange code for access token ---
            String tokenBody = "code=" + URLEncoder.encode(code, "UTF-8")
                    + "&client_id=" + URLEncoder.encode(GoogleConfig.CLIENT_ID, "UTF-8")
                    + "&client_secret=" + URLEncoder.encode(GoogleConfig.CLIENT_SECRET, "UTF-8")
                    + "&redirect_uri=" + URLEncoder.encode(GoogleConfig.REDIRECT_URI, "UTF-8")
                    + "&grant_type=authorization_code";

            URL tokenUrl = new URL("https://oauth2.googleapis.com/token");
            HttpURLConnection tokenConn = (HttpURLConnection) tokenUrl.openConnection();
            tokenConn.setRequestMethod("POST");
            tokenConn.setDoOutput(true);
            tokenConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = tokenConn.getOutputStream();
            os.write(tokenBody.getBytes("UTF-8"));
            os.flush();
            os.close();

            BufferedReader tokenReader = new BufferedReader(
                    new InputStreamReader(tokenConn.getInputStream()));
            String tokenResponse = tokenReader.lines().reduce("", (a, b) -> a + b);
            tokenReader.close();

            Gson gson = new Gson();
            String accessToken = gson.fromJson(tokenResponse, JsonObject.class)
                    .get("access_token").getAsString();

            // --- Step 2: Get user info with access token ---
            URL userUrl = new URL("https://www.googleapis.com/oauth2/v2/userinfo");
            HttpURLConnection userConn = (HttpURLConnection) userUrl.openConnection();
            userConn.setRequestProperty("Authorization", "Bearer " + accessToken);

            BufferedReader userReader = new BufferedReader(
                    new InputStreamReader(userConn.getInputStream()));
            String userResponse = userReader.lines().reduce("", (a, b) -> a + b);
            userReader.close();

            JsonObject userJson = gson.fromJson(userResponse, JsonObject.class);
            String email = userJson.get("email").getAsString();
            String name = userJson.has("name") ? userJson.get("name").getAsString() : email;

            // --- Step 3: Login or create user ---
            UserDAO userDao = new UserDAO();
            HttpSession session = request.getSession();
            UserDTO loginUser;

            if (userDao.checkDuplicateEmail(email)) {
                loginUser = userDao.get(email);
            } else {
                userDao.insert(new UserDTO(email, "google_oauth", "US", name, true, email));
                loginUser = userDao.get(email);
            }

            session.setAttribute("LOGIN_USER", loginUser);
            url = "AD".equals(loginUser.getRoleID()) ? "admin.jsp" : "user.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "Google login failed: " + e.getMessage());
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
