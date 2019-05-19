package com.example.emailclient;

import android.app.Activity;
import android.view.View;

import com.example.emailclient.toastFactory.IToast;
import com.example.emailclient.toastFactory.ToastFactory;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

public class DeleteMailCommand implements View.OnClickListener {

    private Activity activity;
    private Message message;
    private Folder folder;

    public DeleteMailCommand(Activity activity, Message message, Folder folder){
        this.activity = activity;
        this.message = message;
        this.folder = folder;
    }

    @Override
    public void onClick(View v) {
        Thread thread = new Thread(){
            public void run(){
                try {
                    message.setFlag(Flags.Flag.DELETED, true);
                    folder.close(true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();

        ToastFactory toastFactory = new ToastFactory();
        IToast toast = toastFactory.getToast("delete");
        toast.createToast(activity);
    }
}
