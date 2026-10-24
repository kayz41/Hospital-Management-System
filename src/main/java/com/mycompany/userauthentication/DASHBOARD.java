package com.mycompany.userauthentication;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Joel Kayz
 */
public class DASHBOARD extends javax.swing.JFrame {
    
    private ExpiryAlertScheduler expiryAlertScheduler;
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DASHBOARD.class.getName());

    /**
     * Creates new form DASHBOARD
     */

    
    
    
    
    
    public DASHBOARD() {
    initComponents();

    setMinimumSize(new java.awt.Dimension(1000, 700));

    loadStatistics();

    applyPermissions();

    jLabel2.setText("WELCOME, " + UserSession.username.toUpperCase());

    lblRole.setText("Role: " + UserSession.role.toUpperCase());

    checkDatabaseStatus();
    lblCurrentUser.setText("User : " + UserSession.username);
    lblCurrentRole.setText("Role : " + UserSession.role);
    lblVersion.setText("Version : 2.0");

    startClock();

    loadChart();
    
    loadNotifications();
    
    startNotificationTicker();
    
      startRecentActivityRefresh();
      
    
      
        expiryAlertScheduler =
            new ExpiryAlertScheduler();

    expiryAlertScheduler.start();
    
    setExtendedState(JFrame.MAXIMIZED_BOTH);
}
    
    
    private Timer recentActivityTimer;
    
    private Timer clockTimer;
    
   private void startRecentActivityRefresh() {

    if (recentActivityTimer != null
            && recentActivityTimer.isRunning()) {
        recentActivityTimer.stop();
    }

    recentActivityTimer = new Timer(3000, e -> {
        loadRecentActivity();
    });

    recentActivityTimer.setRepeats(true);
    recentActivityTimer.start();

    // Load immediately instead of waiting 3 seconds.
    loadRecentActivity();
}
    
    
    
    
    
    private void checkDatabaseStatus() {

    SwingWorker<Boolean, Void> worker = new SwingWorker<>() {

        @Override
        protected Boolean doInBackground() {

            try (Connection con =
                         DatabaseConnection.getConnection()) {

                return con != null && !con.isClosed();

            } catch (Exception e) {

                System.err.println(
                        "Database status check failed: "
                        + e.getMessage()
                );

                return false;
            }
        }

        @Override
        protected void done() {

            try {

                boolean connected = get();

                if (connected) {
                    lblDBStatus.setText(
                            "Database : Connected"
                    );
                } else {
                    lblDBStatus.setText(
                            "Database : Disconnected"
                    );
                }

            } catch (Exception e) {

                lblDBStatus.setText(
                        "Database : Disconnected"
                );

                System.err.println(
                        "Database status UI update failed: "
                        + e.getMessage()
                );
            }
        }
    };

    worker.execute();
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 private void loadNotifications() {

    SwingWorker<String, Void> worker = new SwingWorker<>() {

        @Override
        protected String doInBackground() throws Exception {

            String sqlLowStock =
                    "SELECT COUNT(*) FROM Pharmacy WHERE Quantity < 10";

            String sqlAppointments =
                    "SELECT COUNT(*) FROM Appointments " +
                    "WHERE AppointmentDate = CAST(GETDATE() AS DATE)";

            String sqlUnpaidBills =
                    "SELECT COUNT(*) FROM Billing " +
                    "WHERE PaymentDate IS NULL";

            int lowStock = 0;
            int todayAppointments = 0;
            int unpaidBills = 0;

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement pstLowStock =
                         con.prepareStatement(sqlLowStock);
                 PreparedStatement pstAppointments =
                         con.prepareStatement(sqlAppointments);
                 PreparedStatement pstUnpaidBills =
                         con.prepareStatement(sqlUnpaidBills)) {

                try (ResultSet rs = pstLowStock.executeQuery()) {
                    if (rs.next()) {
                        lowStock = rs.getInt(1);
                    }
                }

                try (ResultSet rs = pstAppointments.executeQuery()) {
                    if (rs.next()) {
                        todayAppointments = rs.getInt(1);
                    }
                }

                try (ResultSet rs = pstUnpaidBills.executeQuery()) {
                    if (rs.next()) {
                        unpaidBills = rs.getInt(1);
                    }
                }
            }

            StringBuilder notificationText =
                    new StringBuilder();

            if (lowStock > 0) {
                notificationText.append(
                        "LOW STOCK: "
                        + lowStock
                        + " medicine item(s) below minimum stock.   "
                );
            }

            if (todayAppointments > 0) {
                notificationText.append(
                        "TODAY'S APPOINTMENTS: "
                        + todayAppointments
                        + ".   "
                );
            }

            if (unpaidBills > 0) {
                notificationText.append(
                        "UNPAID BILLS: "
                        + unpaidBills
                        + ".   "
                );
            }

            if (notificationText.length() == 0) {
                notificationText.append(
                        "No urgent notifications."
                );
            }

            return notificationText.toString();
        }

        @Override
        protected void done() {

            try {

                String notificationText = get();

                txtNotifications.setText(
                        notificationText
                );

            } catch (Exception e) {

                txtNotifications.setText(
                        "Notifications temporarily unavailable."
                );

                System.err.println(
                        "Dashboard notification refresh failed: "
                        + e.getMessage()
                );
            }
        }
    };

    worker.execute();
}
    
    
    
    

