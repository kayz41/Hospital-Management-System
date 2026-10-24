package com.mycompany.userauthentication;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Joel Kayz
 */
public class SessionManager {

    /*
     * =========================================
     * SESSION TIMEOUT CONFIGURATION
     * =========================================
     *
     * 15 minutes of inactivity.
     */

    private static final int TIMEOUT_MINUTES = 15;

    private static final long TIMEOUT_MILLISECONDS =
            TIMEOUT_MINUTES * 60L * 1000L;


    private static long lastActivityTime =
            System.currentTimeMillis();


    private static Timer timeoutTimer;

    private static AWTEventListener activityListener;

    private static JFrame currentFrame;

    private static boolean sessionActive = false;


    /*
     * =========================================
     * START SESSION
     * =========================================
     */

    public static void startSession(JFrame frame) {

        stopSession();

        currentFrame = frame;

        sessionActive = true;

        updateActivityTime();


        /*
         * Monitor keyboard and mouse activity.
         */

        activityListener =
                new AWTEventListener() {

                    @Override
                    public void eventDispatched(
                            AWTEvent event) {

                        if (!sessionActive) {

                            return;
                        }


                        /*
                         * Keyboard activity.
                         */

                        if (event instanceof KeyEvent) {

                            updateActivityTime();

                            return;
                        }


                        /*
                         * Mouse activity.
                         */

                        if (event instanceof MouseEvent) {

                            updateActivityTime();
                        }
                    }
                };


        Toolkit.getDefaultToolkit()
        .addAWTEventListener(
                activityListener,
                AWTEvent.KEY_EVENT_MASK
                | AWTEvent.MOUSE_EVENT_MASK);


        /*
         * Check for timeout every second.
         */

        timeoutTimer =
                new Timer(
                        1000,
                        e -> checkTimeout());


        timeoutTimer.start();
    }


    /*
     * =========================================
     * UPDATE ACTIVITY
     * =========================================
     */

    private static void updateActivityTime() {

        lastActivityTime =
                System.currentTimeMillis();

        UserSession.updateActivity();
    }


    /*
     * =========================================
     * CHECK TIMEOUT
     * =========================================
     */

    private static void checkTimeout() {

        if (!sessionActive) {

            return;
        }


        long inactiveTime =
                System.currentTimeMillis()
                - lastActivityTime;


        if (inactiveTime
                >= TIMEOUT_MILLISECONDS) {

            expireSession();
        }
    }


    /*
     * =========================================
     * EXPIRE SESSION
     * =========================================
     */

    private static void expireSession() {

        if (!sessionActive) {

            return;
        }


        JFrame expiredFrame =
                currentFrame;


        /*
         * Stop session monitoring.
         */

        stopSession();


        /*
         * Record the timeout.
         */

        try {

            AuditLogger.log(
                    "Session Expired Due To Inactivity: "
                    + UserSession.username);

        }

        catch (Exception e) {

            System.err.println(
                    "Session expiry audit failed: "
                    + e.getMessage());
        }


        /*
         * Clear session information.
         */

        UserSession.clearSession();

        Login.loggedUsername = "";

        Login.loggedRole = "";


        /*
         * Close the current dashboard.
         */

        if (expiredFrame != null) {

            expiredFrame.dispose();
        }


        /*
         * Return to Login.
         */

        EventQueue.invokeLater(() -> {

            JOptionPane.showMessageDialog(
                    null,
                    "Your session has expired due "
                    + "to inactivity.\n\n"
                    + "Please log in again.",
                    "Session Expired",
                    JOptionPane.INFORMATION_MESSAGE);


            Login login =
                    new Login();


            login.setVisible(true);
        });


        currentFrame = null;
    }


    /*
     * =========================================
     * STOP SESSION
     * =========================================
     */

    public static void stopSession() {

        sessionActive = false;


        if (timeoutTimer != null) {

            timeoutTimer.stop();

            timeoutTimer = null;
        }


        if (activityListener != null) {

            Toolkit.getDefaultToolkit()
                    .removeAWTEventListener(
                            activityListener);

            activityListener = null;
        }
    }


    /*
     * =========================================
     * CHECK SESSION STATUS
     * =========================================
     */

    public static boolean isSessionActive() {

        return sessionActive;
    }
}