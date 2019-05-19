package com.example.emailclient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;

public class EmailMessage{
    private String sender;
    private String subject;
    private String body;
    public Date date;
    public String content;
    public boolean seenFlag;
    public boolean attachmentFlag;
    public File attachment;

    public boolean isAttachmentFlag() {
        return attachmentFlag;
    }

    public void setAttachmentFlag(boolean attachmentFlag) {
        this.attachmentFlag = attachmentFlag;
    }

    public boolean isSeenFlag() {
        return seenFlag;
    }

    public void setSeenFlag(boolean seenFlag) {
        this.seenFlag = seenFlag;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(Message message) throws IOException, MessagingException {
        this.content = message.getContent().toString();
    }

    public File getAttachment(){
        return attachment;
    }

    public void setAttachment(File file){
        this.attachment = file;
    }

}
