package com.example.emailclient.sendEmailStrategies;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailStrategy implements ISendEmailStrategy {
    @Override
    public void sendEmail(final String email, final String password, String to, String body, String subject) throws MessagingException {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.user", email);
        properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        session.setDebug(true);

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(email));
        mimeMessage.addRecipient(Message.RecipientType.TO,
                new InternetAddress(to));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(to);

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", email, password);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();

        System.out.println("Sent message successfully....");
    }
}
