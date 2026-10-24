package com.mycompany.userauthentication;

import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailNotificationService
        implements NotificationService {

    private final String senderEmail;
    private final String senderPassword;
    private final boolean enabled;


    public EmailNotificationService(
            String senderEmail,
            String senderPassword,
            boolean enabled) {

        this.senderEmail =
                senderEmail;

        this.senderPassword =
                senderPassword;

        this.enabled =
                enabled;
    }


    @Override
    public void send(
            String recipient,
            String subject,
            String message)
            throws Exception {

        if (!enabled) {

            return;
        }


        if (recipient == null
                || recipient.trim().isEmpty()) {

            throw new Exception(
                    "Email recipient is missing.");
        }


        Properties properties =
                new Properties();


        properties.put(
                "mail.smtp.auth",
                "true");

        properties.put(
                "mail.smtp.starttls.enable",
                "true");

        properties.put(
                "mail.smtp.host",
                "smtp.gmail.com");

        properties.put(
                "mail.smtp.port",
                "587");


        Session session =
                Session.getInstance(
                        properties,
                        new Authenticator() {

                    @Override
                    protected PasswordAuthentication
                    getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                senderEmail,
                                senderPassword);
                    }
                });


        Message email =
                new MimeMessage(session);


        email.setFrom(
                new InternetAddress(
                        senderEmail));


        email.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(
                        recipient));


        email.setSubject(subject);

        email.setText(message);


        Transport.send(email);
    }


    @Override
    public boolean isEnabled() {

        return enabled;
    }
}