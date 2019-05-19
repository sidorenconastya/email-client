package com.example.emailclient.toastFactory;

import android.app.Activity;
import android.widget.Toast;

public class DeleteToast implements IToast {
    @Override
    public void createToast(Activity activity) {
        Toast toast = Toast.makeText(activity.getApplicationContext(),
                "Message was deleted. Please refresh the page.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
