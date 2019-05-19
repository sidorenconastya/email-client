package com.example.emailclient.emailState;

import android.widget.TextView;

import javax.mail.Flags;
import javax.mail.Message;

public interface EmailState {
    void doAction(TextView textView);
}
