/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.userauthentication;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Joel Kayz
 */
public class PharmacyDispensing extends javax.swing.JFrame {
    
    
    private PharmacyDispensingDAO dao =
        new PharmacyDispensingDAO();

private String patientName;
private String doctorName;
private String medicineName;
    
    
    
    
    
   private void loadPatients() {

    try {

        Connection con =
                DatabaseConnection.getConnection();

        String sql =
                "SELECT PatientID, PatientName "
              + "FROM Patients";

        PreparedStatement pst =
                con.prepareStatement(sql);

        ResultSet rs =
                pst.executeQuery();

        cmbPatient.removeAllItems();

        while (rs.next()) {

            cmbPatient.addItem(
                    rs.getInt("PatientID")
                    + " - "
                    + rs.getString("PatientName"));
        }

        rs.close();
        pst.close();
        con.close();

    }

    catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Patient Loading Error:\n"
                + e.getMessage());
    }
} 
  






private void loadDoctors() {

    try {

        Connection con =
                DatabaseConnection.getConnection();

        String sql =
                "SELECT DoctorID, DoctorName "
              + "FROM Doctors";

        PreparedStatement pst =
                con.prepareStatement(sql);

        ResultSet rs =
                pst.executeQuery();

        cmbDoctor.removeAllItems();

        while (rs.next()) {

            cmbDoctor.addItem(
                    rs.getInt("DoctorID")
                    + " - "
                    + rs.getString("DoctorName"));
        }

        rs.close();
        pst.close();
        con.close();

    }

    catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Doctor Loading Error:\n"
                + e.getMessage());
    }
}





private void loadMedicines() {

    try {

        Connection con =
                DatabaseConnection.getConnection();

        String sql =
                "SELECT MedicineID, MedicineName, Quantity "
              + "FROM Pharmacy "
              + "WHERE Quantity > 0 "
              + "ORDER BY MedicineName";

        PreparedStatement pst =
                con.prepareStatement(sql);

        ResultSet rs =
                pst.executeQuery();

        cmbMedicine.removeAllItems();

        while (rs.next()) {

            cmbMedicine.addItem(
                    rs.getInt("MedicineID")
                    + " - "
                    + rs.getString("MedicineName")
                    + " (Stock: "
                    + rs.getInt("Quantity")
                    + ")");
        }

        rs.close();
        pst.close();
        con.close();

    }

    catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Medicine Loading Error:\n"
                + e.getMessage());
    }
}





private void loadMedicalRecords() {

    try {

        Connection con =
                DatabaseConnection.getConnection();

        String sql =
                "SELECT RecordID, PatientID, PatientName, "
              + "DoctorID, DoctorName, Prescription "
              + "FROM InternationalMedicalRecords "
              + "ORDER BY RecordID DESC";

        PreparedStatement pst =
                con.prepareStatement(sql);

        ResultSet rs =
                pst.executeQuery();

        cmbMedicalRecord.removeAllItems();

        while (rs.next()) {

            cmbMedicalRecord.addItem(
                    rs.getInt("RecordID")
                    + " - "
                    + rs.getString("PatientName")
                    + " - "
                    + rs.getString("DoctorName")
                    + " - "
                    + rs.getString("Prescription"));
        }

        rs.close();
        pst.close();
        con.close();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Medical Record Loading Error:\n"
                + e.getMessage());
    }
}




private void loadDispensing() {

    try {

        DefaultTableModel model =
                (DefaultTableModel) tblPharmacyDispensing.getModel();

        model.setRowCount(0);

        ResultSet rs =
                dao.getAllDispensing();

       while (rs.next()) {

   model.addRow(new Object[]{

    rs.getInt("DispensingID"),
    rs.getInt("PatientID"),
    rs.getString("PatientName"),
    rs.getInt("DoctorID"),
    rs.getString("DoctorName"),
    rs.getInt("MedicineID"),
    rs.getString("MedicineName"),
    rs.getInt("Quantity"),
    rs.getDate("DispensingDate"),
    rs.getString("Status"),
    rs.getObject("RecordID")

});
}

        rs.close();

    }

    catch (Exception e) {

        e.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                "Unable to load dispensing records:\n"
                + e.getMessage(),
                "Loading Error",
                JOptionPane.ERROR_MESSAGE);
    }
}






