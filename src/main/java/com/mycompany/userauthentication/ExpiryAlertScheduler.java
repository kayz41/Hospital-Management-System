package com.mycompany.userauthentication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExpiryAlertScheduler {

    private final ScheduledExecutorService scheduler;

    private final ExpiryAlertService expiryAlertService;

    private final String recipientEmail;

    private boolean started = false;


    public ExpiryAlertScheduler() {

        scheduler =
                Executors.newSingleThreadScheduledExecutor();

        expiryAlertService =
                new ExpiryAlertService();

        recipientEmail =
                System.getenv(
                        "HMS_ALERT_RECIPIENT");
    }


    public void start() {

        if (started) {
            return;
        }

        started = true;

        /*
         * Run the first check immediately.
         */
        checkExpiry();


        /*
         * Then check every 24 hours.
         */
        scheduler.scheduleAtFixedRate(
                this::checkExpiry,
                24,
                24,
                TimeUnit.HOURS);
    }


    private void checkExpiry() {

        try {

            if (recipientEmail == null
                    || recipientEmail
                            .trim()
                            .isEmpty()) {

                System.err.println(
                        "HMS_ALERT_RECIPIENT "
                      + "is not configured.");

                return;
            }


            System.out.println(
                    "Running automatic "
                  + "pharmacy expiry check...");


            expiryAlertService
                    .checkExpiryAlerts(
                            recipientEmail);


            System.out.println(
                    "Automatic pharmacy "
                  + "expiry check completed.");
        }

        catch (Exception e) {

            System.err.println(
                    "Automatic pharmacy "
                  + "expiry check failed:");

            e.printStackTrace();
        }
    }


    public void stop() {

        if (!scheduler.isShutdown()) {

            scheduler.shutdownNow();
        }
    }
}