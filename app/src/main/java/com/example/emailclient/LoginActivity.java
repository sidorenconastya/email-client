package com.example.emailclient;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    private EditText email_text;
    private EditText password_text;
    private Button login_button;
    public String email;
    public String password;

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password_text = findViewById(R.id.passwordText);
        email_text = findViewById(R.id.emailText);
        email = email_text.getText().toString();
        password = password_text.getText().toString();
        login_button = findViewById(R.id.loginButton);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MailActivity.class);
                startActivity(intent);
            }
        });
    }
}
