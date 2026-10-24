package com.mycompany.userauthentication.dao;

import com.mycompany.userauthentication.DatabaseConnection;
import com.mycompany.userauthentication.LaboratoryModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;

/**
 *
 * @author Joel Kayz
 */
public class LaboratoryDAO {

    /*
     * =========================================
     * DATABASE CONNECTION
     * =========================================
     */

    public Connection getConnection() throws Exception {

        Connection con =
                DatabaseConnection.getConnection();

        if (con == null) {

            throw new Exception(
                    "Database connection failed.");
        }

        return con;
    }


    /*
     * =========================================
     * LOAD PATIENTS
     * =========================================
     */

    public void loadPatients(
            JComboBox<String> cmbPatient) {

        try {

            Connection con =
                    getConnection();

            String sql =
                    "SELECT PatientID, PatientName "
                    + "FROM Patients "
                    + "ORDER BY PatientName";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            cmbPatient.removeAllItems();

            while (rs.next()) {

                cmbPatient.addItem(

                        rs.getInt("PatientID")
                        + " - "
                        + rs.getString("PatientName")
                );
            }

            rs.close();
            pst.close();
            con.close();

        }

        catch (Exception e) {

            e.printStackTrace();
        }
    }


    /*
     * =========================================
     * LOAD DOCTORS
     * =========================================
     */

    public void loadDoctors(
            JComboBox<String> cmbDoctor) {

        try {

            Connection con =
                    getConnection();

            String sql =
                    "SELECT DoctorID, DoctorName "
                    + "FROM Doctors "
                    + "ORDER BY DoctorName";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            cmbDoctor.removeAllItems();

            while (rs.next()) {

                cmbDoctor.addItem(

                        rs.getInt("DoctorID")
                        + " - "
                        + rs.getString("DoctorName")
                );
            }

            rs.close();
            pst.close();
            con.close();

        }

        catch (Exception e) {

            e.printStackTrace();
        }
    }


    /*
     * =========================================
     * SAVE LABORATORY RECORD
     * =========================================
     */

    public void saveLaboratoryRecord(
            LaboratoryModel model)
            throws Exception {

        Connection con =
                getConnection();

        String sql =
                "INSERT INTO Laboratory ("
                + "PatientID, "
                + "PatientName, "
                + "DoctorID, "
                + "DoctorName, "
                + "TestName, "
                + "Result, "
                + "TestDate"
                + ") VALUES (?,?,?,?,?,?,?)";

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setInt(
                1,
                model.getPatientID());

        pst.setString(
                2,
                model.getPatientName());

        pst.setInt(
                3,
                model.getDoctorID());

        pst.setString(
                4,
                model.getDoctorName());

        pst.setString(
                5,
                model.getTestName());

        pst.setString(
                6,
                model.getResult());

        pst.setString(
                7,
                model.getTestDate());

        pst.executeUpdate();

        pst.close();
        con.close();
    }


    /*
     * =========================================
     * DISPLAY ALL RECORDS
     * =========================================
     */

    public ResultSet getAllLaboratoryRecords()
            throws Exception {

        Connection con =
                getConnection();

        String sql =
                "SELECT TestID, "
                + "PatientID, "
                + "PatientName, "
                + "DoctorID, "
                + "DoctorName, "
                + "TestName, "
                + "Result, "
                + "TestDate "
                + "FROM Laboratory "
                + "ORDER BY TestID";

        PreparedStatement pst =
                con.prepareStatement(sql);

        return pst.executeQuery();
    }


    /*
     * =========================================
     * UPDATE LABORATORY RECORD
     * =========================================
     */

    public void updateLaboratoryRecord(
            LaboratoryModel model)
            throws Exception {

        Connection con =
                getConnection();

        String sql =
                "UPDATE Laboratory SET "
                + "PatientID=?, "
                + "PatientName=?, "
                + "DoctorID=?, "
                + "DoctorName=?, "
                + "TestName=?, "
                + "Result=?, "
                + "TestDate=? "
                + "WHERE TestID=?";

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setInt(
                1,
                model.getPatientID());

        pst.setString(
                2,
                model.getPatientName());

        pst.setInt(
                3,
                model.getDoctorID());

        pst.setString(
                4,
                model.getDoctorName());

        pst.setString(
                5,
                model.getTestName());

        pst.setString(
                6,
                model.getResult());

        pst.setString(
                7,
                model.getTestDate());

        pst.setInt(
                8,
                model.getTestID());

        pst.executeUpdate();

        pst.close();
        con.close();
    }


    /*
     * =========================================
     * DELETE LABORATORY RECORD
     * =========================================
     */

    public void deleteLaboratoryRecord(
            int testID)
            throws Exception {

        Connection con =
                getConnection();

        String sql =
                "DELETE FROM Laboratory "
                + "WHERE TestID=?";

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setInt(
                1,
                testID);

        pst.executeUpdate();

        pst.close();
        con.close();
    }


    /*
     * =========================================
     * SEARCH LABORATORY RECORD
     * =========================================
     */

    public ResultSet searchLaboratoryRecord(
            String keyword)
            throws Exception {

        Connection con =
                getConnection();

        String sql =
                "SELECT TestID, "
                + "PatientID, "
                + "PatientName, "
                + "DoctorID, "
                + "DoctorName, "
                + "TestName, "
                + "Result, "
                + "TestDate "
                + "FROM Laboratory "
                + "WHERE PatientName LIKE ? "
                + "OR DoctorName LIKE ? "
                + "OR TestName LIKE ? "
                + "ORDER BY TestID";

        PreparedStatement pst =
                con.prepareStatement(sql);

        String search =
                "%"
                + keyword
                + "%";

        pst.setString(1, search);
        pst.setString(2, search);
        pst.setString(3, search);

        return pst.executeQuery();
    }
}