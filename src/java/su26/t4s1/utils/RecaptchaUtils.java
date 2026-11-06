 package su26.t4s1.utils;

 import com.google.gson.JsonObject;
 import com.google.gson.JsonParser;
 import java.io.BufferedReader;
 import java.io.InputStreamReader;
 import java.io.OutputStream;
 import java.net.HttpURLConnection;
 import java.net.URL;
 import java.net.URLEncoder;

 public class RecaptchaUtils {

     private static final String SECRET_KEY = "YOUR_RECAPTCHA_SECRET_KEY";
     private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

     public static boolean verify(String recaptchaResponse) {
         if (recaptchaResponse == null || recaptchaResponse.isEmpty()) {
             return false;
         }
         try {
             URL url = new URL(VERIFY_URL);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setRequestMethod("POST");
             conn.setDoOutput(true);

             String params = "secret=" + URLEncoder.encode(SECRET_KEY, "UTF-8")
                     + "&response=" + URLEncoder.encode(recaptchaResponse, "UTF-8");

             OutputStream os = conn.getOutputStream();
             os.write(params.getBytes());
             os.flush();
             os.close();

             BufferedReader br = new BufferedReader(
                     new InputStreamReader(conn.getInputStream()));
             StringBuilder fullResponse = new StringBuilder();
             String line;
             while ((line = br.readLine()) != null) {
                 fullResponse.append(line);
             }
             br.close();

             JsonObject json = JsonParser.parseString(fullResponse.toString()).getAsJsonObject();
             return json.get("success").getAsBoolean();

         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
 }