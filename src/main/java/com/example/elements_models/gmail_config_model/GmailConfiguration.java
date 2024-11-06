package com.example.elements_models.gmail_config_model;

public class GmailConfiguration {
    private final String mailSender;
    private final String mailReceiver;

    public GmailConfiguration(String mailSender, String mailReceiver) {
        this.mailSender = mailSender;
        this.mailReceiver = mailReceiver;
    }

    public String getMailSender() {
        return mailSender;
    }

    public String getMailReceiver() {
        return mailReceiver;
    }
}
