package com.example.emailclient.receiveEmailStrategies;

import com.example.emailclient.EmailMessage;
import com.example.emailclient.EmailMessageBuilder;
import com.example.emailclient.SessionSingleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class PopStrategy implements IReceiveEmailStrategy {
    public Message[] messages1;
    public Folder folder;

    @Override
    public void setMessages1(Message[] messages1) {
        this.messages1 = messages1;
    }

    @Override
    public Message[] getMessages1() {
        return messages1;
    }

    @Override
    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    @Override
    public Folder getFolder() {
        return folder;
    }

    @Override
    public ArrayList<EmailMessage> receiveEmail(final String email, final String password) throws MessagingException, IOException {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "pop3s");
        properties.put("mail.pop3s.host", "pop.mail.ru");
        properties.put("mail.pop3s.user", email);
        properties.put("mail.pop3s.ssl.enable", "true");
        properties.put("mail.pop3s.ssl.trust", "pop.mail.ru");
        properties.put("mail.pop3s.port", "995");

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        SessionSingleton sessionSingleton = SessionSingleton.getInstance(session);
        sessionSingleton.getSession().setDebug(true);
        Store store = sessionSingleton.getSession().getStore("pop3s");
        store.connect("pop.mail.ru", email, password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        Message[] messages;

        messages = inbox.getMessages();

        int messagesCount = inbox.getMessageCount();

        ArrayList<EmailMessage> result = new ArrayList<>();

        for (int i = 0; i < messagesCount; i++) {
            Message message = messages[i];

            EmailMessage emailMessage = new EmailMessageBuilder()
                    .withSender(message.getFrom()[0].toString())
                    .withSubject(message.getSubject())
                    .withBody(message.getContent().toString())
                    .withDate(message.getSentDate())
                    .getResult();

            result.add(emailMessage);
        }

        messages1 = Arrays.copyOf(messages, messages.length);


        //PopStrategy popStrategy = new PopStrategy();
        this.setMessages1(messages1);
        this.setFolder(inbox);

        //inbox.expunge();
        //inbox.close(false);
        return result;

    }
}
