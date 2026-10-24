package com.mycompany.userauthentication;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AuditLogger {

    public static void log(String action) {

        String sql =
                "INSERT INTO AuditTrail "
                + "(Username, Role, ActionPerformed) "
                + "VALUES (?, ?, ?)";

        try (
            Connection con =
                    DatabaseConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql)
        ) {

            pst.setString(
                    1,
                    UserSession.username);

            pst.setString(
                    2,
                    UserSession.role);

            pst.setString(
                    3,
                    action);

            pst.executeUpdate();

        }

        catch (Exception e) {

            System.err.println(
                    "Audit logging failed: "
                    + e.getMessage());

            e.printStackTrace();
        }
    }
}