/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.userauthentication;
import com.mycompany.userauthentication.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Joel Kayz
 */

public class InternationalMedicalRecords extends javax.swing.JFrame {
    
    private final InternationalMedicalRecordsDAO dao = new InternationalMedicalRecordsDAO();
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(InternationalMedicalRecords.class.getName());

    
    String patientName="";
String doctorName="";
    
    
    
    /**
     * Creates new form InternationalMedicalRecords
     */
  public InternationalMedicalRecords() {

    initComponents();

    loadPatients();

    loadDoctors();

    loadMedicalRecords();

}
    
    
    
    
    
    private void loadMedicalRecords() {

    try {

        DefaultTableModel model =
                (DefaultTableModel) tblMedicalRecords.getModel();

        model.setRowCount(0);

        ResultSet rs =
                dao.getAllMedicalRecords();

        while (rs.next()) {

            model.addRow(new Object[]{

                rs.getInt("RecordID"),
                rs.getInt("PatientID"),
                rs.getString("PatientName"),
                rs.getInt("DoctorID"),
                rs.getString("DoctorName"),
                rs.getString("Diagnosis"),
                rs.getString("Treatment"),
                rs.getString("Prescription"),
                rs.getString("MedicalHistory"),
                rs.getString("Allergies"),
                rs.getString("VisitDate"),
                rs.getString("VisitTime"),
                rs.getString("BloodPressure"),
                rs.getString("Temperature"),
                rs.getString("HeartRate"),
                rs.getString("Weight"),
                rs.getString("Height"),
                rs.getString("ClinicalNotes"),
                rs.getString("Status")

            });

        }

        rs.getStatement().getConnection().close();

    }

    catch (Exception e) {

        e.printStackTrace();

    }

}
    
    
    
    
    