private void clearFields() {

  

    txtDispensingID.setText("");

    // ==============================
    // MEDICAL RECORD
    // ==============================

    txtRecordID.setText("");
    txtPrescription.setText("");

    // ==============================
    // PATIENT
    // ==============================

    txtPatientID.setText("");
    txtPatientName.setText("");

    // ==============================
    // DOCTOR
    // ==============================

    txtDoctorID.setText("");
    txtDoctorName.setText("");

    // ==============================
    // MEDICINE
    // ==============================

    txtMedicineID.setText("");
    txtMedicineName.setText("");

    // ==============================
    // DISPENSING
    // ==============================

    txtQuantity.setText("");
    txtDispensingDate.setText("");
    
    txtRecordID.setText("");
txtPrescription.setText("");


    // ==============================
    // COMBO BOXES
    // ==============================

    cmbMedicalRecord.setSelectedIndex(-1);
    cmbPatient.setSelectedIndex(-1);
    cmbDoctor.setSelectedIndex(-1);
    cmbMedicine.setSelectedIndex(-1);
    cmbStatus.setSelectedIndex(-1);

    // ==============================
    // FOCUS
    // ==============================

    txtDispensingID.requestFocus();
    
    
}







    
    
    
    
    
    
    
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PharmacyDispensing.class.getName());

    /**
     * Creates new form PharmacyDispensing
     */
    public PharmacyDispensing() {

    initComponents();
    
    tblPharmacyDispensing.setShowGrid(true);
tblPharmacyDispensing.setIntercellSpacing(
        new java.awt.Dimension(1, 1));

    loadPatients();

    loadDoctors();

    loadMedicines();

    loadDispensing();

    cmbPatient.setSelectedIndex(-1);

    cmbDoctor.setSelectedIndex(-1);

    cmbMedicine.setSelectedIndex(-1);

    cmbStatus.setSelectedIndex(-1);
    
    txtRecordID.setEditable(false);
txtPrescription.setEditable(false);
    
    loadMedicalRecords();
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel17 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnSAVE = new javax.swing.JButton();
        btnSEARCH = new javax.swing.JButton();
        btnDELETE = new javax.swing.JButton();
        btnDISPLAY = new javax.swing.JButton();
        btnCLEAR = new javax.swing.JButton();
        btnDASHBOARD = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDispensingID = new javax.swing.JTextField();
        txtDoctorName = new javax.swing.JTextField();
        txtDispensingDate = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        txtDoctorID = new javax.swing.JTextField();
        txtMedicineID = new javax.swing.JTextField();
        txtMedicineName = new javax.swing.JTextField();
        txtPatientID = new javax.swing.JTextField();
        txtPatientName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPharmacyDispensing = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmbMedicine = new javax.swing.JComboBox<>();
        cmbDoctor = new javax.swing.JComboBox<>();
        cmbPatient = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtRecordID = new javax.swing.JTextField();
        cmbMedicalRecord = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtPrescription = new javax.swing.JTextField();

        jLabel17.setText("jLabel17");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("PHARMACY DISPENSING MANAGEMENT");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel14)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        btnSAVE.setBackground(new java.awt.Color(102, 102, 102));
        btnSAVE.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSAVE.setForeground(new java.awt.Color(255, 255, 255));
        btnSAVE.setText("SAVE");
        btnSAVE.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSAVE.addActionListener(this::btnSAVEActionPerformed);

        btnSEARCH.setBackground(new java.awt.Color(102, 102, 102));
        btnSEARCH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSEARCH.setForeground(new java.awt.Color(255, 255, 255));
        btnSEARCH.setText("SEARCH");
        btnSEARCH.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSEARCH.addActionListener(this::btnSEARCHActionPerformed);

        btnDELETE.setBackground(new java.awt.Color(102, 102, 102));
        btnDELETE.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDELETE.setForeground(new java.awt.Color(255, 255, 255));
        btnDELETE.setText("DELETE");
        btnDELETE.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnDELETE.addActionListener(this::btnDELETEActionPerformed);

        btnDISPLAY.setBackground(new java.awt.Color(102, 102, 102));
        btnDISPLAY.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDISPLAY.setForeground(new java.awt.Color(255, 255, 255));
        btnDISPLAY.setText("DISPLAY");
        btnDISPLAY.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnDISPLAY.addActionListener(this::btnDISPLAYActionPerformed);

        btnCLEAR.setBackground(new java.awt.Color(102, 102, 102));
        btnCLEAR.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCLEAR.setForeground(new java.awt.Color(255, 255, 255));
        btnCLEAR.setText("CLEAR");
        btnCLEAR.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnCLEAR.addActionListener(this::btnCLEARActionPerformed);

        btnDASHBOARD.setBackground(new java.awt.Color(102, 102, 102));
        btnDASHBOARD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDASHBOARD.setForeground(new java.awt.Color(255, 255, 255));
        btnDASHBOARD.setText("DASHBOARD");
        btnDASHBOARD.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnDASHBOARD.addActionListener(this::btnDASHBOARDActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnSAVE, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(btnSEARCH, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(btnDELETE, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(btnDISPLAY, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnDASHBOARD, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btnCLEAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(78, 78, 78))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSAVE)
                    .addComponent(btnSEARCH)
                    .addComponent(btnDELETE)
                    .addComponent(btnDISPLAY)
                    .addComponent(btnCLEAR))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDASHBOARD, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Dispensing ID");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("DoctorName");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Dispensing Date");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Patient ID");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("MedicineID");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Quantity");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("PatientName");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("MedicineName");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("DoctorID");

        txtDispensingID.setText("\n");

        txtDoctorName.setText("\n");

        txtDispensingDate.setText("\n");

        txtQuantity.setText("\n");
        txtQuantity.addActionListener(this::txtQuantityActionPerformed);

        txtDoctorID.setText("\n");
        txtDoctorID.addActionListener(this::txtDoctorIDActionPerformed);

        txtMedicineID.setText("\n");

        txtMedicineName.setText("\n");

        txtPatientID.setText("\n");

        txtPatientName.setText("\n");

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        tblPharmacyDispensing.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Dispensing ID", "PatientID", "Patient Name", "Doctor ID", "Doctor Name", "Medicine ID", "Medicine Name", "Quantity", "Dispensing Date", "Status", "RecordID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPharmacyDispensing.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPharmacyDispensingMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPharmacyDispensing);

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Patient");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Doctor");

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Medicine");

        cmbMedicine.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbMedicine.addActionListener(this::cmbMedicineActionPerformed);

        cmbDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbDoctor.addActionListener(this::cmbDoctorActionPerformed);

        cmbPatient.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbPatient.addActionListener(this::cmbPatientActionPerformed);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Status");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select...", "Dispensed" }));

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("RecordID");

        txtRecordID.addActionListener(this::txtRecordIDActionPerformed);

        cmbMedicalRecord.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbMedicalRecord.addActionListener(this::cmbMedicalRecordActionPerformed);

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("MedicalRecord");

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Prescription");

        txtPrescription.addActionListener(this::txtPrescriptionActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(cmbMedicine, 0, 99, Short.MAX_VALUE)
                        .addGap(753, 753, 753))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(28, 28, 28)
                                        .addComponent(txtDispensingID, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(39, 39, 39)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtPatientID, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtRecordID, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDoctorID, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMedicineID, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel8)
                                                .addComponent(jLabel2))
                                            .addGap(39, 39, 39))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel13)
                                            .addGap(85, 85, 85)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(51, 51, 51)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cmbDoctor, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbStatus, javax.swing.GroupLayout.Alignment.LEADING, 0, 105, Short.MAX_VALUE)
                                        .addComponent(txtDoctorName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtMedicineName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                        .addComponent(txtPatientName, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addGap(64, 64, 64))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(92, 92, 92)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel18))
                                        .addGap(71, 71, 71)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPrescription, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtDispensingDate, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbMedicalRecord, 0, 105, Short.MAX_VALUE)
                                        .addComponent(cmbPatient, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGap(60, 60, 60))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtDispensingID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtDispensingDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtPatientID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtDoctorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtPatientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtMedicineName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtMedicineID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtPrescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addComponent(txtDoctorID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cmbPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(txtRecordID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbMedicalRecord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cmbMedicine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void cmbPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPatientActionPerformed
     
     

    if (cmbPatient.getSelectedItem() == null) {

        return;
    }

    String patient =
            cmbPatient.getSelectedItem()
                    .toString();

    String[] parts =
            patient.split(" - ", 2);

    txtPatientID.setText(parts[0]);

    txtPatientName.setText(parts[1]);

    patientName = parts[1];

    }//GEN-LAST:event_cmbPatientActionPerformed

    private void cmbDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDoctorActionPerformed
    
     

    if (cmbDoctor.getSelectedItem() == null) {

        return;
    }

    String doctor =
            cmbDoctor.getSelectedItem()
                    .toString();

    String[] parts =
            doctor.split(" - ", 2);

    txtDoctorID.setText(parts[0]);

    txtDoctorName.setText(parts[1]);

    doctorName = parts[1];

    }//GEN-LAST:event_cmbDoctorActionPerformed

    private void cmbMedicineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMedicineActionPerformed

    

    if (cmbMedicine.getSelectedItem() == null) {

        return;
    }

    String medicine =
            cmbMedicine.getSelectedItem()
                    .toString();

    String[] parts =
            medicine.split(" - ", 2);

    txtMedicineID.setText(parts[0]);

    String medicinePart =
            parts[1];

    int stockIndex =
            medicinePart.indexOf(" (Stock:");

    if (stockIndex >= 0) {

        medicineName =
                medicinePart.substring(
                        0,
                        stockIndex);

    }
    else {

        medicineName =
                medicinePart;
    }

    txtMedicineName.setText(
            medicineName);

    }//GEN-LAST:event_cmbMedicineActionPerformed

    private void btnSAVEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSAVEActionPerformed

    
    try {

        // ==============================
        // VALIDATION
        // ==============================

        if (!ValidationUtil.required(
                cmbPatient,
                "a patient")) {

            return;
        }

        if (!ValidationUtil.required(
                cmbDoctor,
                "a doctor")) {

            return;
        }

        if (!ValidationUtil.required(
                cmbMedicine,
                "a medicine")) {

            return;
        }

        if (!ValidationUtil.positiveInteger(
                txtQuantity,
                "Quantity")) {

            return;
        }

        if (!ValidationUtil.required(
                txtDispensingDate,
                "Dispensing Date")) {

            return;
        }

        if (!ValidationUtil.required(
                cmbStatus,
                "a status")) {

            return;
        }


        // ==============================
        // CREATE MODEL
        // ==============================

        PharmacyDispensingModel model =
                new PharmacyDispensingModel();
        
        if (txtRecordID.getText().trim().isEmpty()) {

    JOptionPane.showMessageDialog(
            this,
            "Please select a Medical Record.");

    return;
}

model.setRecordID(
        Integer.parseInt(
                txtRecordID.getText().trim()));


        model.setPatientID(
                Integer.parseInt(
                        txtPatientID.getText()
                                .trim()));


        model.setPatientName(
                txtPatientName.getText()
                        .trim());


        model.setDoctorID(
                Integer.parseInt(
                        txtDoctorID.getText()
                                .trim()));


        model.setDoctorName(
                txtDoctorName.getText()
                        .trim());


        model.setMedicineID(
                Integer.parseInt(
                        txtMedicineID.getText()
                                .trim()));


        model.setMedicineName(
                txtMedicineName.getText()
                        .trim());


        model.setQuantity(
                Integer.parseInt(
                        txtQuantity.getText()
                                .trim()));


        model.setDispensingDate(
                txtDispensingDate.getText()
                        .trim());


        model.setStatus(
                cmbStatus.getSelectedItem()
                        .toString());

     

        // ==============================
        // SAVE + REDUCE STOCK
        // ==============================

        dao.saveDispensing(model);


        // ==============================
        // REFRESH
        // ==============================

        loadDispensing();

        loadMedicines();


        JOptionPane.showMessageDialog(
                this,
                "Medicine dispensed successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);


        clearFields();

    }

    catch (Exception e) {

        e.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Dispensing Error",
                JOptionPane.ERROR_MESSAGE);
    }

    
    
    
    
    
    
    }//GEN-LAST:event_btnSAVEActionPerformed

    private void btnDISPLAYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDISPLAYActionPerformed
    
    
    
   
       

    loadDispensing();

    
    
    
    
    }//GEN-LAST:event_btnDISPLAYActionPerformed

    private void btnSEARCHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSEARCHActionPerformed

   
    try {

        if (!ValidationUtil.required(
                txtDispensingID,
                "Dispensing ID")) {

            return;
        }

        String id =
                txtDispensingID.getText()
                        .trim();

        ResultSet rs =
                dao.searchDispensing(id);

        if (rs.next()) {

            // ==============================
            // DISPENSING INFORMATION
            // ==============================

            txtDispensingID.setText(
                    rs.getString("DispensingID"));

            txtPatientID.setText(
                    rs.getString("PatientID"));

            txtPatientName.setText(
                    rs.getString("PatientName"));

            txtDoctorID.setText(
                    rs.getString("DoctorID"));

            txtDoctorName.setText(
                    rs.getString("DoctorName"));

            txtMedicineID.setText(
                    rs.getString("MedicineID"));

            txtMedicineName.setText(
                    rs.getString("MedicineName"));

            txtQuantity.setText(
                    rs.getString("Quantity"));

            txtDispensingDate.setText(
                    rs.getString("DispensingDate"));

            cmbStatus.setSelectedItem(
                    rs.getString("Status"));


            // ==============================
            // MEDICAL RECORD ID
            // ==============================

            Object recordID =
                    rs.getObject("RecordID");

            if (recordID != null) {

                txtRecordID.setText(
                        recordID.toString());

                // ==========================
                // LOAD PRESCRIPTION
                // ==========================

                Connection con =
                        DatabaseConnection.getConnection();

                String sql =
                        "SELECT Prescription "
                      + "FROM InternationalMedicalRecords "
                      + "WHERE RecordID=?";

                PreparedStatement pst =
                        con.prepareStatement(sql);

                pst.setInt(
                        1,
                        Integer.parseInt(
                                recordID.toString()));

                ResultSet medicalRs =
                        pst.executeQuery();

                if (medicalRs.next()) {

                    txtPrescription.setText(
                            medicalRs.getString(
                                    "Prescription"));
                }

                medicalRs.close();
                pst.close();
                con.close();

            } else {

                txtRecordID.setText("");
                txtPrescription.setText("");
            }


        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No dispensing record found.");
        }

        rs.close();

    } catch (Exception e) {

        e.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                "Search Error:\n"
                + e.getMessage());
    }

    }//GEN-LAST:event_btnSEARCHActionPerformed

    private void btnDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDELETEActionPerformed
        
    
    
    
    try {

        if (!ValidationUtil.required(
                txtDispensingID,
                "Dispensing ID")) {

            return;
        }


        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deleting this dispensing record "
                        + "will return its quantity "
                        + "to pharmacy stock.\n\n"
                        + "Continue?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);


        if (option != JOptionPane.YES_OPTION) {

            return;
        }


        int id =
                Integer.parseInt(
                        txtDispensingID
                                .getText()
                                .trim());


        dao.deleteDispensing(id);


        loadDispensing();

        loadMedicines();


        JOptionPane.showMessageDialog(
                this,
                "Dispensing record deleted "
                + "and stock restored.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);


        clearFields();

    }

       catch (Exception e) {

        e.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                "Delete Error:\n"
                + e.getMessage());
    }
   
    

    
    }//GEN-LAST:event_btnDELETEActionPerformed

    private void btnCLEARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCLEARActionPerformed
        
    
   

    clearFields();

    
   
    
    }//GEN-LAST:event_btnCLEARActionPerformed

    private void tblPharmacyDispensingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPharmacyDispensingMouseClicked




    int row =
            tblPharmacyDispensing
                    .getSelectedRow();

    if (row < 0) {

        return;
    }


    txtDispensingID.setText(
            tblPharmacyDispensing
                    .getValueAt(row, 0)
                    .toString());

    txtPatientID.setText(
            tblPharmacyDispensing
                    .getValueAt(row, 1)
                    .toString());

    txtPatientName.setText(
            tblPharmacyDispensing
                    .getValueAt(row, 2)
                    .toString());

    txtDoctorID.setText(
            tblPharmacyDispensing
                    .getValueAt(row, 3)
                    .toString());

    txtDoctorName.setText(
            tblPharmacyDispensing
                    .getValueAt(row, 4)
                    .toString());

    txtMedicineID.setText(
            tblPharmacyDispensing
                    .getValueAt(row, 5)
                    .toString());

    txtMedicineName.setText(
            tblPharmacyDispensing
                    .getValueAt(row, 6)
                    .toString());

    txtQuantity.setText(
            tblPharmacyDispensing
                    .getValueAt(row, 7)
                    .toString());

    txtDispensingDate.setText(
            tblPharmacyDispensing
                    .getValueAt(row, 8)
                    .toString());

    cmbStatus.setSelectedItem(
            tblPharmacyDispensing
                    .getValueAt(row, 9)
                    .toString());
    Object recordID =
        tblPharmacyDispensing
                .getValueAt(row, 10);

