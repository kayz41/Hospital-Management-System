/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.userauthentication;
import com.mycompany.userauthentication.dao.BillingDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.BorderLayout;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.awt.print.PrinterException;
/**
 *
 * @author Joel Kayz
 */
public class RevenueDashboard extends javax.swing.JFrame {
    
    BillingDAO billingDAO = new BillingDAO();
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RevenueDashboard.class.getName());

    /**
     * Creates new form RevenueDashboard
     */
    public RevenueDashboard() {
        initComponents();
        
         
        loadRevenueStatistics();
        
        loadDateTime();
        
        loadRevenueTable();
        
        loadRevenueChart();
        
          
    }

    
    
    private void loadRevenueStatistics() {

    try {

        Connection con = DatabaseConnection.getConnection();

        // Total Revenue
        PreparedStatement pst1 = con.prepareStatement(
                "SELECT ISNULL(SUM(Amount),0) FROM Billing");
        ResultSet rs1 = pst1.executeQuery();

        if (rs1.next()) {
            jLabel5.setText("UGX " + rs1.getInt(1));
        }

        // Monthly Revenue
        PreparedStatement pst2 = con.prepareStatement(
                "SELECT ISNULL(SUM(Amount),0) FROM Billing WHERE MONTH(PaymentDate)=MONTH(GETDATE()) AND YEAR(PaymentDate)=YEAR(GETDATE())");
        ResultSet rs2 = pst2.executeQuery();

        if (rs2.next()) {
            jLabel7.setText("UGX " + rs2.getInt(1));
        }

        // Today's Revenue
        PreparedStatement pst3 = con.prepareStatement(
                "SELECT ISNULL(SUM(Amount),0) FROM Billing WHERE PaymentDate=CAST(GETDATE() AS DATE)");
        ResultSet rs3 = pst3.executeQuery();

        if (rs3.next()) {
            jLabel9.setText("UGX " + rs3.getInt(1));
        }

        // Total Bills
        PreparedStatement pst4 = con.prepareStatement(
                "SELECT COUNT(*) FROM Billing");
        ResultSet rs4 = pst4.executeQuery();

        if (rs4.next()) {
            jLabel11.setText(rs4.getString(1));
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage());

    }

}
    
    
    
    private void loadRevenueTable() {

    try {

        Connection con = DatabaseConnection.getConnection();

       PreparedStatement pst;
       String filter = cmbPaymentFilter.getSelectedItem().toString();
       if (filter.equals("All Payments")) {
           pst =con.prepareStatement(
           "SELECT BillingID, PatientID, Amount, PaymentMethod, PaymentDate FROM Billing");
       } else {
           pst = con.prepareStatement(
           "SELECT BillingID, PatientID, Amount, PaymentMethod, PaymentDate " +
                   "FROM Billing WHERE PaymentMethod = ?");
           pst.setString(1, filter);
       }
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) tblRevenueTable.getModel();

        model.setRowCount(0);

        while (rs.next()) {

            model.addRow(new Object[]{

                rs.getInt("BillingID"),
                rs.getString("PatientID"),
                rs.getInt("Amount"),
                rs.getString("PaymentMethod"),
                rs.getDate("PaymentDate")

            });

        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage());

    }

}
    
    
    
    
  
    
    private void exportRevenueToExcel() {

    try {

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Save Revenue Report");

        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            XSSFWorkbook workbook = new XSSFWorkbook();

            XSSFSheet sheet = workbook.createSheet("Revenue Report");


            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < tblRevenueTable.getColumnCount(); i++) {

                Cell cell = headerRow.createCell(i);

                cell.setCellValue(
                        tblRevenueTable.getColumnName(i)
                );

            }


            for (int i = 0; i < tblRevenueTable.getRowCount(); i++) {

                Row row = sheet.createRow(i + 1);

                for (int j = 0; j < tblRevenueTable.getColumnCount(); j++) {

                    Cell cell = row.createCell(j);

                    cell.setCellValue(
                            tblRevenueTable.getValueAt(i, j).toString()
                    );

                }

            }


            FileOutputStream fileOut =
                    new FileOutputStream(filePath);

            workbook.write(fileOut);

            fileOut.close();

            workbook.close();


            JOptionPane.showMessageDialog(this,
                    "Revenue Report Exported Successfully");

        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Export Error: " + e.getMessage());

    }

}
    
    
    
    
    
    
    private void printRevenueReport() {

    try {

        boolean complete = tblRevenueTable.print();

        if (complete) {

            JOptionPane.showMessageDialog(this,
                    "Revenue Report Printed Successfully");

        } else {

            JOptionPane.showMessageDialog(this,
                    "Printing Cancelled");

        }

    } catch (PrinterException e) {

        JOptionPane.showMessageDialog(this,
                "Print Error: " + e.getMessage());

    }

}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 private void loadRevenueChart() {
    try {
        Connection con =
                DatabaseConnection.getConnection();
        PreparedStatement pst;
        String filter =
                cmbPaymentFilter.getSelectedItem().toString();
        if (filter.equals("All Payments")) {
            pst = con.prepareStatement(
                    "SELECT PaymentMethod,  SUM(Amount) AS TotalRevenue "
                    + "FROM Billing "
                    + "GROUP BY PaymentMethod"
            );
        }
        else {
            pst = con.prepareStatement(
                    "SELECT PaymentMethod, "
                    + "SUM(Amount) AS TotalRevenue "
                    + "FROM Billing "
                    + "WHERE PaymentMethod = ? "
                    + "GROUP BY PaymentMethod"
            );
            pst.setString(1, filter);
        }
        ResultSet rs =
                pst.executeQuery();
        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        int categoryCount = 0;

        while (rs.next()) {
            dataset.addValue(
                    rs.getDouble("TotalRevenue"),
                    "Revenue",
                    rs.getString("PaymentMethod")
            );
            categoryCount++;
        }
        JFreeChart chart =
                ChartFactory.createBarChart(
                        "Revenue by Payment Method",
                        "Payment Method",
                        "Revenue",
                        dataset
                );
        ChartPanel chartPanel =
                new ChartPanel(chart);

        int panelWidth = pnlBARCHART.getWidth() > 0 ? pnlBARCHART.getWidth() : 700;
        int panelHeight = 126;

        // Wider per-category spacing so the chart reliably exceeds panel width
        int chartWidth = Math.max(panelWidth, categoryCount * 250);

        chartPanel.setPreferredSize(
                new java.awt.Dimension(chartWidth, panelHeight)
        );

        chartPanel.setMaximumDrawWidth(4000);
        chartPanel.setMaximumDrawHeight(2000);

        javax.swing.JScrollPane chartScrollPane =
                new javax.swing.JScrollPane(chartPanel);

        // Force horizontal bar visible always, so it's easy to confirm it's there
        chartScrollPane.setHorizontalScrollBarPolicy(
                javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        chartScrollPane.setVerticalScrollBarPolicy(
                javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER
        );

        chartScrollPane.setPreferredSize(
                new java.awt.Dimension(panelWidth, panelHeight)
        );
        chartScrollPane.setMaximumSize(
                new java.awt.Dimension(Integer.MAX_VALUE, panelHeight)
        );

        pnlBARCHART.removeAll();
        pnlBARCHART.setLayout(
                new java.awt.BorderLayout()
        );
        pnlBARCHART.add(
                chartScrollPane,
                java.awt.BorderLayout.CENTER
        );

        pnlBARCHART.setPreferredSize(
                new java.awt.Dimension(panelWidth, panelHeight)
        );
        pnlBARCHART.setMaximumSize(
                new java.awt.Dimension(Integer.MAX_VALUE, panelHeight)
        );

        pnlBARCHART.revalidate();
        pnlBARCHART.repaint();
        rs.close();
        pst.close();
        con.close();
    }
    catch (Exception e) {
        JOptionPane.showMessageDialog(
                this,
                "Chart Error: " + e.getMessage()
        );
    }
}
        
        
        
        
        
        
        
        
        
        

      
    
    
    
    
    
  private void loadDateTime() {

    Timer timer = new Timer(1000, e -> {

        Date date = new Date();

        txtDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));

        txtTime.setText(new SimpleDateFormat("hh:mm:ss a").format(date));

    });

    timer.start();

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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnREFRESH = new javax.swing.JButton();
        btnBACKTODASHBOARD = new javax.swing.JButton();
        btnEXPORT = new javax.swing.JButton();
        btnPRINT = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        txtTime = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pnlBARCHART = new javax.swing.JPanel();
        jScrollBar1 = new javax.swing.JScrollBar();
        lblREVENUEBARCHART = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        RevenueScrollPane = new javax.swing.JScrollPane();
        tblRevenueTable = new javax.swing.JTable();
        cmbPaymentFilter = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("REVENUE ANALYTICS DASHBOARD");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(287, 287, 287)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(41, 41, 41))
        );

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
                .addGap(81, 81, 81)
                .addComponent(btnREFRESH, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(btnEXPORT, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(btnPRINT, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBACKTODASHBOARD, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBACKTODASHBOARD, btnEXPORT, btnPRINT, btnREFRESH});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBACKTODASHBOARD, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnREFRESH)
                    .addComponent(btnEXPORT)
                    .addComponent(btnPRINT))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBACKTODASHBOARD, btnEXPORT, btnPRINT, btnREFRESH});

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Date");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Time");

        txtDate.setEditable(false);
        txtDate.addActionListener(this::txtDateActionPerformed);

        txtTime.setEditable(false);
        txtTime.addActionListener(this::txtTimeActionPerformed);

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TOTAL REVENUE");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("UGX 0");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("MONTHLY REVENUE");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("UGX 0");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("TODAY'S REVENUE");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("UGX 0");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("TOTAL BILLS");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("UGX 0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(33, 33, 33)))
                .addGap(145, 145, 145)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(53, 53, 53)))
                .addGap(146, 146, 146)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(45, 45, 45)))
                .addGap(147, 147, 147)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)))
                .addGap(110, 110, 110))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)))
                .addGap(28, 28, 28))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9});

        javax.swing.GroupLayout pnlBARCHARTLayout = new javax.swing.GroupLayout(pnlBARCHART);
        pnlBARCHART.setLayout(pnlBARCHARTLayout);
        pnlBARCHARTLayout.setHorizontalGroup(
            pnlBARCHARTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBARCHARTLayout.createSequentialGroup()
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBARCHARTLayout.setVerticalGroup(
            pnlBARCHARTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
        );

        lblREVENUEBARCHART.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblREVENUEBARCHART.setForeground(new java.awt.Color(255, 255, 255));
        lblREVENUEBARCHART.setText("REVENUE BAR CHART");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("REVENUE TABLE");

        tblRevenueTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Bill ID", "Patient ID", "Amount", "Payment Method", "Payment Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        RevenueScrollPane.setViewportView(tblRevenueTable);

        cmbPaymentFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT...", "All Payments", "Cash", "Mobile Money", "Card" }));
        cmbPaymentFilter.addActionListener(this::cmbPaymentFilterActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(155, 155, 155))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlBARCHART, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(389, 389, 389)
                .addComponent(lblREVENUEBARCHART)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmbPaymentFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(420, 420, 420)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(RevenueScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblREVENUEBARCHART)
                    .addComponent(cmbPaymentFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBARCHART, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RevenueScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateActionPerformed

    private void btnREFRESHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnREFRESHActionPerformed
       
        
        
       loadRevenueStatistics();

loadRevenueTable();

loadRevenueChart();

loadDateTime();
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }//GEN-LAST:event_btnREFRESHActionPerformed

    private void txtTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimeActionPerformed

    private void btnBACKTODASHBOARDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBACKTODASHBOARDActionPerformed
     this.dispose();
    }//GEN-LAST:event_btnBACKTODASHBOARDActionPerformed

    private void btnEXPORTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEXPORTActionPerformed
     
        
        exportRevenueToExcel();
        
        
        
        
        
        
        
    }//GEN-LAST:event_btnEXPORTActionPerformed

    private void btnPRINTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPRINTActionPerformed
      
        
        printRevenueReport();
        
        
        
        
        
    }//GEN-LAST:event_btnPRINTActionPerformed

    private void cmbPaymentFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaymentFilterActionPerformed
    
        
        
        
        loadRevenueTable();
        loadRevenueChart();
        
        
        
        
        
        
    }//GEN-LAST:event_cmbPaymentFilterActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new RevenueDashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane RevenueScrollPane;
    private javax.swing.JButton btnBACKTODASHBOARD;
    private javax.swing.JButton btnEXPORT;
    private javax.swing.JButton btnPRINT;
    private javax.swing.JButton btnREFRESH;
    private javax.swing.JComboBox<String> cmbPaymentFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JLabel lblREVENUEBARCHART;
    private javax.swing.JPanel pnlBARCHART;
    private javax.swing.JTable tblRevenueTable;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