    private void clearFields() {

    txtRecordID.setText("");

    if (cmbPatient.getItemCount() > 0)
        cmbPatient.setSelectedIndex(0);

    if (cmbDoctor.getItemCount() > 0)
        cmbDoctor.setSelectedIndex(0);

    txtDiagnosis.setText("");
    txtTreatment.setText("");
    txtPrescription.setText("");
    txtMedicalHistory.setText("");
    txtAllergies.setText("");

    txtVisitDate.setText("");
    txtVisitTime.setText("");

    txtBloodPressure.setText("");
    txtTemperature.setText("");
    txtHeartRate.setText("");

    txtWeight.setText("");
    txtHeight.setText("");

    txtClinicalNotes.setText("");

    if (cmbStatus.getItemCount() > 0)
        cmbStatus.setSelectedIndex(0);

}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void loadPatients() {

    cmbPatient.removeAllItems();

    try {

        Connection con = DatabaseConnection.getConnection();

        String sql =
                "SELECT PatientID, PatientName "
                + "FROM Patients "
                + "ORDER BY PatientName";

        PreparedStatement pst =
                con.prepareStatement(sql);

        ResultSet rs =
                pst.executeQuery();

        while (rs.next()) {

            cmbPatient.addItem(

                    rs.getInt("PatientID")
                    + " - "
                    + rs.getString("PatientName"));

        }

        rs.close();
        pst.close();
        con.close();

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    
    
    
    private void loadDoctors() {

    cmbDoctor.removeAllItems();

    try {

        Connection con = DatabaseConnection.getConnection();

        String sql =
                "SELECT DoctorID, DoctorName "
                + "FROM Doctors "
                + "ORDER BY DoctorName";

        PreparedStatement pst =
                con.prepareStatement(sql);

        ResultSet rs =
                pst.executeQuery();

        while (rs.next()) {

            cmbDoctor.addItem(

                    rs.getInt("DoctorID")
                    + " - "
                    + rs.getString("DoctorName"));

        }

        rs.close();
        pst.close();
        con.close();

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    
    
    
    /**
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          

   


     /*
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlInternationalMedicalRecords = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnSAVE = new javax.swing.JButton();
        btnUPDATE = new javax.swing.JButton();
        btnDELETE = new javax.swing.JButton();
        btnSEARCH = new javax.swing.JButton();
        btnDISPLAY = new javax.swing.JButton();
        btnCLEAR = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtRecordID = new javax.swing.JTextField();
        cmbPatient = new javax.swing.JComboBox<>();
        cmbDoctor = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiagnosis = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtTreatment = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtPrescription = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtMedicalHistory = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAllergies = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblMedicalRecords = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtVisitTime = new javax.swing.JTextField();
        txtHeartRate = new javax.swing.JTextField();
        txtBloodPressure = new javax.swing.JTextField();
        txtWeight = new javax.swing.JTextField();
        txtVisitDate = new javax.swing.JTextField();
        txtTemperature = new javax.swing.JTextField();
        txtHeight = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<>();
        txtClinicalNotess = new javax.swing.JScrollPane();
        txtClinicalNotes = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlInternationalMedicalRecords.setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("INTERNATIONAL MEDICAL RECORDS MANAGEMENT");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(264, 264, 264))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(35, 35, 35))
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        btnSAVE.setBackground(new java.awt.Color(102, 102, 102));
        btnSAVE.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSAVE.setForeground(new java.awt.Color(255, 255, 255));
        btnSAVE.setText("SAVE");
        btnSAVE.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSAVE.setFocusPainted(false);
        btnSAVE.addActionListener(this::btnSAVEActionPerformed);

        btnUPDATE.setBackground(new java.awt.Color(102, 102, 102));
        btnUPDATE.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUPDATE.setForeground(new java.awt.Color(255, 255, 255));
        btnUPDATE.setText("UPDATE");
        btnUPDATE.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnUPDATE.setFocusPainted(false);
        btnUPDATE.addActionListener(this::btnUPDATEActionPerformed);

        btnDELETE.setBackground(new java.awt.Color(102, 102, 102));
        btnDELETE.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDELETE.setForeground(new java.awt.Color(255, 255, 255));
        btnDELETE.setText("DELETE");
        btnDELETE.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnDELETE.setFocusPainted(false);
        btnDELETE.addActionListener(this::btnDELETEActionPerformed);

        btnSEARCH.setBackground(new java.awt.Color(102, 102, 102));
        btnSEARCH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSEARCH.setForeground(new java.awt.Color(255, 255, 255));
        btnSEARCH.setText("SEARCH");
        btnSEARCH.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSEARCH.setFocusPainted(false);
        btnSEARCH.addActionListener(this::btnSEARCHActionPerformed);

        btnDISPLAY.setBackground(new java.awt.Color(102, 102, 102));
        btnDISPLAY.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDISPLAY.setForeground(new java.awt.Color(255, 255, 255));
        btnDISPLAY.setText("DISPLAY");
        btnDISPLAY.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnDISPLAY.setFocusPainted(false);
        btnDISPLAY.addActionListener(this::btnDISPLAYActionPerformed);

        btnCLEAR.setBackground(new java.awt.Color(102, 102, 102));
        btnCLEAR.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCLEAR.setForeground(new java.awt.Color(255, 255, 255));
        btnCLEAR.setText("CLEAR");
        btnCLEAR.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnCLEAR.setFocusPainted(false);
        btnCLEAR.addActionListener(this::btnCLEARActionPerformed);

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("DASHBOARD");
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnSAVE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(48, 48, 48)
                .addComponent(btnUPDATE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(50, 50, 50)
                .addComponent(btnDELETE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(59, 59, 59)
                .addComponent(btnSEARCH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(64, 64, 64)
                .addComponent(btnDISPLAY, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(64, 64, 64)
                .addComponent(btnCLEAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(54, 54, 54)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(37, 37, 37))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCLEAR)
                    .addComponent(btnDISPLAY)
                    .addComponent(btnSEARCH)
                    .addComponent(btnDELETE)
                    .addComponent(btnUPDATE)
                    .addComponent(btnSAVE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCLEAR, btnDELETE, btnDISPLAY, btnSAVE, btnSEARCH, btnUPDATE});

        txtRecordID.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cmbPatient.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbPatient.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cmbPatient.addActionListener(this::cmbPatientActionPerformed);

        cmbDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbDoctor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cmbDoctor.addActionListener(this::cmbDoctorActionPerformed);

        txtDiagnosis.setColumns(20);
        txtDiagnosis.setRows(5);
        txtDiagnosis.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setViewportView(txtDiagnosis);

        txtTreatment.setColumns(20);
        txtTreatment.setRows(5);
        txtTreatment.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane2.setViewportView(txtTreatment);

        txtPrescription.setColumns(20);
        txtPrescription.setRows(5);
        txtPrescription.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane3.setViewportView(txtPrescription);

        txtMedicalHistory.setColumns(20);
        txtMedicalHistory.setRows(5);
        txtMedicalHistory.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane4.setViewportView(txtMedicalHistory);

        txtAllergies.setColumns(20);
        txtAllergies.setRows(5);
        txtAllergies.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane5.setViewportView(txtAllergies);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Record ID");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Patient");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Doctor");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Diagnosis");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Treatment");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Prescription");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Medical History");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Allergies");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Visit Date");

        tblMedicalRecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Record ID", "Patient ID", "Patient Name", "Doctor ID", "Doctor Name", "Treatment", "Prescription", "Medical History", "Allergies", "Visit Date", "Visit Time", "Blood Pressure", "Temperature", "Heart Rate", "Weight ", "Height", "Clinical Notes", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMedicalRecords.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMedicalRecordsMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblMedicalRecords);

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("VisitTime");

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("BloodPressure");

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Temperature(C)");

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Status");

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Clinical Notes");

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Heart Rate (BPM)");

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Weight(KG)");

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Height(CM)");

        txtHeartRate.addActionListener(this::txtHeartRateActionPerformed);

        txtBloodPressure.addActionListener(this::txtBloodPressureActionPerformed);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select...", "Completed", "Follow-up", "Admitted", "Referred", "Cancelled", " " }));

        txtClinicalNotes.setColumns(20);
        txtClinicalNotes.setRows(5);
        txtClinicalNotess.setViewportView(txtClinicalNotes);

        javax.swing.GroupLayout pnlInternationalMedicalRecordsLayout = new javax.swing.GroupLayout(pnlInternationalMedicalRecords);
        pnlInternationalMedicalRecords.setLayout(pnlInternationalMedicalRecordsLayout);
        pnlInternationalMedicalRecordsLayout.setHorizontalGroup(
            pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                        .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel9))
                                .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addGap(18, 18, 18)))
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                                        .addComponent(txtRecordID)
                                        .addGap(155, 155, 155))
                                    .addComponent(jScrollPane1)
                                    .addComponent(jScrollPane5)
                                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(77, 77, 77)
                                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                                        .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel6))
                                        .addGap(29, 29, 29)
                                        .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane4)
                                            .addComponent(cmbPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane2)
                                            .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                                                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtBloodPressure)
                                                    .addComponent(txtWeight)
                                                    .addComponent(txtVisitDate))
                                                .addGap(155, 155, 155)))
                                        .addGap(28, 28, 28))
                                    .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtVisitTime)
                                    .addComponent(txtHeartRate))
                                .addGap(212, 212, 212)
                                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(303, 303, 303)))
                        .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(jLabel13)
                            .addComponent(jLabel19)
                            .addComponent(jLabel16))
                        .addGap(32, 32, 32)
                        .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3)
                            .addComponent(txtClinicalNotess, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtHeight)
                                    .addComponent(txtTemperature))
                                .addGap(155, 155, 155)))
                        .addGap(38, 38, 38))))
            .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        pnlInternationalMedicalRecordsLayout.setVerticalGroup(
            pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRecordID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(cmbPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cmbDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtClinicalNotess, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(44, 44, 44)
                                .addComponent(jLabel16))
                            .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInternationalMedicalRecordsLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))))))))
                .addGap(54, 54, 54)
                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11)
                    .addComponent(txtVisitTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBloodPressure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTemperature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(txtHeartRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnlInternationalMedicalRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtVisitDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(251, 251, 251))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlInternationalMedicalRecords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlInternationalMedicalRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 708, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPatientActionPerformed
    

    if (cmbPatient.getSelectedItem() == null) {
        return;
    }

    String selectedPatient = cmbPatient.getSelectedItem().toString();

    String patientID = selectedPatient.split(" - ")[0];

    try {

        Connection con = DatabaseConnection.getConnection();

        String sql =
                "SELECT PatientID, PatientName "
              + "FROM Patients WHERE PatientID=?";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, Integer.parseInt(patientID));

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {

            patientName = rs.getString("PatientName");

        }

        rs.close();
        pst.close();
        con.close();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Patient Loading Error:\n" + e.getMessage());

    }
    


    }//GEN-LAST:event_cmbPatientActionPerformed

    private void btnSAVEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSAVEActionPerformed




    try {

        InternationalMedicalRecordsModel model =
                new InternationalMedicalRecordsModel();


        String patient =
                cmbPatient.getSelectedItem().toString();

        model.setPatientID(
                Integer.parseInt(patient.split(" - ")[0]));

        String doctor =
                cmbDoctor.getSelectedItem().toString();

        model.setDoctorID(
                Integer.parseInt(doctor.split(" - ")[0]));

        model.setPatientName(patientName);
        model.setDoctorName(doctorName);

        model.setDiagnosis(txtDiagnosis.getText());
        model.setTreatment(txtTreatment.getText());
        model.setPrescription(txtPrescription.getText());
        model.setMedicalHistory(txtMedicalHistory.getText());
        model.setAllergies(txtAllergies.getText());

        model.setVisitDate(txtVisitDate.getText());
        model.setVisitTime(txtVisitTime.getText());

        model.setBloodPressure(txtBloodPressure.getText());
        model.setTemperature(txtTemperature.getText());
        model.setHeartRate(txtHeartRate.getText());
        model.setWeight(txtWeight.getText());
        model.setHeight(txtHeight.getText());

        model.setStatus(cmbStatus.getSelectedItem().toString());

        model.setClinicalNotes(txtClinicalNotes.getText());

        dao.saveMedicalRecord(model);
        
        loadMedicalRecords();
        
        JOptionPane.showMessageDialog(
        this,
        "Medical record saved successfully.");

    } catch (Exception e) {

    e.printStackTrace();

    JOptionPane.showMessageDialog(
            this,
            e.toString());

}


    }//GEN-LAST:event_btnSAVEActionPerformed

    private void btnUPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUPDATEActionPerformed
try {

    InternationalMedicalRecordsModel model =
            new InternationalMedicalRecordsModel();

    model.setRecordID(
            Integer.parseInt(txtRecordID.getText()));

    String patient =
            cmbPatient.getSelectedItem().toString();

    model.setPatientID(
            Integer.parseInt(patient.split(" - ")[0]));

    String doctor =
            cmbDoctor.getSelectedItem().toString();

    model.setDoctorID(
            Integer.parseInt(doctor.split(" - ")[0]));

    model.setPatientName(patientName);
    model.setDoctorName(doctorName);

    model.setDiagnosis(txtDiagnosis.getText());
    model.setTreatment(txtTreatment.getText());
    model.setPrescription(txtPrescription.getText());
    model.setMedicalHistory(txtMedicalHistory.getText());
    model.setAllergies(txtAllergies.getText());

    model.setVisitDate(txtVisitDate.getText());
    model.setVisitTime(txtVisitTime.getText());

    model.setBloodPressure(txtBloodPressure.getText());
    model.setTemperature(txtTemperature.getText());
    model.setHeartRate(txtHeartRate.getText());

    model.setWeight(txtWeight.getText());
    model.setHeight(txtHeight.getText());

    model.setClinicalNotes(txtClinicalNotes.getText());

    model.setStatus(
            cmbStatus.getSelectedItem().toString());

    dao.updateMedicalRecord(model);

    loadMedicalRecords();

    JOptionPane.showMessageDialog(
            this,
            "Medical record updated successfully.");

}
catch (Exception e) {

    e.printStackTrace();

    JOptionPane.showMessageDialog(
            this,
            e.toString());

}
        
    }//GEN-LAST:event_btnUPDATEActionPerformed

    private void btnDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDELETEActionPerformed

try {

    int option = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this medical record?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

    if (option == JOptionPane.YES_OPTION) {

        int recordID =
                Integer.parseInt(txtRecordID.getText());

        dao.deleteMedicalRecord(recordID);

        loadMedicalRecords();

        clearFields();

        JOptionPane.showMessageDialog(
                this,
                "Medical record deleted successfully.");

    }

}
catch (Exception e) {

    e.printStackTrace();

    JOptionPane.showMessageDialog(
            this,
            e.toString());

}
    }//GEN-LAST:event_btnDELETEActionPerformed

    private void btnSEARCHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSEARCHActionPerformed
JOptionPane.showMessageDialog(
        this,
        "Search feature will be implemented later.");
        
    }//GEN-LAST:event_btnSEARCHActionPerformed

    private void btnDISPLAYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDISPLAYActionPerformed
loadMedicalRecords();

JOptionPane.showMessageDialog(
        this,
        "Medical records displayed successfully.");
       
    }//GEN-LAST:event_btnDISPLAYActionPerformed

    private void btnCLEARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCLEARActionPerformed
clearFields();
      
    }//GEN-LAST:event_btnCLEARActionPerformed

    private void txtHeartRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHeartRateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHeartRateActionPerformed

    private void cmbDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDoctorActionPerformed

    if (cmbDoctor.getSelectedItem() == null) {
        return;
    }

    String selectedDoctor = cmbDoctor.getSelectedItem().toString();

    String doctorID = selectedDoctor.split(" - ")[0];

    try {

        Connection con = DatabaseConnection.getConnection();

        String sql =
                "SELECT DoctorID, DoctorName "
              + "FROM Doctors WHERE DoctorID=?";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, Integer.parseInt(doctorID));

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {

            doctorName = rs.getString("DoctorName");

        }

        rs.close();
        pst.close();
        con.close();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Doctor Loading Error:\n" + e.getMessage());

    }

    }//GEN-LAST:event_cmbDoctorActionPerformed

    private void txtBloodPressureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBloodPressureActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBloodPressureActionPerformed

    private void tblMedicalRecordsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMedicalRecordsMouseClicked
     int row = tblMedicalRecords.getSelectedRow();

txtRecordID.setText(
        tblMedicalRecords.getValueAt(row, 0).toString());

cmbPatient.setSelectedItem(
        tblMedicalRecords.getValueAt(row, 1).toString()
        + " - "
        + tblMedicalRecords.getValueAt(row, 2).toString());

cmbDoctor.setSelectedItem(
        tblMedicalRecords.getValueAt(row, 3).toString()
        + " - "
        + tblMedicalRecords.getValueAt(row, 4).toString());

txtDiagnosis.setText(
        tblMedicalRecords.getValueAt(row, 5).toString());

txtTreatment.setText(
        tblMedicalRecords.getValueAt(row, 6).toString());

txtPrescription.setText(
        tblMedicalRecords.getValueAt(row, 7).toString());

txtMedicalHistory.setText(
        tblMedicalRecords.getValueAt(row, 8).toString());

txtAllergies.setText(
        tblMedicalRecords.getValueAt(row, 9).toString());

txtVisitDate.setText(
        tblMedicalRecords.getValueAt(row, 10).toString());

txtVisitTime.setText(
        tblMedicalRecords.getValueAt(row, 11).toString());

txtBloodPressure.setText(
        tblMedicalRecords.getValueAt(row, 12).toString());

txtTemperature.setText(
        tblMedicalRecords.getValueAt(row, 13).toString());

txtHeartRate.setText(
        tblMedicalRecords.getValueAt(row, 14).toString());

txtWeight.setText(
        tblMedicalRecords.getValueAt(row, 15).toString());

txtHeight.setText(
        tblMedicalRecords.getValueAt(row, 16).toString());

txtClinicalNotes.setText(
        tblMedicalRecords.getValueAt(row, 17).toString());

cmbStatus.setSelectedItem(
        tblMedicalRecords.getValueAt(row, 18).toString());
    }//GEN-LAST:event_tblMedicalRecordsMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new InternationalMedicalRecords().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCLEAR;
    private javax.swing.JButton btnDELETE;
    private javax.swing.JButton btnDISPLAY;
    private javax.swing.JButton btnSAVE;
    private javax.swing.JButton btnSEARCH;
    private javax.swing.JButton btnUPDATE;
    private javax.swing.JComboBox<String> cmbDoctor;
    private javax.swing.JComboBox<String> cmbPatient;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel pnlInternationalMedicalRecords;
    private javax.swing.JTable tblMedicalRecords;
    private javax.swing.JTextArea txtAllergies;
    private javax.swing.JTextField txtBloodPressure;
    private javax.swing.JTextArea txtClinicalNotes;
    private javax.swing.JScrollPane txtClinicalNotess;
    private javax.swing.JTextArea txtDiagnosis;
    private javax.swing.JTextField txtHeartRate;
    private javax.swing.JTextField txtHeight;
    private javax.swing.JTextArea txtMedicalHistory;
    private javax.swing.JTextArea txtPrescription;
    private javax.swing.JTextField txtRecordID;
    private javax.swing.JTextField txtTemperature;
    private javax.swing.JTextArea txtTreatment;
    private javax.swing.JTextField txtVisitDate;
    private javax.swing.JTextField txtVisitTime;
    private javax.swing.JTextField txtWeight;
    // End of variables declaration//GEN-END:variables
}
