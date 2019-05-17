package com.example.emailclient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emailclient.receiveEmailStrategies.IReceiveEmailStrategy;
import com.example.emailclient.sendEmailStrategies.GmailStrategy;
import com.example.emailclient.sendEmailStrategies.MailStrategy;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

public class MailActivity extends Activity {

    public LinearLayout emailLayout;
    public Button newEmailButton;
    private Activity activity = this;
    public Button refreshButton;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        emailLayout = (LinearLayout) findViewById(R.id.emailLayout);
        newEmailButton = (Button) findViewById(R.id.newEmailButton);
        refreshButton = (Button) findViewById(R.id.refreshButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //progressBar.setVisibility(ProgressBar.VISIBLE);

        Intent intent = getIntent();
        final String email = getIntent().getExtras().getString("email");
        final String password = intent.getStringExtra("password");
        final String mail = intent.getStringExtra("mail");

        emailStrategy = (IReceiveEmailStrategy) intent.getSerializableExtra("strategy");

        AsyncRequest asyncRequest = new AsyncRequest();
        asyncRequest.execute(email, password, mail);

        newEmailButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MailActivity.this, NewMailActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("mail", mail);
                if (mail.equals("mail")){
                intent.putExtra("strategy", new MailStrategy());}
                else if (mail.equals("gmail")){intent.putExtra("strategy", new GmailStrategy());}
                startActivity(intent);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                emailLayout.removeAllViews();
                AsyncRequest asyncRequest = new AsyncRequest();
                asyncRequest.execute(email, password, mail);
            }
        });
        //progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    private IReceiveEmailStrategy emailStrategy;


    public class AsyncRequest extends AsyncTask<String, Void, ArrayList<EmailMessage>> {

        @Override
        protected void onPreExecute(){
            //super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected ArrayList<EmailMessage> doInBackground(String... params) {
            try {
                return emailStrategy.receiveEmail(params[0], params[1]);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<EmailMessage> messages) {
            int viewCount = messages.size();
//            TextView[] textViews = new TextView[viewCount];

            progressBar.setVisibility(ProgressBar.INVISIBLE);

//            for (final EmailMessage message : messages) {
//                TextView tmp = new TextView(activity);
//                Button del = new Button(activity);
//
//                tmp.setOnClickListener(new ClickMailCommand(activity, message));
//                del.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Message[] messages1 = emailStrategy.getMessages1();
//
//
//                    }
//                });
//
//                tmp.setText(message.printEmail());
//                emailLayout.addView(tmp);
//                emailLayout.addView(del);
//            }

            for (int i = 0; i < viewCount; i++){
                TextView tmp = new TextView(activity);
                Button del = new Button(activity);

                final int finalI = i;
                tmp.setOnClickListener(new ClickMailCommand(activity, messages.get(i)));

                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Message[] messages1 = emailStrategy.getMessages1();
                        final Folder folder = emailStrategy.getFolder();
                        Thread thread = new Thread(){
                               public void run(){
                                   try {
                                       messages1[finalI].setFlag(Flags.Flag.DELETED, true);
                                       folder.close(true);
                                   } catch (MessagingException e) {
                                       e.printStackTrace();
                                   }

                               }
                        };
                        thread.start();

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Message was deleted. Please refresh the page.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

                tmp.setText(messages.get(i).printEmail());
                emailLayout.addView(tmp);
                emailLayout.addView(del);
//                textViews[i] = tmp;
            }

        }

    }
}
