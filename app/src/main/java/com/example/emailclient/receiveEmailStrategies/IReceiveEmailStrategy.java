package com.example.emailclient.receiveEmailStrategies;

import com.example.emailclient.EmailMessage;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

public interface IReceiveEmailStrategy extends Serializable {

    ArrayList<EmailMessage> receiveEmail(String email, String password) throws MessagingException, IOException;

    void setMessages1(Message[] messages1);

    Message[] getMessages1();

    void setFolder(Folder folder);

    Folder getFolder();

}