if (recordID != null) {

    txtRecordID.setText(
            recordID.toString());

} else {

    txtRecordID.setText("");
}







    }//GEN-LAST:event_tblPharmacyDispensingMouseClicked

    private void txtDoctorIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDoctorIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDoctorIDActionPerformed

    private void txtRecordIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecordIDActionPerformed
       txtRecordID.setEditable(false);
    }//GEN-LAST:event_txtRecordIDActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void cmbMedicalRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMedicalRecordActionPerformed
      
   

    if (cmbMedicalRecord.getSelectedItem() == null) {

        return;
    }


    try {

        String selectedRecord =
                cmbMedicalRecord
                        .getSelectedItem()
                        .toString();


        String recordID =
                selectedRecord.split(" - ")[0];


        Connection con =
                DatabaseConnection.getConnection();


        String sql =
                "SELECT RecordID, PatientID, PatientName, "
              + "DoctorID, DoctorName, Prescription "
              + "FROM InternationalMedicalRecords "
              + "WHERE RecordID=?";


        PreparedStatement pst =
                con.prepareStatement(sql);


        pst.setInt(
                1,
                Integer.parseInt(recordID));


        ResultSet rs =
                pst.executeQuery();


        if (rs.next()) {

            txtRecordID.setText(
                    rs.getString("RecordID"));


            txtPatientID.setText(
                    rs.getString("PatientID"));


            txtPatientName.setText(
                    rs.getString("PatientName"));


            txtDoctorID.setText(
                    rs.getString("DoctorID"));


            txtDoctorName.setText(
                    rs.getString("DoctorName"));
            
            txtPrescription.setText(
        rs.getString("Prescription"));


           
        }


        rs.close();
        pst.close();
        con.close();


    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Medical Record Loading Error:\n"
                + e.getMessage());
    }

    }//GEN-LAST:event_cmbMedicalRecordActionPerformed

    private void txtPrescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrescriptionActionPerformed
      txtPrescription.setEditable(false);
    }//GEN-LAST:event_txtPrescriptionActionPerformed

    private void btnDASHBOARDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDASHBOARDActionPerformed
      this.dispose();
    }//GEN-LAST:event_btnDASHBOARDActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new PharmacyDispensing().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCLEAR;
    private javax.swing.JButton btnDASHBOARD;
    private javax.swing.JButton btnDELETE;
    private javax.swing.JButton btnDISPLAY;
    private javax.swing.JButton btnSAVE;
    private javax.swing.JButton btnSEARCH;
    private javax.swing.JComboBox<String> cmbDoctor;
    private javax.swing.JComboBox<String> cmbMedicalRecord;
    private javax.swing.JComboBox<String> cmbMedicine;
    private javax.swing.JComboBox<String> cmbPatient;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPharmacyDispensing;
    private javax.swing.JTextField txtDispensingDate;
    private javax.swing.JTextField txtDispensingID;
    private javax.swing.JTextField txtDoctorID;
    private javax.swing.JTextField txtDoctorName;
    private javax.swing.JTextField txtMedicineID;
    private javax.swing.JTextField txtMedicineName;
    private javax.swing.JTextField txtPatientID;
    private javax.swing.JTextField txtPatientName;
    private javax.swing.JTextField txtPrescription;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtRecordID;
    // End of variables declaration//GEN-END:variables
}