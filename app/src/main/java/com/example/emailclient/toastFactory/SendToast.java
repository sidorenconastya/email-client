package com.example.emailclient.toastFactory;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emailclient.NewMailActivity;
import com.example.emailclient.R;
import com.example.emailclient.iconDecorator.IconDecorator;

import org.w3c.dom.Text;

public class SendToast implements IToast {
    @Override
    public void createToast(Activity activity) {
        LayoutInflater inflater =  activity.getLayoutInflater();
        View toastView = inflater.inflate(R.layout.toast_layout, (ViewGroup) activity.findViewById(R.id.toastLayout));

//        Toast toast = Toast.makeText(activity.getApplicationContext(),
//                "Email was sent successfully!", Toast.LENGTH_SHORT);
//        ImageView view = new ImageView(activity);
        //view.setImageResource(R.drawable.success);
        //ImageView imageView = (ImageView) activity.findViewById(R.id.imageView1);
        //Toast toast = new Toast(activity);

        ImageView imageView = (ImageView) toastView.findViewById(R.id.toastImage);
        TextView textView = (TextView) toastView.findViewById(R.id.toastText);
        textView.setText("Email was sent successfully!");

        Toast toast = new Toast(activity.getApplicationContext());
        //toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        IconDecorator decorator = new IconDecorator(activity, imageView, R.drawable.success_small);
        toast.setView(toastView);
        toast.show();
    }
}
