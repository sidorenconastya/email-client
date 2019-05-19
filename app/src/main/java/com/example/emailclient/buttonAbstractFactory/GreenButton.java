package com.example.emailclient.buttonAbstractFactory;

import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;

public class GreenButton implements IButton{
    @Override
    public void paint(String name, Button button) {
        button.setText(name);
        button.setBackgroundColor(Color.parseColor("#0D9931"));
    }
}
