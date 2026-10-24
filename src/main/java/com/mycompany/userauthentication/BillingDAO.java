package com.mycompany.userauthentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BillingDAO {

    public Connection getConnection() throws Exception {

        return DatabaseConnection.getConnection();

    }

    public void saveBilling(BillingModel model)
            throws Exception {

        Connection con = getConnection();

        String sql =
                "INSERT INTO Billing("
                + "PatientID,"
                + "Amount,"
                + "PaymentMethod,"
                + "PaymentDate"
                + ") VALUES(?,?,?,?)";

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setString(1, model.getPatientID());
        pst.setInt(2, model.getAmount());
        pst.setString(3, model.getPaymentMethod());
        pst.setString(4, model.getPaymentDate());

        pst.executeUpdate();

        pst.close();
        con.close();

    }

    public ResultSet getAllBillings()
            throws Exception {

        Connection con = getConnection();

        PreparedStatement pst =
                con.prepareStatement("SELECT * FROM Billing");

        return pst.executeQuery();

    }

    public void updateBilling(BillingModel model)
            throws Exception {

        Connection con = getConnection();

        String sql =
                "UPDATE Billing SET "
                + "PatientID=?,"
                + "Amount=?,"
                + "PaymentMethod=?,"
                + "PaymentDate=? "
                + "WHERE BillingID=?";

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setString(1, model.getPatientID());
        pst.setInt(2, model.getAmount());
        pst.setString(3, model.getPaymentMethod());
        pst.setString(4, model.getPaymentDate());
        pst.setInt(5, model.getBillingID());

        pst.executeUpdate();

        pst.close();
        con.close();

    }

    public void deleteBilling(int billingID)
            throws Exception {

        Connection con = getConnection();

        PreparedStatement pst =
                con.prepareStatement(
                        "DELETE FROM Billing WHERE BillingID=?");

        pst.setInt(1, billingID);

        pst.executeUpdate();

        pst.close();
        con.close();

    }

    public ResultSet searchBilling(String keyword)
            throws Exception {

        Connection con = getConnection();

        PreparedStatement pst =
                con.prepareStatement(
                        "SELECT * FROM Billing "
                        + "WHERE PatientID LIKE ? "
                        + "OR PaymentMethod LIKE ?");

        String search = "%" + keyword + "%";

        pst.setString(1, search);
        pst.setString(2, search);

        return pst.executeQuery();

    }

}