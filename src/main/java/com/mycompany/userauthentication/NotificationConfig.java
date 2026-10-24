package com.mycompany.userauthentication;

public class NotificationConfig {

    private final String emailAddress;
    private final String emailPassword;

    private final boolean emailEnabled;
    private final boolean smsEnabled;
    private final boolean whatsappEnabled;


    public NotificationConfig(
            String emailAddress,
            String emailPassword,
            boolean emailEnabled,
            boolean smsEnabled,
            boolean whatsappEnabled) {

        this.emailAddress =
                emailAddress;

        this.emailPassword =
                emailPassword;

        this.emailEnabled =
                emailEnabled;

        this.smsEnabled =
                smsEnabled;

        this.whatsappEnabled =
                whatsappEnabled;
    }


    public String getEmailAddress() {

        return emailAddress;
    }


    public String getEmailPassword() {

        return emailPassword;
    }


    public boolean isEmailEnabled() {

        return emailEnabled;
    }


    public boolean isSmsEnabled() {

        return smsEnabled;
    }


    public boolean isWhatsappEnabled() {

        return whatsappEnabled;
    }
}