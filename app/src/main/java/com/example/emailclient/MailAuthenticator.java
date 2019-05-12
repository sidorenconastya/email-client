package com.example.emailclient;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {
    String e;
    String p;
    public MailAuthenticator (String email, String password){
        super();
        this.e = email;
        this.p = password;
    }
    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(e, p);
    }
}
