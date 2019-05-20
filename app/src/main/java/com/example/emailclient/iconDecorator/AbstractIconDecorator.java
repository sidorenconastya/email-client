package com.example.emailclient.iconDecorator;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.util.Arrays;

public abstract class AbstractIconDecorator implements Decorable{
    protected View view;
    protected View[] views;
    protected int drawableId;
    protected Activity context;

    public AbstractIconDecorator(Activity context, View view, int drawableId){
        super();
        this.view = view;
        this.context = context;
        this.drawableId = drawableId;
        decorate();
    }

    public AbstractIconDecorator (Activity context, View[] views){
        super();
        this.context = context;
        this.views = Arrays.copyOf(views, views.length);
        decorate();
    }
}
