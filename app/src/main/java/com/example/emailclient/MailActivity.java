package com.example.emailclient;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class MailActivity extends Activity {

    public TextView subjectText;
    public TextView fromText;
    public TextView bodyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        subjectText = findViewById(R.id.subjectText);
        fromText = findViewById(R.id.fromText);
        bodyText = findViewById(R.id.bodyText);

        LoginActivity loginActivity = new LoginActivity();
        String email = loginActivity.getEmail();
        String password = loginActivity.getPassword();

        //Message[] messages;
        Message message;
        
        try {
            message = receiveEmail(email, password);
            subjectText.setText(message.getSubject());
            fromText.setText((message.getFrom()[0]).toString());
            bodyText.setText((message.getContent()).toString());
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Message receiveEmail(String email, String password) throws MessagingException {


        Properties properties = new Properties();
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.pop3.socketFactory.port", "995");
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.host", "pop.mail.ru");
        properties.put("mail.pop3.user", email);

        Session session = Session.getDefaultInstance(properties);
        Store store = session.getStore("pop3");
        store.connect("pop.mail.ru", email, password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        return messages[0];
    }
}
