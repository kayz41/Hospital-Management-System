package com.mycompany.userauthentication;

public interface NotificationService {

    void send(
            String recipient,
            String subject,
            String message) throws Exception;

    boolean isEnabled();
}