package com.example.emailclient.receiveEmailStrategies;

import com.example.emailclient.EmailMessage;
import com.example.emailclient.EmailMessageBuilder;
import com.example.emailclient.SessionSingleton;
import com.sun.mail.imap.IMAPFolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class ImapStrategy implements IReceiveEmailStrategy {

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
    public ArrayList<EmailMessage> receiveEmail(final String email, final String password, final String mail, final String folderName) throws MessagingException, IOException {
        Properties properties = new Properties();
        if (mail.equals("gmail")){
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", "imap.gmail.com");
        properties.put("mail.imaps.user", email);
        properties.put("mail.imaps.ssl.enable", "true");
        properties.put("mail.imaps.ssl.trust", "imap.gmail.com");
        properties.put("mail.imaps.port", "993");}
        else if (mail.equals("mail")){
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.host", "imap.mail.ru");
            properties.put("mail.imaps.user", email);
            properties.put("mail.imaps.ssl.enable", "true");
            properties.put("mail.imaps.ssl.trust", "imap.mail.ru");
            properties.put("mail.imaps.port", "993");
        }

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        SessionSingleton sessionSingleton = SessionSingleton.getInstance(session);
        sessionSingleton.getSession().setDebug(true);
        Message[] messages = null;
        int messagesCount = 0;
        IMAPFolder imapInbox = null;
        Folder inbox = null;
        if(mail.equals("gmail")){ Store store = sessionSingleton.getSession().getStore("imaps");
        store.connect("imap.gmail.com", email, password);
            imapInbox = (IMAPFolder) store.getFolder(folderName);
            imapInbox.open(Folder.READ_WRITE);

            messages = imapInbox.getMessages();
            messagesCount = imapInbox.getMessageCount();

            Folder[] f = store.getDefaultFolder().list();
            for(Folder fd:f)
                System.out.println(">> "+fd.getName());

        } else if (mail.equals("mail")){
            Store store = sessionSingleton.getSession().getStore("imaps");

            store.connect("imap.mail.ru", email, password);
            inbox = store.getFolder(folderName);
            inbox.open(Folder.READ_WRITE);
            messages = inbox.getMessages();
            messagesCount = inbox.getMessageCount();

            Folder[] f = store.getDefaultFolder().list();
            for(Folder fd:f)
                System.out.println(">> "+fd.getName());
        }



        ArrayList<EmailMessage> result = new ArrayList<>();

        for (int i = 0; i < messagesCount; i++) {
            Message message = messages[i];

            EmailMessage emailMessage = new EmailMessageBuilder()
                    .withSender(message.getFrom()[0].toString())
                    .withSubject(message.getSubject())
                    //.withBody(message.getContent().toString())
                    .withDate(message.getSentDate())
                    .withFlag(message.isSet(Flags.Flag.SEEN))
                    //.withAttachment(message.isMimeType("multipart/*"))
                    .getResult();

            result.add(emailMessage);
        }

        messages1 = Arrays.copyOf(messages, messages.length);


        //PopStrategy popStrategy = new PopStrategy();
        this.setMessages1(messages1);
        if (mail.equals("gmail")){
        this.setFolder(imapInbox);} else if (mail.equals("mail")) {this.setFolder(inbox);}

        return result;

    }
}
