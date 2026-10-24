package com.mycompany.userauthentication.dao;

import com.mycompany.userauthentication.DatabaseConnection;
import com.mycompany.userauthentication.PharmacyModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PharmacyDAO {

    /*
     * =========================================
     * DATABASE CONNECTION
     * =========================================
     */

    public Connection getConnection()
            throws Exception {

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
     * SAVE MEDICINE
     * =========================================
     */

    public void saveMedicine(
            PharmacyModel model)
            throws Exception {

        Connection con =
                getConnection();

        String sql =
                "INSERT INTO Pharmacy ("
                + "MedicineName,"
                + "Supplier,"
                + "Price,"
                + "Quantity,"
                + "ExpiryDate"
                + ") VALUES (?,?,?,?,?)";

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setString(
                1,
                model.getMedicineName());

        pst.setString(
                2,
                model.getSupplier());

        pst.setDouble(
                3,
                model.getPrice());

        pst.setInt(
                4,
                model.getQuantity());

        pst.setString(
                5,
                model.getExpiryDate());

        pst.executeUpdate();

        pst.close();
        con.close();
    }


    /*
     * =========================================
     * GET ALL MEDICINE
     * =========================================
     */

    public ResultSet getAllMedicine()
            throws Exception {

        Connection con =
                getConnection();

        String sql =
                "SELECT MedicineID,"
                + "MedicineName,"
                + "Supplier,"
                + "Price,"
                + "Quantity,"
                + "ExpiryDate "
                + "FROM Pharmacy "
                + "ORDER BY MedicineID";

        PreparedStatement pst =
                con.prepareStatement(sql);

        return pst.executeQuery();
    }


    /*
     * =========================================
     * UPDATE MEDICINE
     * =========================================
     */

    public void updateMedicine(
            PharmacyModel model)
            throws Exception {

        Connection con =
                getConnection();

        String sql =
                "UPDATE Pharmacy SET "
                + "MedicineName=?,"
                + "Supplier=?,"
                + "Price=?,"
                + "Quantity=?,"
                + "ExpiryDate=? "
                + "WHERE MedicineID=?";

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setString(
                1,
                model.getMedicineName());

        pst.setString(
                2,
                model.getSupplier());

        pst.setDouble(
                3,
                model.getPrice());

        pst.setInt(
                4,
                model.getQuantity());

        pst.setString(
                5,
                model.getExpiryDate());

        pst.setInt(
                6,
                model.getMedicineID());

        pst.executeUpdate();

        pst.close();
        con.close();
    }


    /*
     * =========================================
     * DELETE MEDICINE
     * =========================================
     */

    public void deleteMedicine(
            int id)
            throws Exception {

        Connection con =
                getConnection();

        String sql =
                "DELETE FROM Pharmacy "
                + "WHERE MedicineID=?";

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setInt(1, id);

        pst.executeUpdate();

        pst.close();
        con.close();
    }


    /*
     * =========================================
     * SEARCH MEDICINE
     * =========================================
     */

    public ResultSet searchMedicine(
            String keyword)
            throws Exception {

        Connection con =
                getConnection();

        String sql =
                "SELECT MedicineID,"
                + "MedicineName,"
                + "Supplier,"
                + "Price,"
                + "Quantity,"
                + "ExpiryDate "
                + "FROM Pharmacy "
                + "WHERE MedicineName LIKE ? "
                + "OR Supplier LIKE ? "
                + "ORDER BY MedicineID";

        PreparedStatement pst =
                con.prepareStatement(sql);

        String search =
                "%"
                + keyword
                + "%";

        pst.setString(
                1,
                search);

        pst.setString(
                2,
                search);

        return pst.executeQuery();
    }
}