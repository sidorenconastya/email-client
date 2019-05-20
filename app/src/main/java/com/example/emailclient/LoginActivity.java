package com.example.emailclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emailclient.buttonAbstractFactory.ButtonFactory;
import com.example.emailclient.buttonAbstractFactory.ConfigureFactory;
import com.example.emailclient.receiveEmailStrategies.ImapStrategy;
import com.example.emailclient.receiveEmailStrategies.PopStrategy;

import javax.mail.MessagingException;

public class LoginActivity extends Activity {

    public EditText email_text;
    public EditText password_text;
    public Button login_button;
    public String emailIntent;
    public String passwordIntent;
    public Button gmailButton;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password_text = (EditText) findViewById(R.id.passwordText);
        email_text = (EditText) findViewById(R.id.emailText);

        login_button = findViewById(R.id.loginButton);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailIntent = email_text.getText().toString();
                passwordIntent = password_text.getText().toString();
//                Intent intent = new Intent(LoginActivity.this, MailActivity.class);
//                intent.putExtra("email", emailIntent);
//                intent.putExtra("password", passwordIntent);
//                intent.putExtra("mail", "mail");
//                intent.putExtra("strategy", new ImapStrategy());

                //intent.putExtra("strategy", new PopStrategy());

                try {
                    Intent intent = new Intent(LoginActivity.this, MailActivity.class);
                    intent.putExtra("email", emailIntent);
                    intent.putExtra("password", passwordIntent);
                    intent.putExtra("mail", "mail");
                    intent.putExtra("strategy", new ImapStrategy());
                    intent.putExtra("folder", "INBOX");
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast toast = Toast.makeText(activity.getApplicationContext(),
                            "Email or password is incorrect. Please try again.", Toast.LENGTH_SHORT);
                    toast.show();
                    password_text.setText("");
                    email_text.setText("");
                }
            }
        });
        gmailButton = findViewById(R.id.gmailButton);
        gmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailIntent = email_text.getText().toString();
                passwordIntent = password_text.getText().toString();
                Intent intent = new Intent(LoginActivity.this, MailActivity.class);
                intent.putExtra("email", emailIntent);
                intent.putExtra("password", passwordIntent);
                intent.putExtra("mail", "gmail");
                intent.putExtra("folder", "INBOX");

                intent.putExtra("strategy", new ImapStrategy());

                try {
                    startActivity(intent);}
                catch (NullPointerException e){
                    Toast toast = Toast.makeText(activity.getApplicationContext(),
                            "Email or password is incorrect. Please try again.", Toast.LENGTH_SHORT);
                    toast.show();
                    password_text.setText("");
                    email_text.setText("");
                }
            }
        });

        ConfigureFactory configureFactory = new ConfigureFactory();
        ButtonFactory buttonFactory;
        buttonFactory = configureFactory.cofigureButtons(activity);
        buttonFactory.createButton().paint(activity, "Sign in mail", login_button);
        buttonFactory.createButton().paint(activity, "Sign in gmail", gmailButton);


    }
}
