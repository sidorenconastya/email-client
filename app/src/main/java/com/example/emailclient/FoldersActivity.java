package com.example.emailclient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emailclient.receiveEmailStrategies.ImapStrategy;
import com.sun.mail.imap.IMAPFolder;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class FoldersActivity extends AppCompatActivity {

    public LinearLayout ll;
    public Activity activity = this;
    public String email;
    public String password;
    public String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folders);

        ll = (LinearLayout) findViewById(R.id.foldersLayout);

        Intent intent = getIntent();
        email = getIntent().getExtras().getString("email");
        password = intent.getStringExtra("password");
        mail = intent.getStringExtra("mail");

        AsyncFolders asyncFolders = new AsyncFolders();
        asyncFolders.execute(email, password, mail);


    }

    class AsyncFolders extends AsyncTask<String, Void, String[]>{

        @Override
        protected String[] doInBackground(final String... strings) {
            Properties properties = new Properties();
            if (strings[2].equals("gmail")){
                properties.put("mail.store.protocol", "imaps");
                properties.put("mail.imaps.host", "imap.gmail.com");
                properties.put("mail.imaps.user", strings[0]);
                properties.put("mail.imaps.ssl.enable", "true");
                properties.put("mail.imaps.ssl.trust", "imap.gmail.com");
                properties.put("mail.imaps.port", "993");}
            else if (strings[2].equals("mail")){
                properties.put("mail.store.protocol", "imaps");
                properties.put("mail.imaps.host", "imap.mail.ru");
                properties.put("mail.imaps.user", strings[0]);
                properties.put("mail.imaps.ssl.enable", "true");
                properties.put("mail.imaps.ssl.trust", "imap.mail.ru");
                properties.put("mail.imaps.port", "993");
            }

            Session session = Session.getInstance(properties,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(strings[0], strings[1]);
                        }
                    });
            SessionSingleton sessionSingleton = SessionSingleton.getInstance(session);
            sessionSingleton.getSession().setDebug(true);

            //String[] s;

            if(strings[2].equals("gmail")){
                Store store = null;
                try {
                    store = sessionSingleton.getSession().getStore("imaps");
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                }
                try {
                    store.connect("imap.gmail.com", strings[0], strings[1]);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                Folder[] f = new Folder[0];
                try {
                    f = store.getDefaultFolder().list();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                int foldersCount = f.length;
                String[] s = new String[foldersCount];
                //s = new String[foldersCount];
                for(int i = 0; i < foldersCount; i++) {
                    s[i] = f[i].getName();
                }
                return s;

            } else if (strings[2].equals("mail")){
                Store store = null;
                try {
                    store = sessionSingleton.getSession().getStore("imaps");
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                }

                try {
                    store.connect("imap.mail.ru", strings[0], strings[1]);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                Folder[] f = new Folder[0];
                try {
                    f = store.getDefaultFolder().list();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                int foldersCount = f.length;
                String[] s = new String[foldersCount];
                //s = String[foldersCount];
                for(int i = 0; i < foldersCount; i++) {
                    s[i] = f[i].getName();
                }
                return s;
            }
            return null;
        }

        @Override
        protected void onPostExecute(final String[] s){
            for (int i = 0; i < s.length; i++){
                TextView tmp = new TextView(activity);
                tmp.setText(s[i]);
                final int finalI = i;
                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FoldersActivity.this, MailActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        intent.putExtra("mail", "mail");
                        intent.putExtra("folder", s[finalI]);
                        intent.putExtra("strategy", new ImapStrategy());
                        startActivity(intent);
                    }
                });
                ll.addView(tmp);

                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                tmp.setLayoutParams(textParam);
                tmp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                tmp.setGravity(Gravity.CENTER);
            }
        }
    }

}
