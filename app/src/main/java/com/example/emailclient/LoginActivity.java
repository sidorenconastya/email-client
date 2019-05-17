package com.example.emailclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.emailclient.receiveEmailStrategies.ImapStrategy;
import com.example.emailclient.receiveEmailStrategies.PopStrategy;

public class LoginActivity extends Activity {

    public EditText email_text;
    public EditText password_text;
    public Button login_button;
    public String emailIntent;
    public String passwordIntent;
    public Button gmailButton;

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
                Intent intent = new Intent(LoginActivity.this, MailActivity.class);
                intent.putExtra("email", emailIntent);
                intent.putExtra("password", passwordIntent);
                intent.putExtra("mail", "mail");

                intent.putExtra("strategy", new PopStrategy());
                startActivity(intent);
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

                intent.putExtra("strategy", new ImapStrategy());

                startActivity(intent);
            }
        });


    }
}
