package com.mycompany.userauthentication;

public class NotificationConfigLoader {

    public static NotificationConfig load() {

        String emailAddress =
                System.getenv(
                        "HMS_EMAIL_ADDRESS");


        String emailPassword =
                System.getenv(
                        "HMS_EMAIL_PASSWORD");


        boolean emailEnabled =
                Boolean.parseBoolean(
                        System.getenv(
                                "HMS_EMAIL_ENABLED"));


        boolean smsEnabled =
                Boolean.parseBoolean(
                        System.getenv(
                                "HMS_SMS_ENABLED"));


        boolean whatsappEnabled =
                Boolean.parseBoolean(
                        System.getenv(
                                "HMS_WHATSAPP_ENABLED"));


        return new NotificationConfig(
                emailAddress,
                emailPassword,
                emailEnabled,
                smsEnabled,
                whatsappEnabled);
    }
}