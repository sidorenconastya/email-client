package com.example.emailclient;

public class EmailMessage {
    public String sender;
    public String subject;
    public String body;

    public EmailMessage(String sender, String subject, String body){
        this.sender = sender;
        this.subject = subject;
        this.body = body;
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
}
