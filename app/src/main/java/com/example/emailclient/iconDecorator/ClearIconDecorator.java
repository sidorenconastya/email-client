package com.example.emailclient.iconDecorator;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class ClearIconDecorator extends AbstractIconDecorator {

    public ClearIconDecorator(Activity context, View[] views){
        super(context, views);
    }
    @Override
    public void decorate() {
        for (View view: views){
            view.setBackground(null);
        }
    }


}
