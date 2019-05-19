package com.example.emailclient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emailclient.buttonAbstractFactory.ButtonFactory;
import com.example.emailclient.buttonAbstractFactory.ConfigureFactory;
import com.example.emailclient.emailState.EmailState;
import com.example.emailclient.emailState.EmailStateContext;
import com.example.emailclient.emailState.ReadEmail;
import com.example.emailclient.emailState.UnreadEmail;
import com.example.emailclient.receiveEmailStrategies.IReceiveEmailStrategy;
import com.example.emailclient.receiveEmailStrategies.ImapStrategy;
import com.example.emailclient.sendEmailStrategies.GmailStrategy;
import com.example.emailclient.sendEmailStrategies.MailStrategy;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.search.FlagTerm;

public class MailActivity extends Activity {

    public LinearLayout emailLayout;
    public Button newEmailButton;
    private Activity activity = this;
    public Button refreshButton;
    public ProgressBar progressBar;
    public boolean seenFlag;
    public boolean hasAttachment;
    public Button foldersButton;
    public String folder;
    public TextView folderNameText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        emailLayout = (LinearLayout) findViewById(R.id.emailLayout);
        newEmailButton = (Button) findViewById(R.id.newEmailButton);
        refreshButton = (Button) findViewById(R.id.refreshButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        foldersButton = (Button) findViewById(R.id.foldersButton);
        folderNameText = (TextView) findViewById(R.id.folderNameText);

        //progressBar.setVisibility(ProgressBar.VISIBLE);

        Intent intent = getIntent();
        final String email = getIntent().getExtras().getString("email");
        final String password = intent.getStringExtra("password");
        final String mail = intent.getStringExtra("mail");
        final String folder = intent.getStringExtra("folder");

        emailStrategy = (IReceiveEmailStrategy) intent.getSerializableExtra("strategy");

        folderNameText.setText(folder);

        try{
        AsyncRequest asyncRequest = new AsyncRequest();
        asyncRequest.execute(email, password, mail, folder);} catch (Exception e){Thread thread = new Thread(){
            public void run(){
                Intent intent1 = new Intent(MailActivity.this, LoginActivity.class);
                Toast toast = Toast.makeText(activity.getApplicationContext(),
                        "Email or password is incorrect. Please try again.", Toast.LENGTH_SHORT);
                toast.show();

            }
        };
        thread.start();}

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
                asyncRequest.execute(email, password, mail, folder);
            }
        });

        foldersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MailActivity.this, FoldersActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("mail", "mail");
                //intent.putExtra("strategy", new ImapStrategy());
                startActivity(intent);
            }
        });

        ConfigureFactory configureFactory = new ConfigureFactory();
        ButtonFactory buttonFactory;
        buttonFactory = configureFactory.cofigureButtons(activity);
        buttonFactory.createButton().paint("Refresh", refreshButton);
        buttonFactory.createButton().paint("New", newEmailButton);
        buttonFactory.createButton().paint("Folders", foldersButton);

    }

    private IReceiveEmailStrategy emailStrategy;


    public class AsyncRequest extends AsyncTask<String, Void, ArrayList<EmailMessage>> {

        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected ArrayList<EmailMessage> doInBackground(String... params) {
            try {
                return emailStrategy.receiveEmail(params[0], params[1],params[2], params[3]);
            } catch (MessagingException | IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<EmailMessage> messages) {
            int viewCount = messages.size();

            progressBar.setVisibility(ProgressBar.INVISIBLE);

            for (int i = viewCount-1; i > 0; i--){
                TextView tmp = new TextView(activity);
                Button del = new Button(activity);
                LinearLayout ll = new LinearLayout(activity);

                //FlagTerm seenFlagTmp = new FlagTerm(seenFlag, true);

                final int finalI = i;
                final Message[] messages1 = emailStrategy.getMessages1();
                final Folder folder = emailStrategy.getFolder();

                tmp.setOnClickListener(new ClickMailCommand(activity, messages.get(i), messages1[i]));
                del.setOnClickListener(new DeleteMailCommand(activity,messages1[i],folder));

//                Thread thread = new Thread(){
//                    public void run(){
//                        try {
//                            //hasAttachment = (messages1[finalI]).isMimeType("multipart/mixed");
//                            if (messages1[finalI].isMimeType("multipart/mixed")){
//                                Multipart mp = (Multipart) messages1[finalI].getContent();
//                                if (mp.getCount() > 1) hasAttachment = true;
//                            } else hasAttachment = false;
//                        } catch (MessagingException | IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                thread.start();
//
//                if (hasAttachment){
//                    tmp.setBackgroundColor(Color.parseColor("#E2E2E2"));
//                }

                EmailStateContext emailStateContext = new EmailStateContext();
                EmailState readEmail = new ReadEmail();
                EmailState unreadEmail = new UnreadEmail();

                if(!messages.get(i).isSeenFlag()) {
                    //tmp.setBackgroundColor(Color.parseColor("#E2E2E2"));
                    emailStateContext.setState(unreadEmail);
                    emailStateContext.doAction(tmp);
                } else if (messages.get(i).isSeenFlag()){
                    emailStateContext.setState(readEmail);
                    emailStateContext.doAction(tmp);
                }

                //System.out.println("FLAG:"+seenFlag);

                tmp.setText(messages.get(i).printEmail());
                ConfigureFactory configureFactory = new ConfigureFactory();
                ButtonFactory buttonFactory;
                buttonFactory = configureFactory.cofigureButtons(activity);
                buttonFactory.createButton().paint("X", del);

                int dpValueW = 100; // margin in dips
                float dW = activity.getApplicationContext().getResources().getDisplayMetrics().density;
                int width = (int)(dpValueW * dW);

                del.setWidth(width);

                int dpValue = 8; // margin in dips
                float d = activity.getApplicationContext().getResources().getDisplayMetrics().density;
                int margin = (int)(dpValue * d);

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                try {
                    display.getRealSize(size);
                } catch (NoSuchMethodError err) {
                    display.getSize(size);
                }
                int widthDisplay = size.x;

                emailLayout.addView(ll);
                ll.addView(tmp);
                ll.addView(del);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)(widthDisplay*0.8),
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                tmp.setLayoutParams(layoutParams);

                LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) del.getLayoutParams();
                lp1.gravity = Gravity.CENTER_VERTICAL;
                del.setLayoutParams(lp1);



                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ll.getLayoutParams();
                lp.setMargins(margin, margin, margin, margin);
//                textViews[i] = tmp;
            }

        }

    }
}
