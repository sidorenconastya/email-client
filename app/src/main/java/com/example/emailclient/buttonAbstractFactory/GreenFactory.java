package com.example.emailclient.buttonAbstractFactory;

public class GreenFactory implements ButtonFactory {
    @Override
    public IButton createButton() {
        return new GreenButton();
    }
}
