package com.example.emailclient;

import javax.mail.Session;

public final class SessionSingleton{
    public Session session;
    private static SessionSingleton instance = null;

    private SessionSingleton(Session session) {
        this.session = session;
    }

    public static SessionSingleton getInstance(Session session){
        if (instance == null) {
            instance = new SessionSingleton(session);
        }
        return instance;
    }

    public Session getSession() {
        return session;
    }

}
