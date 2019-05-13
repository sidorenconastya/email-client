package com.example.emailclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    public TextView fromTextView;
    public TextView subjectTextView;
    public TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        fromTextView = (TextView) findViewById(R.id.fromTextView);
        subjectTextView = (TextView) findViewById(R.id.subjectTextView);
        messageTextView = (TextView) findViewById(R.id.messageTextView);

        Intent intent = getIntent();
        String from = intent.getExtras().getString("sender");
        String subject = intent.getExtras().getString("subject");
        String body = intent.getExtras().getString("body");

        fromTextView.setText(from);
        subjectTextView.setText(subject);
        messageTextView.setText(body);
    }
}