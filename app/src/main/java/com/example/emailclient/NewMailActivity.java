package com.example.emailclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NewMailActivity extends AppCompatActivity {

    public Button sendButton;
    public EditText messageText;
    public EditText recipientText;
    public EditText subjectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mail);

        sendButton = (Button) findViewById(R.id.sendButton);
        messageText = (EditText) findViewById(R.id.messageText);
        recipientText = (EditText) findViewById(R.id.recipientText);
        subjectText = (EditText) findViewById(R.id.subjectText);

        Intent intent = getIntent();
        final String email = getIntent().getExtras().getString("email");
        final String password = intent.getStringExtra("password");



        //System.out.println("To:"+to);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String to = recipientText.getText().toString();
                final String message = messageText.getText().toString();
                final String subject = subjectText.getText().toString();
                AsyncEmail asyncEmail = new AsyncEmail();
                asyncEmail.execute(email, password, to, message, subject);
            }
        });

    }

    public void sendEmail(final String email, final String password, String to, String message, String subject) throws MessagingException {

        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mail.ru");
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
        mimeMessage.setText(message);

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.mail.ru", email, password);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();

        System.out.println("Sent message successfully....");
    }

    public class AsyncEmail extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                sendEmail(params[0], params[1], params[2], params[3], params[4]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
