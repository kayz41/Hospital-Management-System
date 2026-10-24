package com.mycompany.userauthentication.dao;

import com.mycompany.userauthentication.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BillingDAO {

    public double getTotalRevenue() {

        double totalRevenue = 0;

        try {

            Connection con = DatabaseConnection.getConnection();

            String sql = "SELECT SUM(Amount) FROM Billing";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                totalRevenue = rs.getDouble(1);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return totalRevenue;

    }

    public ResultSet getRevenueData() {

        try {

            Connection con = DatabaseConnection.getConnection();

            String sql =
                    "SELECT BillingID, PatientID, Amount, PaymentMethod, PaymentDate " +
                    "FROM Billing ORDER BY BillingID DESC";

            PreparedStatement pst = con.prepareStatement(sql);

            return pst.executeQuery();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

}