package com.example.emailclient;

import java.util.Date;

public class EmailMessageBuilder {

    private EmailMessage emailMessage = new EmailMessage();

    public EmailMessageBuilder withSender(String sender) {
        emailMessage.setSender(sender);

        return this;
    }

    public EmailMessageBuilder withSubject(String subject) {
        emailMessage.setSubject(subject);

        return this;
    }

    public EmailMessageBuilder withDate(Date date) {
        emailMessage.setDate(date);

        return this;
    }

    public EmailMessageBuilder withBody(String body) {
        emailMessage.setBody(body);

        return this;
    }

    void reset() {
        emailMessage = new EmailMessage();
    }

    public EmailMessage getResult() {
        return emailMessage;
    }
}
