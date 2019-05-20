package com.example.emailclient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emailclient.buttonAbstractFactory.BlueFactory;
import com.example.emailclient.buttonAbstractFactory.ButtonFactory;
import com.example.emailclient.buttonAbstractFactory.ConfigureFactory;
import com.example.emailclient.buttonAbstractFactory.IButton;
import com.example.emailclient.toastFactory.IToast;
import com.example.emailclient.toastFactory.ToastFactory;
import com.example.emailclient.receiveEmailStrategies.ImapStrategy;
import com.example.emailclient.sendEmailStrategies.ISendEmailStrategy;

import javax.mail.MessagingException;

public class NewMailActivity extends AppCompatActivity {

    public Button sendButton;
    public EditText messageText;
    public EditText recipientText;
    public EditText subjectText;
    public String email;
    public String password;
    public String mail;
    private ISendEmailStrategy emailStrategy;
    public Button attachmentButton;
    String filePath = null;
    public TextView attachmentText;
    public Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mail);

        sendButton = (Button) findViewById(R.id.sendButton);
        messageText = (EditText) findViewById(R.id.messageText);
        recipientText = (EditText) findViewById(R.id.recipientText);
        subjectText = (EditText) findViewById(R.id.subjectText);
        attachmentButton = (Button) findViewById(R.id.attachmentButton);
        attachmentText = (TextView) findViewById(R.id.attachmenText);

        Intent intent = getIntent();
        email = getIntent().getExtras().getString("email");
        password = intent.getStringExtra("password");
        mail = intent.getStringExtra("mail");
        emailStrategy = (ISendEmailStrategy) intent.getSerializableExtra("strategy");

        ConfigureFactory configureFactory = new ConfigureFactory();
        ButtonFactory buttonFactory;
        buttonFactory = configureFactory.cofigureButtons(activity);
        buttonFactory.createButton().paint(activity, "Send", sendButton);
        buttonFactory.createButton().paint(activity, "Attach a file", attachmentButton);

        attachmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                startActivityForResult(intent, 7);
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 7);
            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String to = recipientText.getText().toString();
                final String message = messageText.getText().toString();
                final String subject = subjectText.getText().toString();
                AsyncEmail asyncEmail = new AsyncEmail();
                asyncEmail.execute(email, password, to, message, subject, mail, filePath);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch(requestCode){
            case 7:
                if(resultCode==RESULT_OK){
//                    filepath = data.getData().getPath();
//                    File file = new File(filepath);
//                    String absolutePath = null;
//                    try {
//                        absolutePath = file.getCanonicalPath();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    Uri photoUri = data.getData();
                    filePath = FetchPath.getPath(this, photoUri);
                    //Toast.makeText(NewMailActivity.this, PathHolder , Toast.LENGTH_LONG).show();
                    attachmentText.setText("File: " + filePath);
                }
                break;
        }
    }

    public class AsyncEmail extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                emailStrategy.sendEmail(params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            recipientText.setText("");
            messageText.setText("");
            subjectText.setText("");

//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "Email was sent successfully!", Toast.LENGTH_SHORT);
//            toast.show();
            ToastFactory toastFactory = new ToastFactory();
            IToast toast = toastFactory.getToast("send");
            toast.createToast(activity);

            Intent intent = new Intent(NewMailActivity.this, MailActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("mail", mail);
            intent.putExtra("folder", "INBOX");
            //if (mail.equals("mail")){
            //    intent.putExtra("strategy", new PopStrategy());}
            //else if (mail.equals("gmail")){intent.putExtra("strategy", new ImapStrategy());}
            intent.putExtra("strategy", new ImapStrategy());
            startActivity(intent);
        }
    }

}
