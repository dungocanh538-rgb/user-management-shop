
package su26.t4s1.utils;

public class GoogleConfig {

    public static final String CLIENT_ID
            = "YOUR_GOOGLE_CLIENT_ID";
    public static final String CLIENT_SECRET
            = "YOUR_GOOGLE_CLIENT_SECRET";
    public static final String REDIRECT_URI
            = "http://localhost:8080/UserManagement_T4S1_JSP/GoogleCallbackController";

    public static final String AUTH_URL
            = "https://accounts.google.com/o/oauth2/auth"
            + "?client_id=" + CLIENT_ID
            + "&redirect_uri=" + REDIRECT_URI
            + "&response_type=code"
            + "&scope=email%20profile";
}
