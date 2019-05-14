package com.example.emailclient;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    public EditText email_text;
    public EditText password_text;
    public Button login_button;
    public String emailIntent;
    public String passwordIntent;
    public Button gmailButton;

    /*public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password_text = (EditText) findViewById(R.id.passwordText);
        email_text = (EditText) findViewById(R.id.emailText);

       // System.out.println("EmailLogin"+emailIntent);
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
                startActivity(intent);
            }
        });


    }
}
