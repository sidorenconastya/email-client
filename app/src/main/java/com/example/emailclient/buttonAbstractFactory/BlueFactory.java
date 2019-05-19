package com.example.emailclient.buttonAbstractFactory;

public class BlueFactory implements ButtonFactory {
    @Override
    public IButton createButton() {
        return new BlueButton();
    }
}
