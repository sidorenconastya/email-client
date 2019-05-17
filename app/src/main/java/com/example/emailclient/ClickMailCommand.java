package com.example.emailclient;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class ClickMailCommand implements View.OnClickListener {

    private Activity activity;
    private EmailMessage emailMessage;

    ClickMailCommand(Activity activity, EmailMessage emailMessage) {
        this.activity = activity;
        this.emailMessage = emailMessage;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, MessageActivity.class);

        intent.putExtra("sender", emailMessage.getSender());
        intent.putExtra("subject", emailMessage.getSubject());
        intent.putExtra("body", emailMessage.getBody());
        intent.putExtra("date", emailMessage.getDate());

        activity.startActivity(intent);
    }
}
