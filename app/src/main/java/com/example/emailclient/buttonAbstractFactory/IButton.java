package com.example.emailclient.buttonAbstractFactory;

import android.app.Activity;
import android.widget.Button;

public interface IButton {
    void paint(Activity activity, String name, Button button);
}
