package com.example.emailclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class MailActivity extends Activity {

//    public TextView subjectText;
//    public TextView fromText;
//    public TextView bodyText;
    public LinearLayout emailLayout;
    public Button newEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

//        subjectText = (TextView) findViewById(R.id.subjectText);
//        fromText = (TextView) findViewById(R.id.fromText);
//        bodyText = (TextView) findViewById(R.id.bodyText);
        emailLayout = (LinearLayout) findViewById(R.id.emailLayout);
        newEmailButton = (Button) findViewById(R.id.newEmailButton);

        //Message[] messages;
        //String email;
        Intent intent = getIntent();
        final String email = getIntent().getExtras().getString("email");
        final String password = intent.getStringExtra("password");

        AsyncRequest asyncRequest = new AsyncRequest();
        asyncRequest.execute(email, password);

        newEmailButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MailActivity.this, NewMailActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });

    }

    public ArrayList<EmailMessage> receiveEmail(final String email, final String password) throws MessagingException, IOException {

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "pop3s");
        //properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //properties.put("mail.pop3.socketFactory.port", "995");
       // properties.put("mail.pop3.auth", "true");
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
        session.setDebug(true);
        Store store = session.getStore("pop3s");
        store.connect("pop.mail.ru", email, password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] messages;
        messages = inbox.getMessages();

        int messagesCount = inbox.getMessageCount();

        //Map<String, String> messageMap;
        //ArrayList<Map<String, String>> result = new ArrayList<>();

        ArrayList<EmailMessage> result = new ArrayList<>();

        for (int i = 0; i < messagesCount; i++){
            Message message = messages[i];
            result.add(new EmailMessage((message.getFrom()[0]).toString(), message.getSubject(), (message.getContent()).toString()));
        }
//        for (int i = 0; i < messagesCount; i++){
//            subjects[i] = (messages.get(i).getSubject());
//            froms[i] = ((messages.get(i).getFrom()[0]).toString());
//            contents[i] = ((messages.get(i).getContent()).toString());
//        }

        return result;
        //return messages[0];
    }

    public class AsyncRequest extends AsyncTask<String, Void, ArrayList<EmailMessage>>{
        @Override
        protected ArrayList<EmailMessage> doInBackground(String... params) {
            ArrayList<EmailMessage> message;
            try {
                message = receiveEmail(params[0], params[1]);
                return message;
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(final ArrayList<EmailMessage> message){
            int viewCount = message.size();
            TextView[] textViews = new TextView[viewCount];
            Context context = getApplicationContext();
            for (int i = 0; i < viewCount; i++){
                TextView tmp = new TextView(context);
                final int finalI = i;
                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Intent intent = new Intent(MailActivity.this, MessageActivity.class);
                            intent.putExtra("sender", message.get(finalI).getSender());
                            intent.putExtra("subject", message.get(finalI).getSubject());
                            intent.putExtra("body", message.get(finalI).getBody());
                            startActivity(intent);
                    }
                });
                tmp.setText(message.get(i).printEmail());
                emailLayout.addView(tmp);
                textViews[i] = tmp;
            }




//            subjectText.setText(message.get(0));
//            fromText.setText((message.get(1)));
//            bodyText.setText((message.get(2)));
        }

    }
}
