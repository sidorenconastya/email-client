package com.example.emailclient.iconDecorator;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class IconDecorator extends AbstractIconDecorator {
    public IconDecorator (Activity context, View view, int drawableId){
        super(context, view, drawableId);
    }

    @Override
    public void decorate() {
        view.setBackground(context.getDrawable(drawableId));
    }

}
