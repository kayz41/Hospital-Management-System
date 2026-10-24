package com.mycompany.userauthentication;

public class WhatsAppNotificationService
        implements NotificationService {

    private final boolean enabled;


    public WhatsAppNotificationService(
            boolean enabled) {

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


        /*
         * WHATSAPP PROVIDER WILL BE CONNECTED HERE.
         *
         * Possible future providers:
         *
         * Meta WhatsApp Business Cloud API
         * Twilio WhatsApp
         * Other WhatsApp Business provider
         *
         * The expiry-alert system will not
         * need to be rewritten when connected.
         */


        throw new Exception(
                "WhatsApp service is enabled but "
              + "no WhatsApp provider has been configured.");
    }


    @Override
    public boolean isEnabled() {

        return enabled;
    }
}