package com.mycompany.userauthentication;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SMSService {

    public void sendSMS(
            int patientID,
            String phoneNumber,
            String message) {

        String status;

        boolean enabled = Boolean.parseBoolean(
                System.getenv("HMS_SMS_ENABLED"));

        if (!enabled) {
            status = "SMS_DISABLED";
        } else {
            try {
                status = AfricaTalkingSmsClient.sendSandbox(
                        phoneNumber, message);
            } catch (Exception e) {
                status = "SMS_FAILED";
                System.err.println(
                        "SMS provider request failed: "
                        + e.getMessage());
            }
        }

        String sql =
                "INSERT INTO SMSLogs "
                + "(PatientID, PhoneNumber, Message, Status) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, patientID);
            pst.setString(2, phoneNumber);
            pst.setString(3, message);
            pst.setString(4, status);
            pst.executeUpdate();

            System.out.println("SMS log saved. Status: " + status);

        } catch (Exception e) {
            System.err.println(
                    "Could not save SMS log: " + e.getMessage());
        }
    }
}
