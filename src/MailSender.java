import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailSender {

    private String host;
    private String port;

    // Constructor that takes only host and port
    public MailSender(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public void sendEmail(String subject, String content, String toEmail) {
        // Set up the mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);
        props.put("mail.smtp.starttls.enable", "true");

        // Create a session with no authenticator
        Session session = Session.getInstance(props, null);

        try {
            // Create a new email
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("stimpla@vhe.is"));  // replace with your email
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(content);

            // Send the email
            Transport.send(message);
        } catch (MessagingException ex) {
            // Handle any errors
            ex.printStackTrace();
        }
    }
}
