package com.mycompany.userauthentication.dao;

import com.mycompany.userauthentication.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Joel Kayz
 */
public class DoctorDAO {

    /*
     * =========================================
     * DOCTOR CRUD
     * =========================================
     */

    public boolean addDoctor(
            int doctorID,
            String doctorName,
            String specialization,
            int phoneNumber,
            String emailAddress) throws SQLException {

        String sql =
                "INSERT INTO Doctors "
                + "(DoctorID, DoctorName, Specialization, "
                + "PhoneNumber, EmailAddress) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            if (con == null) {

                throw new SQLException(
                        "Database connection failed.");
            }

            pst.setInt(1, doctorID);
            pst.setString(2, doctorName);
            pst.setString(3, specialization);
            pst.setInt(4, phoneNumber);
            pst.setString(5, emailAddress);

            return pst.executeUpdate() > 0;
        }
    }


    public ResultSet getDoctorByID(int doctorID)
            throws SQLException {

        Connection con =
                DatabaseConnection.getConnection();

        if (con == null) {

            throw new SQLException(
                    "Database connection failed.");
        }

        String sql =
                "SELECT * FROM Doctors "
                + "WHERE DoctorID=?";

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setInt(1, doctorID);

        return pst.executeQuery();
    }


    public ResultSet getAllDoctors()
            throws SQLException {

        Connection con =
                DatabaseConnection.getConnection();

        if (con == null) {

            throw new SQLException(
                    "Database connection failed.");
        }

        String sql =
                "SELECT DoctorID, DoctorName, "
                + "Specialization, PhoneNumber, "
                + "EmailAddress "
                + "FROM Doctors "
                + "ORDER BY DoctorID";

        PreparedStatement pst =
                con.prepareStatement(sql);

        return pst.executeQuery();
    }


    public boolean updateDoctor(
            int doctorID,
            String doctorName,
            String specialization,
            int phoneNumber,
            String emailAddress)
            throws SQLException {

        String sql =
                "UPDATE Doctors SET "
                + "DoctorName=?, "
                + "Specialization=?, "
                + "PhoneNumber=?, "
                + "EmailAddress=? "
                + "WHERE DoctorID=?";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            if (con == null) {

                throw new SQLException(
                        "Database connection failed.");
            }

            pst.setString(1, doctorName);
            pst.setString(2, specialization);
            pst.setInt(3, phoneNumber);
            pst.setString(4, emailAddress);
            pst.setInt(5, doctorID);

            return pst.executeUpdate() > 0;
        }
    }


    public boolean deleteDoctor(int doctorID)
            throws SQLException {

        String sql =
                "DELETE FROM Doctors "
                + "WHERE DoctorID=?";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            if (con == null) {

                throw new SQLException(
                        "Database connection failed.");
            }

            pst.setInt(1, doctorID);

            return pst.executeUpdate() > 0;
        }
    }


    /*
     * =========================================
     * DASHBOARD / PERFORMANCE
     * =========================================
     */

    public int getTotalDoctors() {

        int count = 0;

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            if (con == null) {

                throw new SQLException(
                        "Database connection failed.");
            }

            String sql =
                    "SELECT COUNT(*) "
                    + "FROM Doctors";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            if (rs.next()) {

                count =
                        rs.getInt(1);
            }

            rs.close();
            pst.close();
            con.close();

        }

        catch (Exception e) {

            e.printStackTrace();
        }

        return count;
    }


    /*
     * =========================================
     * DOCTOR PERFORMANCE
     * =========================================
     *
     * Kept compatible with the current
     * DoctorPerformance.java caller.
     *
     * We will refactor this ResultSet-based
     * method during the dedicated resource
     * management phase.
     */

    public ResultSet getDoctorPerformance() {

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            if (con == null) {

                throw new SQLException(
                        "Database connection failed.");
            }

            String sql =
                    "SELECT d.DoctorName, "
                    + "d.Specialization, "
                    + "COUNT(p.PatientID) "
                    + "AS PatientsTreated "
                    + "FROM Doctors d "
                    + "LEFT JOIN Patients p "
                    + "ON d.DoctorName = p.Doctor "
                    + "GROUP BY d.DoctorName, "
                    + "d.Specialization "
                    + "ORDER BY PatientsTreated DESC";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            return pst.executeQuery();

        }

        catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}