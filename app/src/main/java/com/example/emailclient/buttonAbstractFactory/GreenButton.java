package com.example.emailclient.buttonAbstractFactory;

import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;

import com.example.emailclient.R;

public class GreenButton implements IButton{
    @Override
    public void paint(Activity activity, String name, Button button) {
        button.setText(name);
        //button.setBackgroundColor(Color.parseColor("#399B4D"));
        button.setBackground(activity.getDrawable(R.drawable.button_rounded_green));
    }
}
