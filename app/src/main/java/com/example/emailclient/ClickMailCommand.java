package com.example.emailclient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import java.io.IOException;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;

public class ClickMailCommand implements View.OnClickListener {

    private Activity activity;
    private EmailMessage emailMessage;
    private Message message;
    public String content;

    ClickMailCommand(Activity activity, EmailMessage emailMessage, Message message) {
        this.activity = activity;
        this.emailMessage = emailMessage;
        this.message = message;
    }

    @Override
    public void onClick(View v) {

        AsyncMessage asyncMessage = new AsyncMessage();
        asyncMessage.execute(message);

    }

    class AsyncMessage extends AsyncTask<Message, Void, String>{

        @Override
        protected String doInBackground(Message... messages) {
            String content = null;
            try {
                content =  messages[0].getContent().toString();
            } catch (IOException | MessagingException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onPostExecute(String content){
            Intent intent = new Intent(activity, MessageActivity.class);
            intent.putExtra("sender", emailMessage.getSender());
            intent.putExtra("subject", emailMessage.getSubject());
            //intent.putExtra("body", emailMessage.getBody());
            intent.putExtra("date", emailMessage.getDate());
            intent.putExtra("body", content);
            activity.startActivity(intent);
        }
    }
}
