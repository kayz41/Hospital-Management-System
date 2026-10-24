package com.mycompany.userauthentication;

public class UserSession {

    public static String role;
    public static String username;

    private static long lastActivityTime;


    // =========================================
    // START SESSION
    // =========================================

    public static void startSession(
            String sessionUsername,
            String sessionRole) {

        username = sessionUsername;
        role = sessionRole;

        updateActivity();
    }


    // =========================================
    // UPDATE ACTIVITY
    // =========================================

    public static void updateActivity() {

        lastActivityTime =
                System.currentTimeMillis();
    }


    // =========================================
    // GET LAST ACTIVITY
    // =========================================

    public static long getLastActivityTime() {

        return lastActivityTime;
    }


    // =========================================
    // CHECK WHETHER SESSION EXISTS
    // =========================================

    public static boolean isLoggedIn() {

        return username != null
                && !username.trim().isEmpty();
    }


    // =========================================
    // CLEAR SESSION
    // =========================================

    public static void clearSession() {

        username = null;
        role = null;
        lastActivityTime = 0;
    }
}