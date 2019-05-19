package com.example.emailclient.emailState;

import android.widget.TextView;

import javax.mail.Flags;
import javax.mail.Message;

public class EmailStateContext implements EmailState {
    private EmailState state;

    public void setState(EmailState state) {
        this.state = state;
    }

    @Override
    public void doAction(TextView textView) {
        this.state.doAction(textView);
    }
}
