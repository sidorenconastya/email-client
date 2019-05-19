package com.example.emailclient.emailState;

import android.graphics.Typeface;
import android.widget.TextView;

import javax.mail.Flags;
import javax.mail.Message;

public class ReadEmail implements EmailState {
    @Override
    public void doAction(TextView textView) {
        textView.setTypeface(null, Typeface.NORMAL);
    }
}
