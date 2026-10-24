package com.mycompany.userauthentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PharmacyDispensingDAO {

    // ==========================================
    // SAVE / DISPENSE MEDICINE
    // ==========================================

   public void saveDispensing(
        PharmacyDispensingModel model)
        throws Exception {

    Connection con =
            DatabaseConnection.getConnection();

    try {

        con.setAutoCommit(false);


        // ==================================
        // CHECK DUPLICATE DISPENSING
        // ==================================

        String duplicateSQL =
                "SELECT COUNT(*) "
              + "FROM PharmacyDispensing "
              + "WHERE RecordID=? "
              + "AND MedicineID=?";


        PreparedStatement duplicatePst =
                con.prepareStatement(duplicateSQL);


        duplicatePst.setInt(
                1,
                model.getRecordID());

        duplicatePst.setInt(
                2,
                model.getMedicineID());


        ResultSet duplicateRs =
                duplicatePst.executeQuery();


        if (duplicateRs.next()) {

            int count =
                    duplicateRs.getInt(1);

            if (count > 0) {

                duplicateRs.close();
                duplicatePst.close();

                throw new Exception(
                        "This medicine has already "
                      + "been dispensed for this "
                      + "Medical Record.");
            }
        }


        duplicateRs.close();
        duplicatePst.close();


        // ==================================
        // CHECK STOCK
        // ==================================

        String stockSQL =
                "SELECT Quantity "
              + "FROM Pharmacy "
              + "WHERE MedicineID=?";


        PreparedStatement stockPst =
                con.prepareStatement(stockSQL);


        stockPst.setInt(
                1,
                model.getMedicineID());


        ResultSet stockRs =
                stockPst.executeQuery();


        if (!stockRs.next()) {

            stockRs.close();
            stockPst.close();

            throw new Exception(
                    "Medicine not found in Pharmacy.");
        }


        int currentStock =
                stockRs.getInt("Quantity");


        if (currentStock
                < model.getQuantity()) {

            stockRs.close();
            stockPst.close();

            throw new Exception(
                    "Insufficient stock available. "
                  + "Current stock: "
                  + currentStock);
        }


        stockRs.close();
        stockPst.close();


        // ==================================
        // INSERT DISPENSING RECORD
        // ==================================

        String insertSQL =
                "INSERT INTO PharmacyDispensing ("
              + "PatientID,"
              + "PatientName,"
              + "DoctorID,"
              + "DoctorName,"
              + "MedicineID,"
              + "MedicineName,"
              + "Quantity,"
              + "DispensingDate,"
              + "Status,"
              + "RecordID"
              + ") VALUES (?,?,?,?,?,?,?,?,?,?)";


        PreparedStatement insertPst =
                con.prepareStatement(insertSQL);


        insertPst.setInt(
                1,
                model.getPatientID());

        insertPst.setString(
                2,
                model.getPatientName());

        insertPst.setInt(
                3,
                model.getDoctorID());

        insertPst.setString(
                4,
                model.getDoctorName());

        insertPst.setInt(
                5,
                model.getMedicineID());

        insertPst.setString(
                6,
                model.getMedicineName());

        insertPst.setInt(
                7,
                model.getQuantity());

        insertPst.setString(
                8,
                model.getDispensingDate());

        insertPst.setString(
                9,
                model.getStatus());


        // ==================================
        // SAVE MEDICAL RECORD ID
        // ==================================

        if (model.getRecordID() > 0) {

            insertPst.setInt(
                    10,
                    model.getRecordID());

        } else {

            insertPst.setNull(
                    10,
                    java.sql.Types.INTEGER);
        }


        insertPst.executeUpdate();

        insertPst.close();


        // ==================================
        // REDUCE PHARMACY STOCK
        // ==================================

        String updateStockSQL =
                "UPDATE Pharmacy "
              + "SET Quantity = Quantity - ? "
              + "WHERE MedicineID=?";


        PreparedStatement stockUpdatePst =
                con.prepareStatement(
                        updateStockSQL);


        stockUpdatePst.setInt(
                1,
                model.getQuantity());

        stockUpdatePst.setInt(
                2,
                model.getMedicineID());


        stockUpdatePst.executeUpdate();

        stockUpdatePst.close();


        // ==================================
        // COMMIT TRANSACTION
        // ==================================

        con.commit();

    }

    catch (Exception e) {

        con.rollback();

        throw e;

    }

    finally {

        con.setAutoCommit(true);

        con.close();

    }
}
   // ==========================================
// GET ALL DISPENSING RECORDS
// ==========================================

public ResultSet getAllDispensing()
        throws Exception {

    Connection con =
            DatabaseConnection.getConnection();

    String sql =
            "SELECT "
          + "DispensingID, "
          + "PatientID, "
          + "PatientName, "
          + "DoctorID, "
          + "DoctorName, "
          + "MedicineID, "
          + "MedicineName, "
          + "Quantity, "
          + "DispensingDate, "
          + "Status, "
          + "RecordID "
          + "FROM PharmacyDispensing "
          + "ORDER BY DispensingID DESC";

    PreparedStatement pst =
            con.prepareStatement(sql);

    return pst.executeQuery();
}


    // ==========================================
    // SEARCH
    // ==========================================

    public ResultSet searchDispensing(
            String dispensingID)
            throws Exception {

        Connection con =
                DatabaseConnection.getConnection();

        String sql =
                "SELECT * "
              + "FROM PharmacyDispensing "
              + "WHERE DispensingID=?";

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setString(
                1,
                dispensingID);
        
       

        return pst.executeQuery();
    }


    // ==========================================
    // DELETE
    // ==========================================

   public void deleteDispensing(
        int dispensingID)
        throws Exception {

    Connection con =
            DatabaseConnection.getConnection();

    try {

        con.setAutoCommit(false);


        // ==================================
        // GET DISPENSING INFORMATION
        // ==================================

        String selectSQL =
                "SELECT MedicineID, Quantity "
              + "FROM PharmacyDispensing "
              + "WHERE DispensingID=?";


        PreparedStatement selectPst =
                con.prepareStatement(selectSQL);

        selectPst.setInt(
                1,
                dispensingID);


        ResultSet rs =
                selectPst.executeQuery();


        if (!rs.next()) {

            throw new Exception(
                    "Dispensing record not found.");

        }


        int medicineID =
                rs.getInt("MedicineID");


        int quantity =
                rs.getInt("Quantity");


        rs.close();
        selectPst.close();


        // ==================================
        // DELETE DISPENSING RECORD
        // ==================================

        String deleteSQL =
                "DELETE FROM PharmacyDispensing "
              + "WHERE DispensingID=?";


        PreparedStatement deletePst =
                con.prepareStatement(deleteSQL);


        deletePst.setInt(
                1,
                dispensingID);


        deletePst.executeUpdate();

        deletePst.close();


        // ==================================
        // RETURN STOCK
        // ==================================

        String stockSQL =
                "UPDATE Pharmacy "
              + "SET Quantity = Quantity + ? "
              + "WHERE MedicineID=?";


        PreparedStatement stockPst =
                con.prepareStatement(stockSQL);


        stockPst.setInt(
                1,
                quantity);

        stockPst.setInt(
                2,
                medicineID);


        stockPst.executeUpdate();

        stockPst.close();


        // ==================================
        // COMMIT
        // ==================================

        con.commit();

    }

    catch (Exception e) {

        con.rollback();

        throw e;

    }

    finally {

        con.setAutoCommit(true);

        con.close();
    }
   }
   
   
   
   
   
}