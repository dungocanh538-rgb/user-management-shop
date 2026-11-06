package su26.t4s1.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String FROM_EMAIL = "your_email@gmail.com";
    private static final String FROM_PASSWORD = "YOUR_GMAIL_APP_PASSWORD";

    public static void sendOrderConfirmation(String toEmail, String userName,
            String orderId, double total)
            throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject("Order Confirmation — Order #" + orderId);

        String html = "<h1>Thank you for your purchase, " + userName + "!</h1>"
                + "<p>Your order with ID <strong>#" + orderId + "</strong> has been placed.</p>"
                + "<p><strong>Total:</strong> $" + String.format("%.2f", total) + "</p>"
                + "<p>We'll notify you when your items ship.</p>";

        message.setContent(html, "text/html; charset=UTF-8");
        System.out.println("Sending email to: " + toEmail);
        Transport.send(message);
        System.out.println("Email sent successfully!");
    }
}
