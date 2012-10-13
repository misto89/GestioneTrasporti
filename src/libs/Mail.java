/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import connection.MailManager;
import controllo.FrontController;
import eccezioni.EccezioneCredenzialiErrate;
import java.io.File;
import javax.mail.*;
import java.util.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Mail {
    private static String host;  //tuo smtp
    private static String port;
    private static String from; //tuo indirizzo email
    private static String user;
    private static String pass; //Your password
    
    static {
        MailManager params = FrontController.getMailParams();
        host = params.getHost();
        port = params.getPort();
        from = params.getFrom();
        user = params.getUser();
        pass = params.getPass();
    }
    
    public static boolean send(List<String> to, String subject, String text) throws AddressException, MessagingException {
        return send(to, subject, text, null);
    }

    public static boolean send(List<String> to, String subject, String text, File file) throws AddressException, MessagingException {
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        boolean sessionDebug = true;
        Properties props = System.getProperties();
        props.put("mail.host", host);
        props.put("mail.transport.protocol.", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.", "true");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        Session mailSession = Session.getDefaultInstance(props, null);
        mailSession.setDebug(sessionDebug);
        Message msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] address = new InternetAddress[to.size()];
        
        int cont = 0;
        for (String ad : to) {
            address[cont++] = new InternetAddress(ad);
        }
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(subject);
        
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(text);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        if (file != null) {
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(file.getName());
            multipart.addBodyPart(messageBodyPart);
        }
        
        msg.setContent(multipart);
        //msg.setContent(messageText, "text/html"); // use setText if you want to send text
        Transport transport = mailSession.getTransport("smtp");
        try {
            transport.connect(host, user, pass);
        } catch (AuthenticationFailedException e) {
            throw new EccezioneCredenzialiErrate("Mail non inviata! Username e/o password errate");
        }
        
        boolean wasEmailSent;
        try {
            transport.sendMessage(msg, msg.getAllRecipients());
            wasEmailSent = true; // assume it was sent
            transport.close();
            
        } catch (Exception err) {
            wasEmailSent = false; // assume it's a fail
        }
        
        return wasEmailSent;
    }

}

