package com.mycompany.userauthentication.dao;

import com.mycompany.userauthentication.DatabaseConnection;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Joel Kayz
 */
public class SMSDAO {

    public void loadSMSHistory(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        try {
            Connection con = DatabaseConnection.getConnection();

            String sql = "SELECT SMSID, PatientID, PhoneNumber, Message, Status, DateSent FROM SMSLogs ORDER BY DateSent DESC";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                    rs.getInt("SMSID"),
                    rs.getInt("PatientID"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Message"),
                    rs.getString("Status"),
                    rs.getTimestamp("DateSent")
                });
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.println("SMS History Error: " + e.getMessage());
        }
    }

    public void searchSMSHistory(JTable table, int patientID) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        try {

            Connection con = DatabaseConnection.getConnection();

            String sql = "SELECT SMSID, PatientID, PhoneNumber, Message, Status, DateSent "
                       + "FROM SMSLogs WHERE PatientID = ? ORDER BY DateSent DESC";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, patientID);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                    rs.getInt("SMSID"),
                    rs.getInt("PatientID"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Message"),
                    rs.getString("Status"),
                    rs.getTimestamp("DateSent")
                });

            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    
    
    
    
    public int getTotalSMS() {

    int total = 0;

    try {

        Connection con = DatabaseConnection.getConnection();

        String sql = "SELECT COUNT(*) FROM SMSLogs";

        PreparedStatement pst = con.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {

            total = rs.getInt(1);

        }

        rs.close();
        pst.close();
        con.close();

    } catch (Exception e) {

        e.printStackTrace();

    }

    return total;

}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}