package com.example.emailclient.buttonAbstractFactory;

import android.app.Activity;

import com.example.emailclient.LoginActivity;
import com.example.emailclient.MailActivity;
import com.example.emailclient.NewMailActivity;

public class ConfigureFactory {

    public ButtonFactory cofigureButtons(Activity activity){
        ButtonFactory buttonFactory = null;
        if (activity instanceof LoginActivity){
            buttonFactory = new BlueFactory();
        } else if (activity instanceof NewMailActivity){
            buttonFactory = new GreenFactory();
        } else if (activity instanceof MailActivity) {
            buttonFactory = new BlueFactory();
        }
        return buttonFactory;
    }
}
