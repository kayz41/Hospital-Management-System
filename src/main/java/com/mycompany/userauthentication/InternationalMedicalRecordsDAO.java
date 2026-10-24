package com.mycompany.userauthentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import java.sql.ResultSet;

public class InternationalMedicalRecordsDAO {

    public Connection getConnection() throws Exception {

        return DatabaseConnection.getConnection();

    }

    //=====================================================
    // Load Patients
    //=====================================================

    public void loadPatients(JComboBox<String> cmbPatient) {

        try {

            Connection con = getConnection();

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

    //=====================================================
    // Load Doctors
    //=====================================================

    public void loadDoctors(JComboBox<String> cmbDoctor) {

        try {

            Connection con = getConnection();

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

    
    
    
    public ResultSet getAllMedicalRecords() throws Exception {

    Connection con = getConnection();

    String sql =
            "SELECT * FROM InternationalMedicalRecords ORDER BY RecordID";

    PreparedStatement pst =
            con.prepareStatement(sql);

    return pst.executeQuery();
}
    
    
    
    
    public void updateMedicalRecord(InternationalMedicalRecordsModel model)
        throws Exception {

    Connection con = getConnection();

    String sql =
            "UPDATE InternationalMedicalRecords SET "
            + "PatientID=?,"
            + "PatientName=?,"
            + "DoctorID=?,"
            + "DoctorName=?,"
            + "Diagnosis=?,"
            + "Treatment=?,"
            + "Prescription=?,"
            + "MedicalHistory=?,"
            + "Allergies=?,"
            + "VisitDate=?,"
            + "VisitTime=?,"
            + "BloodPressure=?,"
            + "Temperature=?,"
            + "HeartRate=?,"
            + "Weight=?,"
            + "Height=?,"
            + "ClinicalNotes=?,"
            + "Status=? "
            + "WHERE RecordID=?";

    PreparedStatement pst = con.prepareStatement(sql);

    pst.setInt(1, model.getPatientID());
    pst.setString(2, model.getPatientName());

    pst.setInt(3, model.getDoctorID());
    pst.setString(4, model.getDoctorName());

    pst.setString(5, model.getDiagnosis());
    pst.setString(6, model.getTreatment());
    pst.setString(7, model.getPrescription());
    pst.setString(8, model.getMedicalHistory());
    pst.setString(9, model.getAllergies());

    pst.setString(10, model.getVisitDate());
    pst.setString(11, model.getVisitTime());

    pst.setString(12, model.getBloodPressure());
    pst.setString(13, model.getTemperature());
    pst.setString(14, model.getHeartRate());

    pst.setString(15, model.getWeight());
    pst.setString(16, model.getHeight());

    pst.setString(17, model.getClinicalNotes());
    pst.setString(18, model.getStatus());

    pst.setInt(19, model.getRecordID());

    pst.executeUpdate();

    pst.close();
    con.close();
}
    
    
    
    
    
    
    
    
    public void deleteMedicalRecord(int recordID)
        throws Exception {

    Connection con = getConnection();

    String sql =
            "DELETE FROM InternationalMedicalRecords "
            + "WHERE RecordID=?";

    PreparedStatement pst =
            con.prepareStatement(sql);

    pst.setInt(1, recordID);

    pst.executeUpdate();

    pst.close();
    con.close();
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //=====================================================
// Save Medical Record
//=====================================================

public void saveMedicalRecord(InternationalMedicalRecordsModel model)
        throws Exception {

    Connection con = getConnection();

    String sql =
    "INSERT INTO InternationalMedicalRecords ("
    + "PatientID,"
            + "PatientName,"
            + "DoctorID,"
            + "DoctorName,"
            + "Diagnosis,"
            + "Treatment,"
            + "Prescription,"
            + "MedicalHistory,"
            + "Allergies,"
            + "VisitDate,"
            + "VisitTime,"
            + "BloodPressure,"
            + "Temperature,"
            + "HeartRate,"
            + "Weight,"
            + "Height,"
            + "ClinicalNotes,"
            + "Status"
            + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    PreparedStatement pst =
            con.prepareStatement(sql);
    
    
pst.setInt(1, model.getPatientID());
pst.setString(2, model.getPatientName());

pst.setInt(3, model.getDoctorID());
pst.setString(4, model.getDoctorName());

pst.setString(5, model.getDiagnosis());
pst.setString(6, model.getTreatment());
pst.setString(7, model.getPrescription());
pst.setString(8, model.getMedicalHistory());
pst.setString(9, model.getAllergies());

pst.setString(10, model.getVisitDate());
pst.setString(11, model.getVisitTime());

pst.setString(12, model.getBloodPressure());
pst.setString(13, model.getTemperature());
pst.setString(14, model.getHeartRate());

pst.setString(15, model.getWeight());
pst.setString(16, model.getHeight());

pst.setString(17, model.getClinicalNotes());
pst.setString(18, model.getStatus());
pst.executeUpdate();

    // We will fill these parameters in the next step.

    pst.close();
    con.close();

}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}