private Timer notificationTicker;

private void startNotificationTicker() {
    if (notificationTicker != null) {
        notificationTicker.stop();
    }

    notificationTicker = new Timer(40, new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            JScrollBar horizontal = jScrollPane2.getHorizontalScrollBar();

            int current = horizontal.getValue();
            int max = horizontal.getMaximum() - horizontal.getVisibleAmount();

            if (max <= 0) {
                return;
            }

            if (current >= max) {
                horizontal.setValue(0);
            } else {
                horizontal.setValue(current + 1);
            }
        }
    });

    notificationTicker.start();
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
private void loadChart() {

    SwingWorker<int[], Void> worker = new SwingWorker<>() {

        @Override
        protected int[] doInBackground() throws Exception {

            String[] tables = {
                "Patients",
                "Doctors",
                "Staff",
                "Appointments"
            };

            int[] counts = new int[tables.length];

            try (Connection con =
                         DatabaseConnection.getConnection()) {

                for (int i = 0; i < tables.length; i++) {

                    String sql =
                            "SELECT COUNT(*) FROM " + tables[i];

                    try (PreparedStatement pst =
                                 con.prepareStatement(sql);
                         ResultSet rs =
                                 pst.executeQuery()) {

                        if (rs.next()) {
                            counts[i] = rs.getInt(1);
                        }
                    }
                }
            }

            return counts;
        }

        @Override
        protected void done() {

            try {

                int[] counts = get();

                DefaultCategoryDataset dataset =
                        new DefaultCategoryDataset();

                dataset.addValue(
                        counts[0],
                        "Total",
                        "Patients"
                );

                dataset.addValue(
                        counts[1],
                        "Total",
                        "Doctors"
                );

                dataset.addValue(
                        counts[2],
                        "Total",
                        "Staff"
                );

                dataset.addValue(
                        counts[3],
                        "Total",
                        "Appointments"
                );

                JFreeChart chart =
                        ChartFactory.createBarChart(
                                "Hospital Statistics",
                                "Category",
                                "Count",
                                dataset,
                                PlotOrientation.VERTICAL,
                                true,
                                true,
                                false
                        );

                pnlChart.removeAll();
                pnlChart.setLayout(
                        new BorderLayout()
                );

                pnlChart.add(
                        new ChartPanel(chart),
                        BorderLayout.CENTER
                );

                pnlChart.revalidate();
                pnlChart.repaint();

            } catch (Exception e) {

                System.err.println(
                        "Dashboard chart refresh failed: "
                        + e.getMessage()
                );
            }
        }
    };

    worker.execute();
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 private void startClock() {
    if (clockTimer != null && clockTimer.isRunning()) {
        clockTimer.stop();
    }

    clockTimer = new Timer(1000, e -> {
        Date date = new Date();
        lblDATE.setText(new SimpleDateFormat("dd MMM yyyy").format(date));
        lblTIME.setText(new SimpleDateFormat("hh:mm:ss a").format(date));
    });

    clockTimer.setRepeats(true);
    clockTimer.start();
}
    
    
    
    
    
    
   

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DashboardPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        StudentsCard = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        PayementsCard = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        CoursesCard = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        RecentActivityPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRecentActivity = new javax.swing.JTable();
        btnRefreshActivity = new javax.swing.JButton();
        FooterPanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        lblDBStatus = new javax.swing.JLabel();
        lblCurrentUser = new javax.swing.JLabel();
        lblCurrentRole = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        PayementsCard1 = new javax.swing.JPanel();
        joel = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        CoursesCard2 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        CoursesCard1 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblDATE = new javax.swing.JLabel();
        lblTIME = new javax.swing.JLabel();
        lblHospitalName = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNotifications = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        pnlChart = new javax.swing.JPanel();
        PayementsCard2 = new javax.swing.JPanel();
        joel1 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        CoursesCard4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        StudentPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnDoctors = new javax.swing.JButton();
        btnBilling = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        btnRevenueAnalytics = new javax.swing.JButton();
        btnPatients = new javax.swing.JButton();
        btnReports = new javax.swing.JButton();
        btnAppointments = new javax.swing.JButton();
        btnLaboratory = new javax.swing.JButton();
        btnStaff = new javax.swing.JButton();
        btnUsers = new javax.swing.JButton();
        btnDoctorPerformance = new javax.swing.JButton();
        btnDispenses = new javax.swing.JButton();
        btnMedicalRecords = new javax.swing.JButton();
        btnPharmacy = new javax.swing.JButton();
        btnPharmacyDispensing = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        DashboardPanel.setBackground(new java.awt.Color(102, 102, 102));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("WELCOME, JOEL");

        StudentsCard.setBackground(new java.awt.Color(0, 0, 0));
        StudentsCard.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText(" PATIENTS");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("0");

        javax.swing.GroupLayout StudentsCardLayout = new javax.swing.GroupLayout(StudentsCard);
        StudentsCard.setLayout(StudentsCardLayout);
        StudentsCardLayout.setHorizontalGroup(
            StudentsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentsCardLayout.createSequentialGroup()
                .addGroup(StudentsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(StudentsCardLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(StudentsCardLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        StudentsCardLayout.setVerticalGroup(
            StudentsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentsCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        PayementsCard.setBackground(new java.awt.Color(0, 0, 0));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText(" BILLS");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("0");

        javax.swing.GroupLayout PayementsCardLayout = new javax.swing.GroupLayout(PayementsCard);
        PayementsCard.setLayout(PayementsCardLayout);
        PayementsCardLayout.setHorizontalGroup(
            PayementsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PayementsCardLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(PayementsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        PayementsCardLayout.setVerticalGroup(
            PayementsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PayementsCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        CoursesCard.setBackground(new java.awt.Color(0, 0, 0));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("0");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("DOCTORS");

        javax.swing.GroupLayout CoursesCardLayout = new javax.swing.GroupLayout(CoursesCard);
        CoursesCard.setLayout(CoursesCardLayout);
        CoursesCardLayout.setHorizontalGroup(
            CoursesCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoursesCardLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(CoursesCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        CoursesCardLayout.setVerticalGroup(
            CoursesCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoursesCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        RecentActivityPanel.setBackground(new java.awt.Color(0, 0, 0));
        RecentActivityPanel.setForeground(new java.awt.Color(204, 204, 204));
        RecentActivityPanel.setName(""); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("RECENT ACTIVITY");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblRecentActivity.setBackground(new java.awt.Color(204, 204, 204));
        tblRecentActivity.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tblRecentActivity.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Log ID", "Username", "Action", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblRecentActivity);

        btnRefreshActivity.setBackground(new java.awt.Color(102, 102, 102));
        btnRefreshActivity.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefreshActivity.setForeground(new java.awt.Color(255, 255, 255));
        btnRefreshActivity.setText("Refresh Activity");
        btnRefreshActivity.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnRefreshActivity.addActionListener(this::btnRefreshActivityActionPerformed);

        javax.swing.GroupLayout RecentActivityPanelLayout = new javax.swing.GroupLayout(RecentActivityPanel);
        RecentActivityPanel.setLayout(RecentActivityPanelLayout);
        RecentActivityPanelLayout.setHorizontalGroup(
            RecentActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecentActivityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RecentActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RecentActivityPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RecentActivityPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRefreshActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        RecentActivityPanelLayout.setVerticalGroup(
            RecentActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecentActivityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefreshActivity)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FooterPanel.setBackground(new java.awt.Color(0, 0, 0));
        FooterPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        FooterPanel.setForeground(new java.awt.Color(204, 204, 204));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 204, 102));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("2026 PATIENT MANAGEMENT SYSTEM");

        lblDBStatus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDBStatus.setForeground(new java.awt.Color(255, 204, 102));
        lblDBStatus.setText("Database");

        lblCurrentUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCurrentUser.setForeground(new java.awt.Color(255, 204, 102));
        lblCurrentUser.setText("User");

        lblCurrentRole.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCurrentRole.setForeground(new java.awt.Color(255, 204, 102));
        lblCurrentRole.setText("Role");

        lblVersion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVersion.setForeground(new java.awt.Color(255, 204, 102));
        lblVersion.setText("Version");

        javax.swing.GroupLayout FooterPanelLayout = new javax.swing.GroupLayout(FooterPanel);
        FooterPanel.setLayout(FooterPanelLayout);
        FooterPanelLayout.setHorizontalGroup(
            FooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FooterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FooterPanelLayout.createSequentialGroup()
                        .addComponent(lblDBStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(FooterPanelLayout.createSequentialGroup()
                        .addComponent(lblCurrentUser, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblCurrentRole, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblVersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(237, 237, 237))
        );
        FooterPanelLayout.setVerticalGroup(
            FooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FooterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblDBStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(2, 2, 2)
                .addGroup(FooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCurrentUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCurrentRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblVersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel16.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Future Modules Area");

        jLabel17.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 204, 102));
        jLabel17.setText("HOSPITAL MANAGEMENT");

        PayementsCard1.setBackground(new java.awt.Color(0, 0, 0));

        joel.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        joel.setForeground(new java.awt.Color(255, 255, 255));
        joel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        joel.setText("0");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText(" Appointments");

        javax.swing.GroupLayout PayementsCard1Layout = new javax.swing.GroupLayout(PayementsCard1);
        PayementsCard1.setLayout(PayementsCard1Layout);
        PayementsCard1Layout.setHorizontalGroup(
            PayementsCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PayementsCard1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(joel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(PayementsCard1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PayementsCard1Layout.setVerticalGroup(
            PayementsCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PayementsCard1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(joel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        lblRole.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblRole.setForeground(new java.awt.Color(255, 255, 255));
        lblRole.setText("ROLE");

        CoursesCard2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText(" USERS");

        jLabel23.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("0");

        javax.swing.GroupLayout CoursesCard2Layout = new javax.swing.GroupLayout(CoursesCard2);
        CoursesCard2.setLayout(CoursesCard2Layout);
        CoursesCard2Layout.setHorizontalGroup(
            CoursesCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoursesCard2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(CoursesCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        CoursesCard2Layout.setVerticalGroup(
            CoursesCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoursesCard2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        CoursesCard1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText(" STAFF");

        jLabel21.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("0");

        javax.swing.GroupLayout CoursesCard1Layout = new javax.swing.GroupLayout(CoursesCard1);
        CoursesCard1.setLayout(CoursesCard1Layout);
        CoursesCard1Layout.setHorizontalGroup(
            CoursesCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoursesCard1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(CoursesCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        CoursesCard1Layout.setVerticalGroup(
            CoursesCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoursesCard1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblDATE.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblDATE.setForeground(new java.awt.Color(255, 255, 255));
        lblDATE.setText("DATE");

        lblTIME.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTIME.setForeground(new java.awt.Color(255, 255, 255));
        lblTIME.setText("TIME");

        lblHospitalName.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblHospitalName.setForeground(new java.awt.Color(255, 255, 255));
        lblHospitalName.setText("ISBAT MEDICAL CENTRE");

        txtNotifications.setEditable(false);
        txtNotifications.setBackground(new java.awt.Color(153, 153, 153));
        txtNotifications.setColumns(20);
        txtNotifications.setRows(5);
        txtNotifications.setWrapStyleWord(true);
        txtNotifications.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(102, 102, 102)));
        jScrollPane2.setViewportView(txtNotifications);

        pnlChart.setBackground(new java.awt.Color(204, 204, 204));
        pnlChart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnlChartLayout = new javax.swing.GroupLayout(pnlChart);
        pnlChart.setLayout(pnlChartLayout);
        pnlChartLayout.setHorizontalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 974, Short.MAX_VALUE)
        );
        pnlChartLayout.setVerticalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(pnlChart);

        PayementsCard2.setBackground(new java.awt.Color(0, 0, 0));

        joel1.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        joel1.setForeground(new java.awt.Color(255, 255, 255));
        joel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        joel1.setText("0");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Dispenses");

        javax.swing.GroupLayout PayementsCard2Layout = new javax.swing.GroupLayout(PayementsCard2);
        PayementsCard2.setLayout(PayementsCard2Layout);
        PayementsCard2Layout.setHorizontalGroup(
            PayementsCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PayementsCard2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PayementsCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PayementsCard2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(joel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel24))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        PayementsCard2Layout.setVerticalGroup(
            PayementsCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PayementsCard2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(joel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        CoursesCard4.setBackground(new java.awt.Color(0, 0, 0));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("0");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Frame");

        javax.swing.GroupLayout CoursesCard4Layout = new javax.swing.GroupLayout(CoursesCard4);
        CoursesCard4.setLayout(CoursesCard4Layout);
        CoursesCard4Layout.setHorizontalGroup(
            CoursesCard4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoursesCard4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(CoursesCard4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        CoursesCard4Layout.setVerticalGroup(
            CoursesCard4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoursesCard4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DashboardPanelLayout = new javax.swing.GroupLayout(DashboardPanel);
        DashboardPanel.setLayout(DashboardPanelLayout);
        DashboardPanelLayout.setHorizontalGroup(
            DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardPanelLayout.createSequentialGroup()
                .addGap(324, 324, 324)
                .addComponent(lblHospitalName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DashboardPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addComponent(RecentActivityPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DashboardPanelLayout.createSequentialGroup()
                        .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(FooterPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DashboardPanelLayout.createSequentialGroup()
                        .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(DashboardPanelLayout.createSequentialGroup()
                                .addComponent(StudentsCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(CoursesCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(CoursesCard4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(PayementsCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(PayementsCard1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DashboardPanelLayout.createSequentialGroup()
                                .addComponent(lblRole, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(106, 106, 106)
                                .addComponent(lblDATE, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(103, 103, 103)
                                .addComponent(lblTIME, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PayementsCard2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(CoursesCard2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(CoursesCard1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)))
                .addContainerGap())
        );

        DashboardPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CoursesCard, CoursesCard1, CoursesCard2, CoursesCard4, PayementsCard, PayementsCard2, StudentsCard});

        DashboardPanelLayout.setVerticalGroup(
            DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(DashboardPanelLayout.createSequentialGroup()
                            .addComponent(lblRole)
                            .addGap(4, 4, 4))
                        .addComponent(lblDATE, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(lblTIME))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(StudentsCard, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PayementsCard1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PayementsCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CoursesCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CoursesCard2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CoursesCard1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PayementsCard2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CoursesCard4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHospitalName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RecentActivityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FooterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        DashboardPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {CoursesCard, CoursesCard1, CoursesCard2, PayementsCard, PayementsCard1, PayementsCard2, StudentsCard});

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        StudentPanel.setBackground(new java.awt.Color(0, 0, 0));
        StudentPanel.setPreferredSize(new java.awt.Dimension(251, 730));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PATIENT");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnDoctors.setBackground(new java.awt.Color(102, 102, 102));
        btnDoctors.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDoctors.setForeground(new java.awt.Color(255, 255, 255));
        btnDoctors.setText("Doctors");
        btnDoctors.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDoctors.setFocusPainted(false);
        btnDoctors.addActionListener(this::btnDoctorsActionPerformed);

        btnBilling.setBackground(new java.awt.Color(102, 102, 102));
        btnBilling.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBilling.setForeground(new java.awt.Color(255, 255, 255));
        btnBilling.setText("Billing");
        btnBilling.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBilling.setFocusPainted(false);
        btnBilling.addActionListener(this::btnBillingActionPerformed);

        btnLogout.setBackground(new java.awt.Color(102, 102, 102));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("MANAGEMENT");
        jLabel15.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnRevenueAnalytics.setBackground(new java.awt.Color(102, 102, 102));
        btnRevenueAnalytics.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnRevenueAnalytics.setForeground(new java.awt.Color(255, 255, 255));
        btnRevenueAnalytics.setText("Revenue Analytics");
        btnRevenueAnalytics.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRevenueAnalytics.setFocusPainted(false);
        btnRevenueAnalytics.addActionListener(this::btnRevenueAnalyticsActionPerformed);

        btnPatients.setBackground(new java.awt.Color(102, 102, 102));
        btnPatients.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPatients.setForeground(new java.awt.Color(255, 255, 255));
        btnPatients.setText("Patients");
        btnPatients.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPatients.setFocusPainted(false);
        btnPatients.addActionListener(this::btnPatientsActionPerformed);

        btnReports.setBackground(new java.awt.Color(102, 102, 102));
        btnReports.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReports.setForeground(new java.awt.Color(255, 255, 255));
        btnReports.setText("Reports");
        btnReports.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnReports.setFocusPainted(false);
        btnReports.addActionListener(this::btnReportsActionPerformed);

        btnAppointments.setBackground(new java.awt.Color(102, 102, 102));
        btnAppointments.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAppointments.setForeground(new java.awt.Color(255, 255, 255));
        btnAppointments.setText("Appointments");
        btnAppointments.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAppointments.setFocusPainted(false);
        btnAppointments.addActionListener(this::btnAppointmentsActionPerformed);

        btnLaboratory.setBackground(new java.awt.Color(102, 102, 102));
        btnLaboratory.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLaboratory.setForeground(new java.awt.Color(255, 255, 255));
        btnLaboratory.setText("Laboratory");
        btnLaboratory.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLaboratory.setFocusPainted(false);
        btnLaboratory.addActionListener(this::btnLaboratoryActionPerformed);

        btnStaff.setBackground(new java.awt.Color(102, 102, 102));
        btnStaff.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnStaff.setForeground(new java.awt.Color(255, 255, 255));
        btnStaff.setText("Staff");
        btnStaff.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnStaff.setFocusPainted(false);
        btnStaff.addActionListener(this::btnStaffActionPerformed);

        btnUsers.setBackground(new java.awt.Color(102, 102, 102));
        btnUsers.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnUsers.setForeground(new java.awt.Color(255, 255, 255));
        btnUsers.setText("Users");
        btnUsers.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUsers.setFocusPainted(false);
        btnUsers.addActionListener(this::btnUsersActionPerformed);

        btnDoctorPerformance.setBackground(new java.awt.Color(102, 102, 102));
        btnDoctorPerformance.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDoctorPerformance.setForeground(new java.awt.Color(255, 255, 255));
        btnDoctorPerformance.setText("Doctor Performance");
        btnDoctorPerformance.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDoctorPerformance.addActionListener(this::btnDoctorPerformanceActionPerformed);

        btnDispenses.setBackground(new java.awt.Color(102, 102, 102));
        btnDispenses.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDispenses.setForeground(new java.awt.Color(255, 255, 255));
        btnDispenses.setText("Dispenses");
        btnDispenses.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDispenses.addActionListener(this::btnDispensesActionPerformed);

        btnMedicalRecords.setBackground(new java.awt.Color(102, 102, 102));
        btnMedicalRecords.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMedicalRecords.setForeground(new java.awt.Color(255, 255, 255));
        btnMedicalRecords.setText("Medical Records");
        btnMedicalRecords.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnMedicalRecords.addActionListener(this::btnMedicalRecordsActionPerformed);

        btnPharmacy.setBackground(new java.awt.Color(102, 102, 102));
        btnPharmacy.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPharmacy.setForeground(new java.awt.Color(255, 255, 255));
        btnPharmacy.setText("Pharmacy");
        btnPharmacy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPharmacy.addActionListener(this::btnPharmacyActionPerformed);

        btnPharmacyDispensing.setBackground(new java.awt.Color(102, 102, 102));
        btnPharmacyDispensing.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPharmacyDispensing.setForeground(new java.awt.Color(255, 255, 255));
        btnPharmacyDispensing.setText("Pharmacy Dispensing");
        btnPharmacyDispensing.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPharmacyDispensing.addActionListener(this::btnPharmacyDispensingActionPerformed);

        javax.swing.GroupLayout StudentPanelLayout = new javax.swing.GroupLayout(StudentPanel);
        StudentPanel.setLayout(StudentPanelLayout);
        StudentPanelLayout.setHorizontalGroup(
            StudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(StudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(StudentPanelLayout.createSequentialGroup()
                        .addGroup(StudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(StudentPanelLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel1)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(StudentPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(StudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPharmacy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDoctors, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(btnRevenueAnalytics, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(btnPharmacyDispensing, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMedicalRecords, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPatients, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAppointments, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUsers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBilling, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReports, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLaboratory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnStaff, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDispenses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDoctorPerformance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jLayeredPane1)
                        .addGap(18, 18, 18))))
        );
        StudentPanelLayout.setVerticalGroup(
            StudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addGroup(StudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(StudentPanelLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLayeredPane1)
                        .addGap(484, 484, 484))
                    .addGroup(StudentPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnRevenueAnalytics, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDoctors, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPharmacy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPharmacyDispensing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnMedicalRecords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPatients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAppointments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBilling, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLaboratory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDispenses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(btnDoctorPerformance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(74, 74, 74))))
        );

        jScrollPane3.setViewportView(StudentPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DashboardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
            .addComponent(DashboardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActivityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActivityActionPerformed
      
          loadStatistics();
    loadNotifications();
    loadRecentActivity();

        
        
    }//GEN-LAST:event_btnRefreshActivityActionPerformed

    private void btnDoctorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoctorsActionPerformed
        if (UserSession.role.equalsIgnoreCase("Admin")) {

            Doctors c = new Doctors();

            c.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Access Denied!"
            );

        }
    }//GEN-LAST:event_btnDoctorsActionPerformed

    private void btnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBillingActionPerformed

        if (UserSession.role.equalsIgnoreCase("Admin")) {

            Billing b = new Billing();

            b.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Access Denied!"
            );

        }
    }//GEN-LAST:event_btnBillingActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed

        int choice =
        JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Logout",
            JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {

            /*
            * =====================================
            * STOP SESSION TIMEOUT
            * =====================================
            */
           // Stop dashboard background processes
if (recentActivityTimer != null) {
    recentActivityTimer.stop();
}

if (notificationTicker != null) {
    notificationTicker.stop();
}

if (expiryAlertScheduler != null) {
    expiryAlertScheduler.stop();
}

if (clockTimer != null) clockTimer.stop();

SessionManager.stopSession();

            /*
            * =====================================
            * RECORD LOGOUT
            * =====================================
            */

            AuditLogger.log(
                "User Logged Out: "
                + UserSession.username);

            /*
            * =====================================
            * CLEAR USER SESSION
            * =====================================
            */

            UserSession.clearSession();

            Login.loggedUsername = "";
            Login.loggedRole = "";

            /*
            * =====================================
            * RETURN TO LOGIN
            * =====================================
            */

            Login login =
            new Login();

            login.setVisible(true);

            /*
            * Close dashboard.
            */

            this.dispose();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnRevenueAnalyticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevenueAnalyticsActionPerformed

        if (UserSession.role.equalsIgnoreCase("Admin")) {

            RevenueDashboard revenue = new RevenueDashboard();

            revenue.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Access Denied!"
            );

        }
    }//GEN-LAST:event_btnRevenueAnalyticsActionPerformed

    private void btnPatientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPatientsActionPerformed
        Patients ms = new Patients();
        ms.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPatientsActionPerformed

    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportsActionPerformed

        Report r = new Report();

        r.setVisible(true);
    }//GEN-LAST:event_btnReportsActionPerformed

    private void btnAppointmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAppointmentsActionPerformed

       if( UserSession.role.equalsIgnoreCase("Admin")){
           Appointments a = new Appointments();
           
         a.setVisible(true);
           
       } else {
           
           JOptionPane.showMessageDialog(
                   this,
                   "Access Denied"
           );
       }
       
    }//GEN-LAST:event_btnAppointmentsActionPerformed

    private void btnLaboratoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaboratoryActionPerformed

        Laboratory L = new Laboratory();

        L.setVisible(true);
    }//GEN-LAST:event_btnLaboratoryActionPerformed

    private void btnStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaffActionPerformed

        if (UserSession.role.equalsIgnoreCase("Admin")) {

            Staff S = new Staff();

            S.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Access Denied!"
            );

        }
    }//GEN-LAST:event_btnStaffActionPerformed

    private void btnUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsersActionPerformed

        if (UserSession.role.equalsIgnoreCase("Admin")) {

            Users u = new Users();

            u.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Access Denied!"
            );

        }
    }//GEN-LAST:event_btnUsersActionPerformed

    private void btnDoctorPerformanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoctorPerformanceActionPerformed

        if (UserSession.role.equalsIgnoreCase("Admin")) {

            DoctorPerformance doctor = new DoctorPerformance();

            doctor.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Access Denied!"
            );

        }
    }//GEN-LAST:event_btnDoctorPerformanceActionPerformed

    private void btnDispensesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDispensesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDispensesActionPerformed

    private void btnMedicalRecordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMedicalRecordsActionPerformed
        // TODO add your handling code hMedicalRecords medicalRecords = new MedicalRecords();
InternationalMedicalRecords medicalRecords = new InternationalMedicalRecords();
medicalRecords.setVisible(true);






    }//GEN-LAST:event_btnMedicalRecordsActionPerformed

    private void btnPharmacyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPharmacyActionPerformed
       Pharmacy pharmacy = new Pharmacy();
    pharmacy.setVisible(true);
    }//GEN-LAST:event_btnPharmacyActionPerformed

    private void btnPharmacyDispensingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPharmacyDispensingActionPerformed
      PharmacyDispensing pharmacyDispensing = new PharmacyDispensing();
    pharmacyDispensing.setVisible(true);
    }//GEN-LAST:event_btnPharmacyDispensingActionPerformed

    
    
 private void applyPermissions() {

    String role = UserSession.role;

    if (role == null) {
        role = "";
    }

    role = role.trim();

    boolean isAdmin = role.equalsIgnoreCase("Admin");

    btnRevenueAnalytics.setVisible(isAdmin);
    btnDoctors.setVisible(isAdmin);
    btnAppointments.setVisible(isAdmin);
    btnUsers.setVisible(isAdmin);
    btnBilling.setVisible(isAdmin);
    btnStaff.setVisible(isAdmin);
    btnDoctorPerformance.setVisible(isAdmin);

    // These modules are currently available to non-admin users
    // according to the existing dashboard structure.
    btnPatients.setVisible(true);
    btnReports.setVisible(true);
    btnLaboratory.setVisible(true);
}
    
    
    
    
    
    
    
    
    
    
   private void loadStatistics() {

    SwingWorker<int[], Void> worker = new SwingWorker<>() {

        @Override
        protected int[] doInBackground() throws Exception {

            int[] statistics = new int[7];

            String sqlPatients =
                    "SELECT COUNT(*) FROM Patients";

            String sqlDoctors =
                    "SELECT COUNT(*) FROM Doctors";

            String sqlBilling =
                    "SELECT COUNT(*) FROM Billing";

            String sqlAppointments =
                    "SELECT COUNT(*) FROM Appointments";

            String sqlStaff =
                    "SELECT COUNT(*) FROM Staff";

            String sqlUsers =
                    "SELECT COUNT(*) FROM Users";

            String sqlDispenses =
                    "SELECT COUNT(*) FROM PharmacyDispensing";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement pstPatients =
                         con.prepareStatement(sqlPatients);
                 PreparedStatement pstDoctors =
                         con.prepareStatement(sqlDoctors);
                 PreparedStatement pstBilling =
                         con.prepareStatement(sqlBilling);
                 PreparedStatement pstAppointments =
                         con.prepareStatement(sqlAppointments);
                 PreparedStatement pstStaff =
                         con.prepareStatement(sqlStaff);
                 PreparedStatement pstUsers =
                         con.prepareStatement(sqlUsers);
                 PreparedStatement pstDispenses =
                         con.prepareStatement(sqlDispenses)) {

                try (ResultSet rs = pstPatients.executeQuery()) {
                    if (rs.next()) {
                        statistics[0] = rs.getInt(1);
                    }
                }

                try (ResultSet rs = pstDoctors.executeQuery()) {
                    if (rs.next()) {
                        statistics[1] = rs.getInt(1);
                    }
                }

                try (ResultSet rs = pstBilling.executeQuery()) {
                    if (rs.next()) {
                        statistics[2] = rs.getInt(1);
                    }
                }

                try (ResultSet rs = pstAppointments.executeQuery()) {
                    if (rs.next()) {
                        statistics[3] = rs.getInt(1);
                    }
                }

                try (ResultSet rs = pstStaff.executeQuery()) {
                    if (rs.next()) {
                        statistics[4] = rs.getInt(1);
                    }
                }

                try (ResultSet rs = pstUsers.executeQuery()) {
                    if (rs.next()) {
                        statistics[5] = rs.getInt(1);
                    }
                }

                try (ResultSet rs = pstDispenses.executeQuery()) {
                    if (rs.next()) {
                        statistics[6] = rs.getInt(1);
                    }
                }
            }

            return statistics;
        }

        @Override
        protected void done() {

            try {

                int[] statistics = get();

                int patients = statistics[0];
                int doctors = statistics[1];
                int billing = statistics[2];
                int appointments = statistics[3];
                int staff = statistics[4];
                int users = statistics[5];
                int dispenses = statistics[6];

                // UI updates happen safely on the EDT here.
                animateCounter(jLabel7, patients);
                jLabel11.setText(String.valueOf(doctors));
                jLabel9.setText(String.valueOf(billing));
                joel.setText(String.valueOf(appointments));
                jLabel21.setText(String.valueOf(staff));
                jLabel23.setText(String.valueOf(users));
                joel1.setText(String.valueOf(dispenses));

            } catch (Exception e) {

                System.err.println(
                        "Dashboard statistics refresh failed: "
                        + e.getMessage()
                );
            }
        }
    };

    worker.execute();
}
    
    
    
   private void animateCounter(javax.swing.JLabel label, int target) {

    final int[] current = {0};

    javax.swing.Timer animationTimer =
            new javax.swing.Timer(20, null);

    animationTimer.addActionListener(e -> {

        if (current[0] >= target) {

            label.setText(String.valueOf(target));

            animationTimer.stop();

            return;
        }

        current[0]++;

        label.setText(
                String.valueOf(current[0])
        );
    });

    animationTimer.start();
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   private volatile boolean activityRefreshRunning = false;

private void loadRecentActivity() {

    if (activityRefreshRunning) {
        return;
    }

    activityRefreshRunning = true;

    SwingWorker<java.util.List<Object[]>, Void> worker =
            new SwingWorker<>() {

        @Override
        protected java.util.List<Object[]> doInBackground() throws Exception {

            java.util.List<Object[]> rows = new java.util.ArrayList<>();

            String sql =
                    "SELECT TOP 20 AuditID, Username, ActionPerformed, ActionDate " +
                    "FROM AuditTrail " +
                    "ORDER BY ActionDate DESC";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {

                    rows.add(new Object[]{
                        rs.getInt("AuditID"),
                        rs.getString("Username"),
                        rs.getString("ActionPerformed"),
                        rs.getTimestamp("ActionDate")
                    });
                }
            }

            return rows;
        }

        @Override
        protected void done() {

            try {

                java.util.List<Object[]> rows = get();

                DefaultTableModel model =
                        (DefaultTableModel) tblRecentActivity.getModel();

                model.setRowCount(0);

                for (Object[] row : rows) {
                    model.addRow(row);
                }

            } catch (Exception e) {

                System.err.println(
                        "Recent activity refresh failed: "
                        + e.getMessage()
                );

            } finally {

                activityRefreshRunning = false;
            }
        }
    };

    worker.execute();
}
                
            
        
    
    
    
    
    
    
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
        java.awt.EventQueue.invokeLater(() -> new DASHBOARD().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CoursesCard;
    private javax.swing.JPanel CoursesCard1;
    private javax.swing.JPanel CoursesCard2;
    private javax.swing.JPanel CoursesCard4;
    private javax.swing.JPanel DashboardPanel;
    private javax.swing.JPanel FooterPanel;
    private javax.swing.JPanel PayementsCard;
    private javax.swing.JPanel PayementsCard1;
    private javax.swing.JPanel PayementsCard2;
    private javax.swing.JPanel RecentActivityPanel;
    private javax.swing.JPanel StudentPanel;
    private javax.swing.JPanel StudentsCard;
    private javax.swing.JButton btnAppointments;
    private javax.swing.JButton btnBilling;
    private javax.swing.JButton btnDispenses;
    private javax.swing.JButton btnDoctorPerformance;
    private javax.swing.JButton btnDoctors;
    private javax.swing.JButton btnLaboratory;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMedicalRecords;
    private javax.swing.JButton btnPatients;
    private javax.swing.JButton btnPharmacy;
    private javax.swing.JButton btnPharmacyDispensing;
    private javax.swing.JButton btnRefreshActivity;
    private javax.swing.JButton btnReports;
    private javax.swing.JButton btnRevenueAnalytics;
    private javax.swing.JButton btnStaff;
    private javax.swing.JButton btnUsers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel joel;
    private javax.swing.JLabel joel1;
    private javax.swing.JLabel lblCurrentRole;
    private javax.swing.JLabel lblCurrentUser;
    private javax.swing.JLabel lblDATE;
    private javax.swing.JLabel lblDBStatus;
    private javax.swing.JLabel lblHospitalName;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblTIME;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JTable tblRecentActivity;
    private javax.swing.JTextArea txtNotifications;
    // End of variables declaration//GEN-END:variables
}
