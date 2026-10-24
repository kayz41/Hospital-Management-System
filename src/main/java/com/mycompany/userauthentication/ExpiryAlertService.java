package com.mycompany.userauthentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ExpiryAlertService {

    private final NotificationManager notificationManager;


    public ExpiryAlertService() {

        notificationManager =
                new NotificationManager();
    }


    public void checkExpiryAlerts(
            String recipientEmail)
            throws Exception {


        if (recipientEmail == null
                || recipientEmail.trim().isEmpty()) {

            System.out.println(
                    "Expiry alert skipped: "
                  + "recipient email is not configured.");

            return;
        }


        Connection con =
                DatabaseConnection.getConnection();


        try {

            String sql =
                    "SELECT MedicineID, MedicineName, "
                  + "ExpiryDate, Quantity "
                  + "FROM Pharmacy "
                  + "WHERE ExpiryDate IS NOT NULL "
                  + "AND ExpiryDate <= DATEADD(DAY, 30, "
                  + "CAST(GETDATE() AS DATE)) "
                  + "ORDER BY ExpiryDate ASC";


            PreparedStatement pst =
                    con.prepareStatement(sql);


            ResultSet rs =
                    pst.executeQuery();


            StringBuilder alertMessage =
                    new StringBuilder();


            StringBuilder medicinesToRecord =
                    new StringBuilder();


            boolean alertFound =
                    false;


            while (rs.next()) {

                int medicineID =
                        rs.getInt("MedicineID");


                String medicineName =
                        rs.getString(
                                "MedicineName");


                LocalDate expiryDate =
                        rs.getDate("ExpiryDate")
                                .toLocalDate();


                int quantity =
                        rs.getInt("Quantity");


                LocalDate today =
                        LocalDate.now();


                long daysRemaining =
                        ChronoUnit.DAYS.between(
                                today,
                                expiryDate);


                String alertType;


                if (daysRemaining < 0) {

                    alertType =
                            "EXPIRED";

                }

                else if (daysRemaining == 0) {

                    alertType =
                            "EXPIRY_TODAY";

                }

                else {

                    alertType =
                            "EXPIRY_30_DAYS";
                }


                /*
                 * Check whether this exact
                 * alert has already been sent.
                 */

                if (alertAlreadySent(
                        con,
                        medicineID,
                        expiryDate,
                        alertType)) {

                    continue;
                }


                alertFound = true;


                /*
                 * Build the notification.
                 */

                alertMessage.append(
                        "Medicine ID: ")
                        .append(medicineID)
                        .append("\n");


                alertMessage.append(
                        "Medicine Name: ")
                        .append(medicineName)
                        .append("\n");


                alertMessage.append(
                        "Quantity: ")
                        .append(quantity)
                        .append("\n");


                alertMessage.append(
                        "Expiry Date: ")
                        .append(expiryDate)
                        .append("\n");


                if (daysRemaining < 0) {

                    alertMessage.append(
                            "Status: EXPIRED\n");

                }

                else if (daysRemaining == 0) {

                    alertMessage.append(
                            "Status: EXPIRES TODAY\n");

                }

                else {

                    alertMessage.append(
                            "Days Remaining: ")
                            .append(daysRemaining)
                            .append("\n");
                }


                alertMessage.append(
                        "Alert Type: ")
                        .append(alertType)
                        .append("\n");


                alertMessage.append(
                        "--------------------------------"
                      + "--------\n");


                /*
                 * Keep the medicine information
                 * temporarily.
                 *
                 * We record it ONLY after the
                 * notification succeeds.
                 */

                medicinesToRecord
                        .append(medicineID)
                        .append("|")
                        .append(expiryDate)
                        .append("|")
                        .append(alertType)
                        .append("\n");
            }


            rs.close();
            pst.close();


            /*
             * ==================================
             * SEND EMAIL
             * ==================================
             */

            if (!alertFound) {

                System.out.println(
                        "No new pharmacy expiry "
                      + "alerts found.");

                return;
            }


            String subject =
                    "Hospital Pharmacy Expiry Alert";


            String message =
                    "PHARMACY EXPIRY ALERT\n\n"
                  + "The following medicines "
                  + "are expired or will expire "
                  + "within 30 days:\n\n"
                  + alertMessage.toString();


            /*
             * The notification must succeed
             * before we record the alert.
             */

         boolean notificationSent =
        notificationManager.sendNotification(
                recipientEmail,
                subject,
                message);


if (!notificationSent) {

    throw new Exception(
            "Pharmacy expiry alert could not be delivered "
          + "through any enabled notification channel.");
}


            /*
             * ==================================
             * RECORD SUCCESSFUL ALERTS
             * ==================================
             */

            String[] records =
                    medicinesToRecord
                            .toString()
                            .split("\n");


            for (String record : records) {

                if (record.trim().isEmpty()) {

                    continue;
                }


                String[] parts =
                        record.split("\\|");


                int medicineID =
                        Integer.parseInt(parts[0]);


                LocalDate expiryDate =
                        LocalDate.parse(parts[1]);


                String alertType =
                        parts[2];


                recordAlert(
                        con,
                        medicineID,
                        expiryDate,
                        alertType);
            }


            System.out.println(
                    "Pharmacy expiry notification "
                  + "sent and alert records saved.");

        }

        finally {

            con.close();
        }
    }


    /*
     * ==========================================
     * CHECK EXISTING ALERT
     * ==========================================
     */

    private boolean alertAlreadySent(
            Connection con,
            int medicineID,
            LocalDate expiryDate,
            String alertType)
            throws Exception {


        String sql =
                "SELECT COUNT(*) "
              + "FROM PharmacyExpiryAlerts "
              + "WHERE MedicineID=? "
              + "AND ExpiryDate=? "
              + "AND AlertType=?";


        PreparedStatement pst =
                con.prepareStatement(sql);


        pst.setInt(
                1,
                medicineID);


        pst.setDate(
                2,
                java.sql.Date.valueOf(
                        expiryDate));


        pst.setString(
                3,
                alertType);


        ResultSet rs =
                pst.executeQuery();


        boolean exists = false;


        if (rs.next()) {

            exists =
                    rs.getInt(1) > 0;
        }


        rs.close();
        pst.close();


        return exists;
    }


    /*
     * ==========================================
     * RECORD SUCCESSFUL ALERT
     * ==========================================
     */

    private void recordAlert(
            Connection con,
            int medicineID,
            LocalDate expiryDate,
            String alertType)
            throws Exception {


        String sql =
                "INSERT INTO PharmacyExpiryAlerts "
              + "(MedicineID, ExpiryDate, AlertType) "
              + "VALUES (?,?,?)";


        PreparedStatement pst =
                con.prepareStatement(sql);


        pst.setInt(
                1,
                medicineID);


        pst.setDate(
                2,
                java.sql.Date.valueOf(
                        expiryDate));


        pst.setString(
                3,
                alertType);


        pst.executeUpdate();


        pst.close();
    }
}