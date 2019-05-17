package com.example.emailclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emailclient.receiveEmailStrategies.IReceiveEmailStrategy;
import com.example.emailclient.receiveEmailStrategies.ImapStrategy;
import com.example.emailclient.receiveEmailStrategies.PopStrategy;
import com.example.emailclient.sendEmailStrategies.ISendEmailStrategy;

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
    public String email;
    public String password;
    public String mail;
    private ISendEmailStrategy emailStrategy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mail);

        sendButton = (Button) findViewById(R.id.sendButton);
        messageText = (EditText) findViewById(R.id.messageText);
        recipientText = (EditText) findViewById(R.id.recipientText);
        subjectText = (EditText) findViewById(R.id.subjectText);

        Intent intent = getIntent();
        email = getIntent().getExtras().getString("email");
        password = intent.getStringExtra("password");
        mail = intent.getStringExtra("mail");
        emailStrategy = (ISendEmailStrategy) intent.getSerializableExtra("strategy");



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String to = recipientText.getText().toString();
                final String message = messageText.getText().toString();
                final String subject = subjectText.getText().toString();
                AsyncEmail asyncEmail = new AsyncEmail();
                asyncEmail.execute(email, password, to, message, subject, mail);
            }
        });

    }

    public class AsyncEmail extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                emailStrategy.sendEmail(params[0], params[1], params[2], params[3], params[4]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            recipientText.setText("");
            messageText.setText("");
            subjectText.setText("");
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Email was sent successfully!", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(NewMailActivity.this, MailActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("mail", mail);
            if (mail.equals("mail")){
                intent.putExtra("strategy", new PopStrategy());}
            else if (mail.equals("gmail")){intent.putExtra("strategy", new ImapStrategy());}
            startActivity(intent);
        }
    }
}
