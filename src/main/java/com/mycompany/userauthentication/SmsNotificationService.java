
package com.mycompany.userauthentication;

public class SmsNotificationService
        implements NotificationService {

    private final boolean enabled;

    public SmsNotificationService(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void send(
            String recipient,
            String subject,
            String message) throws Exception {

        if (!enabled) {
            return;
        }

        String phoneNumber =
                System.getenv("HMS_SMS_ALERT_RECIPIENT");

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalStateException(
                    "HMS_SMS_ALERT_RECIPIENT is not configured.");
        }

        String result =
                AfricaTalkingSmsClient.sendSandbox(
                        phoneNumber.trim(),
                        subject + "\n\n" + message);

        System.out.println(
                "Pharmacy SMS provider result: " + result);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
