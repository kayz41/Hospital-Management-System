/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.userauthentication;
import javax.swing.*;
import com.mycompany.userauthentication.dao.DoctorDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;


/**
 *
 * @author Joel Kayz
 */
public class DoctorPerformance extends javax.swing.JFrame {
    
    
     DoctorDAO doctorDAO = new DoctorDAO();
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DoctorPerformance.class.getName());

    /**
     * Creates new form DoctorPerformance
     */
    public DoctorPerformance() {
       
        initComponents();
        
        loadDoctorStatistics();
        
        loadDoctorTable();
        
        setupDoctorTable();
        loadDoctorStatistics();
        loadDoctorTable();
        loadDoctorBarChart();
        
          loadDateTime();
    }

    
    private void loadDoctorStatistics() {
        int totalDoctors = doctorDAO.getTotalDoctors();
        txtTotalDoctors.setText(String.valueOf(totalDoctors));
    }
    
    
    
    private void setupDoctorTable() {
     javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
        
        model.addColumn("Doctor Name");
        model.addColumn("Specialization");
        model.addColumn("Patients Treated");
        
        tblDoctor.setModel(model);
    }
    
    
    private void loadDoctorsBarChart() {
        try{
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            
            ResultSet rs = doctorDAO.getDoctorPerformance();
            
            while (rs.next()) {
                
                String doctorName = rs.getString("DoctorName");
                int patients = rs.getInt("PatientsTreated");
                
                dataset.addValue(
                patients,
                "Patients",
                doctorName
                );
            }
            
            JFreeChart chart = ChartFactory.createBarChart(
            "Doctor Performance",
                    "Doctor",
                    "Patients Treated",
                    dataset
            );
            
            ChartPanel chartPanel = new ChartPanel(chart);
            
            pnlDoctorBarChart.removeAll();
            pnlDoctorBarChart.setLayout(
            new java.awt.BorderLayout()
            );
            pnlDoctorBarChart.add(
                    chartPanel,
                    java.awt.BorderLayout.CENTER
            );
            pnlDoctorBarChart.validate();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Chart Error: " + e.getMessage());
        }
    }
            
        
    
    
    
    
    private void loadDateTime() {

    Timer timer = new Timer(1000, e -> {

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd-MM-yyyy");

        SimpleDateFormat timeFormat =
                new SimpleDateFormat("HH:mm:ss");

        txtDate.setText(
                dateFormat.format(new Date())
        );

        txtTime.setText(
                timeFormat.format(new Date())
        );

    });

    timer.start();

}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void loadDoctorTable() {
        try{
            
            ResultSet rs = doctorDAO.getDoctorPerformance();
            javax.swing.table.DefaultTableModel model =
                    (javax.swing.table.DefaultTableModel) tblDoctor.getModel();
            
            model.setRowCount(0);
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("DoctorName"),
                    rs.getString("Specialization"),
                    rs.getInt("PatientsTreated")
                });
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage());
        }
    }
    
    
    
   private void loadDoctorBarChart() {

    try {

        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        ResultSet rs =
                doctorDAO.getDoctorPerformance();

        while (rs.next()) {

            dataset.addValue(
                    rs.getInt("PatientsTreated"),
                    "Patients",
                    rs.getString("DoctorName")
            );
        }

        JFreeChart chart =
                ChartFactory.createBarChart(
                        "Doctor Performance",
                        "Doctors",
                        "Patients Treated",
                        dataset
                );

        ChartPanel chartPanel =
                new ChartPanel(chart);

        chartPanel.setPreferredSize(
                new java.awt.Dimension(500, 300)
        );

        pnlDoctorBarChart.removeAll();

        pnlDoctorBarChart.setLayout(
                new java.awt.BorderLayout()
        );

        pnlDoctorBarChart.add(
                chartPanel,
                java.awt.BorderLayout.CENTER
        );

        pnlDoctorBarChart.revalidate();

        pnlDoctorBarChart.repaint();

    }
    catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Chart Error: " + e.getMessage()
        );

    }

}    
            
        
  
    private void exportDoctorPerformance() {
        try{
            JFileChooser fileChooser = new JFileChooser();
            
            fileChooser.setDialogTitle("Save Doctor Performance Report");
            
            int result = fileChooser.showSaveDialog(this);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                
                XSSFWorkbook workbook = new XSSFWorkbook();
                
                XSSFSheet sheet = workbook.createSheet("Doctor Performance");
                
                
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < tblDoctor.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    
                    cell.setCellValue(
                    tblDoctor.getColumnName(i)
                    );
                }
                
                for (int i = 0; i < tblDoctor.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for(int j=0; j< tblDoctor.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellValue(
                        tblDoctor.getValueAt(i, j).toString()
                        );
                    }
                }
            
            FileOutputStream fileOut = 
                    new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            
            
            JOptionPane.showMessageDialog(this,
                    "Doctor Performance Exported Successfully");
        }
        
      } catch (Exception e) {
    
    JOptionPane.showMessageDialog(this,
    "Export Error: " + e.getMessage());
}
    }



                    
                        
                            
     
    
    
    
    
    
    
    
    
    
    
    
   private void printDoctorPerformance() {

    try {

        boolean complete = tblDoctor.print();

        if (complete) {

            JOptionPane.showMessageDialog(this,
                    "Doctor Performance Report Printed Successfully");

        } else {

            JOptionPane.showMessageDialog(this,
                    "Printing Cancelled");

        }

    } catch (PrinterException e) {

        JOptionPane.showMessageDialog(this,
                "Print Error: " + e.getMessage());

    }

} 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
            
        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnREFRESH = new javax.swing.JButton();
        btnBACKTODASHBOARD = new javax.swing.JButton();
        btnEXPORT = new javax.swing.JButton();
        btnPRINT = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTime = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTotalDoctors = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        DoctorScrollPane = new javax.swing.JScrollPane();
        tblDoctor = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDoctorBarChart = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        btnREFRESH.setBackground(new java.awt.Color(102, 102, 102));
        btnREFRESH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnREFRESH.setForeground(new java.awt.Color(255, 255, 255));
        btnREFRESH.setText("REFRESH");
        btnREFRESH.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnREFRESH.addActionListener(this::btnREFRESHActionPerformed);

        btnBACKTODASHBOARD.setBackground(new java.awt.Color(102, 102, 102));
        btnBACKTODASHBOARD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBACKTODASHBOARD.setForeground(new java.awt.Color(255, 255, 255));
        btnBACKTODASHBOARD.setText("BACK TO DASHBOARD");
        btnBACKTODASHBOARD.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnBACKTODASHBOARD.addActionListener(this::btnBACKTODASHBOARDActionPerformed);

        btnEXPORT.setBackground(new java.awt.Color(102, 102, 102));
        btnEXPORT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEXPORT.setForeground(new java.awt.Color(255, 255, 255));
        btnEXPORT.setText("EXPORT");
        btnEXPORT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnEXPORT.addActionListener(this::btnEXPORTActionPerformed);

        btnPRINT.setBackground(new java.awt.Color(102, 102, 102));
        btnPRINT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPRINT.setForeground(new java.awt.Color(255, 255, 255));
        btnPRINT.setText("PRINT");
        btnPRINT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnPRINT.addActionListener(this::btnPRINTActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(btnREFRESH, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPRINT, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(btnEXPORT, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102)
                .addComponent(btnBACKTODASHBOARD, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBACKTODASHBOARD, btnEXPORT, btnPRINT, btnREFRESH});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBACKTODASHBOARD, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnREFRESH)
                    .addComponent(btnEXPORT)
                    .addComponent(btnPRINT))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBACKTODASHBOARD, btnEXPORT, btnPRINT, btnREFRESH});

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Date");

        txtDate.setEditable(false);
        txtDate.addActionListener(this::txtDateActionPerformed);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Time");

        txtTime.setEditable(false);
        txtTime.addActionListener(this::txtTimeActionPerformed);

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TOTAL DOCTORS");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("TOTAL APPOINTMENTS");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("BUSIEST DOCTOR");

        txtTotalDoctors.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtTotalDoctors.setForeground(new java.awt.Color(255, 255, 255));
        txtTotalDoctors.setText("0");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("0");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(171, 171, 171)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(180, 180, 180)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(195, 195, 195))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(txtTotalDoctors, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(252, 252, 252)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(213, 213, 213))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalDoctors)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("DOCTOR PERFORMANCE BAR CHART");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("TABLE DOCTOR PERFORMANCE");

        tblDoctor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Doctor ID", "Doctor Name", "Appointments"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        DoctorScrollPane.setViewportView(tblDoctor);

        javax.swing.GroupLayout pnlDoctorBarChartLayout = new javax.swing.GroupLayout(pnlDoctorBarChart);
        pnlDoctorBarChart.setLayout(pnlDoctorBarChartLayout);
        pnlDoctorBarChartLayout.setHorizontalGroup(
            pnlDoctorBarChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1068, Short.MAX_VALUE)
        );
        pnlDoctorBarChartLayout.setVerticalGroup(
            pnlDoctorBarChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(pnlDoctorBarChart);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DOCTOR PERFORMANCE MANAGEMENT");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
            .addComponent(DoctorScrollPane)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(362, 362, 362)
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(380, 380, 380)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DoctorScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateActionPerformed

    private void txtTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimeActionPerformed

    private void btnREFRESHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnREFRESHActionPerformed

        
       loadDoctorStatistics();
       loadDoctorTable();
       loadDoctorBarChart();
        

    }//GEN-LAST:event_btnREFRESHActionPerformed

    private void btnBACKTODASHBOARDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBACKTODASHBOARDActionPerformed
       this.dispose();
    }//GEN-LAST:event_btnBACKTODASHBOARDActionPerformed

    private void btnEXPORTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEXPORTActionPerformed


exportDoctorPerformance();




    }//GEN-LAST:event_btnEXPORTActionPerformed

    private void btnPRINTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPRINTActionPerformed
     
                                        

    try {

        boolean complete =
                tblDoctor.print();

        if (complete) {

            JOptionPane.showMessageDialog(
                    this,
                    "Doctor Performance Report Printed Successfully!"
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Printing Cancelled."
            );

        }

    } catch (java.awt.print.PrinterException e) {

        JOptionPane.showMessageDialog(
                this,
                "Printing Error: " + e.getMessage()
        );

    }


        
        
        
    }//GEN-LAST:event_btnPRINTActionPerformed

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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
        java.awt.EventQueue.invokeLater(() -> new DoctorPerformance().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane DoctorScrollPane;
    private javax.swing.JButton btnBACKTODASHBOARD;
    private javax.swing.JButton btnEXPORT;
    private javax.swing.JButton btnPRINT;
    private javax.swing.JButton btnREFRESH;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlDoctorBarChart;
    private javax.swing.JTable tblDoctor;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtTime;
    private javax.swing.JLabel txtTotalDoctors;
    // End of variables declaration//GEN-END:variables
}



