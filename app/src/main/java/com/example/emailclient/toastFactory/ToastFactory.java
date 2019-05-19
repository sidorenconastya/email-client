package com.example.emailclient.toastFactory;

public class ToastFactory {
    public IToast getToast(String toastType) {
        if (toastType == null) {
            return null;
        }
        if (toastType.equalsIgnoreCase("delete")) {
            return new DeleteToast();
        } else if (toastType.equalsIgnoreCase("send")) {
            return new SendToast();
        }
        return null;
    }
}
