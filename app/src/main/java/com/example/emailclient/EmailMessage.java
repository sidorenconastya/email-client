package com.example.emailclient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Message;

public class EmailMessage{
    private String sender;
    private String subject;
    private String body;
    public Date date;

    EmailMessage() {}

    public EmailMessage(String sender, String subject, String body, Date date){
        this.sender = sender;
        this.subject = subject;
        this.body = body;
        this.date = date;
    }

    public String printEmail(){
        String result;
        result = "Subject: " + this.subject + "\n" + "From: " + this.sender + "\n";// + "\n" + this.body;
        return result;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        //String dateNew = date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        //Date dateNew = date;
        String  dateNew = simpleDateFormat.format(date);
        return dateNew;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
