package com.example.emailclient.toastFactory;

import android.app.Activity;
import android.widget.Toast;

public class SendToast implements IToast {
    @Override
    public void createToast(Activity activity) {
        Toast toast = Toast.makeText(activity.getApplicationContext(),
                "Email was sent successfully!", Toast.LENGTH_SHORT);
        toast.show();
    }
}
