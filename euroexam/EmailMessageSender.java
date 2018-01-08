package euroexam;

import javax.mail.Session;
import euroexam.spec.MessageSender;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * http://www.tutorialspoint.com/java/java_sending_email.htm
 * http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
 */
public class EmailMessageSender implements MessageSender {

    private final String FROM, TO, SUBJECT;
    private final Session EMAIL_SENDER_SESSION;

    public EmailMessageSender(String smtpHost, int smtpPort, boolean smtpSSL, String smtpUser, String smtpPasswd, String from, String to, String subject) {
        this.EMAIL_SENDER_SESSION = createSession(smtpHost, smtpPort, smtpSSL, smtpUser, smtpPasswd);
        this.FROM = from;
        this.TO = to;
        this.SUBJECT = subject;
    }

    private Session createSession(String smtpHost, int smtpPort, boolean smtpSSL, final String smtpUser, final String smtpPasswd) {
        Properties props = new Properties();
        Session session;
        props.put("mail.smtp.host", smtpHost);
        
        props.put("mail.smtp.auth", smtpUser!=null);
        props.put("mail.smtp.port", smtpPort);
        
        if (smtpSSL){
            
            //Ez a 2db socket akkor kell ha az smtpSSL igaz
        //Ha nem igaz az smtpSSL akkor nem kell létrehozni az Authenticator-t
        props.put("mail.smtp.socketFactory.port", smtpPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUser, smtpPasswd);
            }
        });
        
        }
        else {
           session = Session.getDefaultInstance(props);
        }
        return session;
    }

    @Override
    public void sendMessage(String message) {
        
        try {
            Message uzenet = new MimeMessage(EMAIL_SENDER_SESSION);
            uzenet.setFrom(new InternetAddress(FROM));
            uzenet.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO));
            uzenet.setSubject(SUBJECT);
            uzenet.setText(message);

            Transport.send(uzenet);
            System.out.println("Üzenet elküldve");

        } catch (MessagingException e) {
            System.out.println("E-mail üzenet hiba: " + e.getMessage());
        }
    }
}
