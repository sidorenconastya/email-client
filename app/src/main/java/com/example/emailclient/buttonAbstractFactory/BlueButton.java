package com.example.emailclient.buttonAbstractFactory;

import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;

import com.example.emailclient.R;

public class BlueButton implements IButton {
    @Override
    public void paint(Activity activity, String name, Button button) {
        button.setText(name);
        //button.setBackgroundColor(Color.parseColor("#3F51B5"));
        button.setBackground(activity.getDrawable(R.drawable.button_rounded_blue));
    }
}
