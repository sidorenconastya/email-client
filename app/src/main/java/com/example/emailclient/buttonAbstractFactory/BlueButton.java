package com.example.emailclient.buttonAbstractFactory;

import android.graphics.Color;
import android.widget.Button;

public class BlueButton implements IButton {
    @Override
    public void paint(String name, Button button) {
        button.setText(name);
        button.setBackgroundColor(Color.parseColor("#4242F9"));
    }
}
