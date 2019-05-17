package com.example.emailclient.sendEmailStrategies;

import java.io.Serializable;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface ISendEmailStrategy extends Serializable {
    void sendEmail(String email, String password, String to, String body, String subject) throws MessagingException;
}
