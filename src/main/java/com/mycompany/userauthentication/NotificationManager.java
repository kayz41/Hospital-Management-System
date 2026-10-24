package com.mycompany.userauthentication;

public class NotificationManager {

    private final NotificationConfig config;


    public NotificationManager() {

        config =
                NotificationConfigLoader.load();
    }


    /*
     * =================================================
     * SEND NOTIFICATION
     * =================================================
     *
     * Returns TRUE if at least one enabled
     * notification channel succeeds.
     *
     * Returns FALSE if all enabled channels fail
     * or no notification channel is enabled.
     *
     */

    public boolean sendNotification(
            String recipient,
            String subject,
            String message) {


        boolean notificationSent =
                false;


        /*
         * =================================
         * EMAIL
         * =================================
         */

        if (config.isEmailEnabled()) {

            try {

                EmailNotificationService email =
                        new EmailNotificationService(
                                config.getEmailAddress(),
                                config.getEmailPassword(),
                                true);


                email.send(
                        recipient,
                        subject,
                        message);


                notificationSent =
                        true;


                System.out.println(
                        "Email notification sent successfully.");


            }

            catch (Exception e) {

                System.err.println(
                        "Email notification failed: "
                      + e.getMessage());
            }
        }


        /*
         * =================================
         * SMS
         * =================================
         */

        if (config.isSmsEnabled()) {

            try {

                SmsNotificationService sms =
                        new SmsNotificationService(
                                true);


                sms.send(
                        recipient,
                        subject,
                        message);


                notificationSent =
                        true;


                System.out.println(
                        "SMS notification sent successfully.");


            }

            catch (Exception e) {

                System.err.println(
                        "SMS notification failed: "
                      + e.getMessage());
            }
        }


        /*
         * =================================
         * WHATSAPP
         * =================================
         */

        if (config.isWhatsappEnabled()) {

            try {

                WhatsAppNotificationService whatsapp =
                        new WhatsAppNotificationService(
                                true);


                whatsapp.send(
                        recipient,
                        subject,
                        message);


                notificationSent =
                        true;


                System.out.println(
                        "WhatsApp notification sent successfully.");


            }

            catch (Exception e) {

                System.err.println(
                        "WhatsApp notification failed: "
                      + e.getMessage());
            }
        }


        /*
         * =================================
         * NO CHANNEL AVAILABLE
         * =================================
         */

        if (!notificationSent) {

            System.err.println(
                    "No notification channel "
                  + "successfully delivered the alert.");
        }


        return notificationSent;
    }
}