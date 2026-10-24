package com.mycompany.userauthentication;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import com.google.zxing.WriterException;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;
import java.awt.print.PrinterJob;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;

import com.itextpdf.io.image.ImageDataFactory;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;

/**
 *
 * @author Joel Kayz
 */
public class Invoice extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Invoice.class.getName());

    /**
     * Creates new form Invoice
     */
  public Invoice() {
    initComponents();
}

public Invoice(String billingID,
               String patientID,
               String patientName,
               String amount,
               String paymentMethod,
               String paymentDate) {

    initComponents();

    lblBillingID.setText(billingID);
    lblPatientID.setText(patientID);
    lblPatientName.setText(patientName);
    lblAmount.setText("UGX " + amount);
    lblPaymentMethod.setText(paymentMethod);
    lblPaymentDate.setText(paymentDate);
    
    String qrData =
        "KAYZ INTERNATIONAL HOSPITAL\n\n"

        + "Billing ID: " + billingID + "\n"

        + "Patient ID: " + patientID + "\n"

        + "Patient Name: " + patientName + "\n"

        + "Amount: UGX " + amount + "\n"

        + "Payment Method: " + paymentMethod + "\n"

        + "Payment Date: " + paymentDate + "\n\n"

        + "Thank you for choosing us.";

generateQRCode(qrData);
generateBarcode("KAYZ-" + billingID);
}







private void generateBarcode(String data) {

    try {

        if (data == null || data.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Barcode data cannot be empty.");

            return;

        }

        BitMatrix matrix =
                new MultiFormatWriter().encode(
                        data,
                        BarcodeFormat.CODE_128,
                        350,
                        80);

        BufferedImage image =
                MatrixToImageWriter.toBufferedImage(matrix);

        java.awt.Image scaledImage =
        image.getScaledInstance(
                lblBarcode.getWidth(),
                lblBarcode.getHeight(),
                java.awt.Image.SCALE_SMOOTH);
        lblBarcode.setIcon(new ImageIcon(scaledImage));

        lblBarcode.setText("");

    }
    catch (WriterException e) {

        JOptionPane.showMessageDialog(
                this,
                "Barcode Generation Error:\n"
                + e.getMessage());

    }
    catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Unexpected Error:\n"
                + e.getMessage());

    }

}






















private void generateQRCode(String data) {

    try {

        if (data == null || data.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "QR Code data cannot be empty.");

            return;

        }

        BitMatrix matrix =
                new MultiFormatWriter().encode(
                        data,
                        BarcodeFormat.QR_CODE,
                        200,
                        200);

        BufferedImage image =
                MatrixToImageWriter.toBufferedImage(matrix);

        java.awt.Image scaledImage =
                image.getScaledInstance(
                        lblQRCode.getWidth(),
                        lblQRCode.getHeight(),
                        java.awt.Image.SCALE_SMOOTH);

        lblQRCode.setIcon(new ImageIcon(scaledImage));
        lblQRCode.setText("");

    }
    catch (WriterException e) {

        JOptionPane.showMessageDialog(
                this,
                "QR Code Generation Error:\n"
                + e.getMessage());

    }
    catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Unexpected Error:\n"
                + e.getMessage());

    }

}




private byte[] getQRCodeBytes() {

    try {

        ImageIcon icon =
                (ImageIcon) lblQRCode.getIcon();

        if (icon == null) {
            return null;
        }

        BufferedImage image =
                new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_RGB);

        image.getGraphics().drawImage(
                icon.getImage(),
                0,
                0,
                null);

        ByteArrayOutputStream baos =
                new ByteArrayOutputStream();

        ImageIO.write(
                image,
                "png",
                baos);

        return baos.toByteArray();

    }
    catch (Exception e) {

        return null;

    }

}







private byte[] getBarcodeBytes() {

    try {

        ImageIcon icon =
                (ImageIcon) lblBarcode.getIcon();

        if (icon == null) {
            return null;
        }

        BufferedImage image =
                new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_RGB);

        image.getGraphics().drawImage(
                icon.getImage(),
                0,
                0,
                null);

        ByteArrayOutputStream baos =
                new ByteArrayOutputStream();

        ImageIO.write(
                image,
                "png",
                baos);

        return baos.toByteArray();

    }
    catch (Exception e) {

        return null;

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

        pnlInvoice = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblHospital = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnPRINTINVOICE = new javax.swing.JButton();
        btnCLOSE = new javax.swing.JButton();
        btnEXPORTPDF = new javax.swing.JButton();
        lblBillingID = new javax.swing.JLabel();
        lblPatientName = new javax.swing.JLabel();
        lblPatientID = new javax.swing.JLabel();
        lblAmount = new javax.swing.JLabel();
        lblPaymentMethod = new javax.swing.JLabel();
        lblPaymentDate = new javax.swing.JLabel();
        lblQRCode = new javax.swing.JLabel();
        lblBarcode = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlInvoice.setBackground(new java.awt.Color(0, 0, 0));
        pnlInvoice.setForeground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setForeground(new java.awt.Color(102, 102, 102));

        lblHospital.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblHospital.setForeground(new java.awt.Color(255, 255, 255));
        lblHospital.setText("KAYZ INTERNATIONAL HOSPITAL");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(267, Short.MAX_VALUE)
                .addComponent(lblHospital)
                .addGap(263, 263, 263))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(lblHospital)
                .addGap(41, 41, 41))
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(102, 102, 102));

        btnPRINTINVOICE.setBackground(new java.awt.Color(102, 102, 102));
        btnPRINTINVOICE.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPRINTINVOICE.setForeground(new java.awt.Color(255, 255, 255));
        btnPRINTINVOICE.setText("PRINT INVOICE");
        btnPRINTINVOICE.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnPRINTINVOICE.addActionListener(this::btnPRINTINVOICEActionPerformed);

        btnCLOSE.setBackground(new java.awt.Color(102, 102, 102));
        btnCLOSE.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCLOSE.setForeground(new java.awt.Color(255, 255, 255));
        btnCLOSE.setText("CLOSE");
        btnCLOSE.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnCLOSE.addActionListener(this::btnCLOSEActionPerformed);

        btnEXPORTPDF.setBackground(new java.awt.Color(102, 102, 102));
        btnEXPORTPDF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEXPORTPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnEXPORTPDF.setText("EXPORT PDF");
        btnEXPORTPDF.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnEXPORTPDF.addActionListener(this::btnEXPORTPDFActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(btnPRINTINVOICE, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(140, 140, 140)
                .addComponent(btnEXPORTPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCLOSE, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCLOSE, btnEXPORTPDF, btnPRINTINVOICE});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPRINTINVOICE)
                    .addComponent(btnCLOSE)
                    .addComponent(btnEXPORTPDF))
                .addGap(34, 34, 34))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCLOSE, btnEXPORTPDF, btnPRINTINVOICE});

        lblBillingID.setForeground(new java.awt.Color(255, 255, 255));

        lblPatientName.setForeground(new java.awt.Color(255, 255, 255));

        lblPatientID.setForeground(new java.awt.Color(255, 255, 255));

        lblAmount.setForeground(new java.awt.Color(255, 255, 255));
        lblAmount.setText(" ");

        lblPaymentMethod.setForeground(new java.awt.Color(255, 255, 255));

        lblPaymentDate.setForeground(new java.awt.Color(255, 255, 255));

        lblQRCode.setForeground(new java.awt.Color(255, 255, 255));
        lblQRCode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQRCode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblQRCode.setMaximumSize(new java.awt.Dimension(50, 50));
        lblQRCode.setMinimumSize(new java.awt.Dimension(50, 50));

        lblBarcode.setForeground(new java.awt.Color(255, 255, 255));
        lblBarcode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblBarcode.setMaximumSize(new java.awt.Dimension(100, 70));
        lblBarcode.setMinimumSize(new java.awt.Dimension(100, 70));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Thank you for choosing KAYZ INTERNATIONAL HOSPITAL.  We wish you a quick recovery.");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("BillingID");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PatientID");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("PateintName");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("AmountPaid");

        jLabel6.setText("jLabel6");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("PaymentMethod");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("PaymentDate");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("QRCode");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Barcode");

        javax.swing.GroupLayout pnlInvoiceLayout = new javax.swing.GroupLayout(pnlInvoice);
        pnlInvoice.setLayout(pnlInvoiceLayout);
        pnlInvoiceLayout.setHorizontalGroup(
            pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInvoiceLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164))
            .addGroup(pnlInvoiceLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlInvoiceLayout.createSequentialGroup()
                        .addGroup(pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addGap(283, 283, 283)
                .addGroup(pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBillingID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPatientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPatientID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPaymentDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblQRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPaymentMethod, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlInvoiceLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblAmount, lblBarcode, lblBillingID, lblPatientID, lblPatientName, lblPaymentDate, lblPaymentMethod, lblQRCode});

        pnlInvoiceLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel2, jLabel3, jLabel5, jLabel9});

        pnlInvoiceLayout.setVerticalGroup(
            pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInvoiceLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBillingID, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(36, 36, 36)
                .addGroup(pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPatientID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(26, 26, 26)
                .addGroup(pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPatientName, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(28, 28, 28)
                .addGroup(pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(32, 32, 32)
                .addGroup(pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(44, 44, 44)
                .addGroup(pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(47, 47, 47)
                .addGroup(pnlInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(3, 3, 3)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlInvoiceLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel2, jLabel3, jLabel4, jLabel5, jLabel7, jLabel8, jLabel9, lblAmount, lblBarcode, lblBillingID, lblPatientID, lblPatientName, lblPaymentDate, lblPaymentMethod, lblQRCode});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPRINTINVOICEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPRINTINVOICEActionPerformed
   


    PrinterJob job = PrinterJob.getPrinterJob();

    job.setJobName("Hospital Invoice");

    job.setPrintable(new Printable() {

        @Override
        public int print(Graphics graphics,
                         PageFormat pageFormat,
                         int pageIndex)
                throws PrinterException {

            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2 = (Graphics2D) graphics;

           double panelWidth = pnlInvoice.getWidth();
double panelHeight = pnlInvoice.getHeight();

double pageWidth = pageFormat.getImageableWidth();
double pageHeight = pageFormat.getImageableHeight();

double scale = Math.min(
        pageWidth / panelWidth,
        pageHeight / panelHeight);



g2.translate(
        pageFormat.getImageableX(),
        pageFormat.getImageableY());

g2.scale(scale, scale);

pnlInvoice.printAll(g2);
            return Printable.PAGE_EXISTS;
        }
    });

    if (job.printDialog()) {

        try {

            // Hide the buttons while printing
            jPanel3.setVisible(false);
            pnlInvoice.revalidate();
            pnlInvoice.repaint();

            // Print the invoice
            job.print();

            // Show the buttons again
            jPanel3.setVisible(true);
            pnlInvoice.revalidate();
            pnlInvoice.repaint();

            JOptionPane.showMessageDialog(
                    this,
                    "Invoice Printed Successfully!");

        } catch (PrinterException ex) {

            // Make sure the buttons return even if printing fails
            jPanel3.setVisible(true);
            pnlInvoice.revalidate();
            pnlInvoice.repaint();

            JOptionPane.showMessageDialog(
                    this,
                    "Printing Error: " + ex.getMessage());
        }

    }




    
    }//GEN-LAST:event_btnPRINTINVOICEActionPerformed

    private void btnCLOSEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCLOSEActionPerformed
     

    dispose();


    }//GEN-LAST:event_btnCLOSEActionPerformed

    private void btnEXPORTPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEXPORTPDFActionPerformed
  


    try {

        JFileChooser chooser = new JFileChooser();

        chooser.setDialogTitle("Save Hospital Invoice");

        chooser.setSelectedFile(
                new java.io.File("Hospital_Invoice.pdf"));

        chooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                        "PDF Files (*.pdf)", "pdf"));

        int option = chooser.showSaveDialog(this);

        if (option != JFileChooser.APPROVE_OPTION) {
            return;
        }

        java.io.File file = chooser.getSelectedFile();

        if (!file.getName().toLowerCase().endsWith(".pdf")) {
            file = new java.io.File(file.getAbsolutePath() + ".pdf");
        }

        if (file.exists()) {

            int overwrite = JOptionPane.showConfirmDialog(
                    this,
                    "The file already exists.\n\nDo you want to replace it?",
                    "Confirm Overwrite",
                    JOptionPane.YES_NO_OPTION);

            if (overwrite != JOptionPane.YES_OPTION) {
                return;
            }
        }

        //==================================================
        // DEBUG (You can remove these later)
        //==================================================

        System.out.println("Billing ID   : " + lblBillingID.getText());
        System.out.println("Patient ID   : " + lblPatientID.getText());
        System.out.println("Patient Name : " + lblPatientName.getText());
        System.out.println("Amount       : " + lblAmount.getText());
        System.out.println("Method       : " + lblPaymentMethod.getText());
        System.out.println("Date         : " + lblPaymentDate.getText());

        //==================================================
        // Create PDF
        //==================================================

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);

        pdf.getDocumentInfo()
                .setTitle("Hospital Payment Invoice")
                .setAuthor("KAYZ INTERNATIONAL HOSPITAL")
                .setCreator("PROJECT UPGRADE")
                .setSubject("Official Hospital Invoice")
                .setKeywords("Hospital, Billing, Invoice");

        Document document = new Document(pdf);

        //==================================================
        // Header
        //==================================================

        document.add(
                new Paragraph("KAYZ INTERNATIONAL HOSPITAL")
                        .setBold()
                        .setFontSize(22)
                        .setTextAlignment(TextAlignment.CENTER));

        document.add(
                new Paragraph("OFFICIAL PAYMENT INVOICE")
                        .setFontSize(13)
                        .setTextAlignment(TextAlignment.CENTER));

        document.add(
                new Paragraph("Generated On : "
                        + java.time.LocalDate.now())
                        .setTextAlignment(TextAlignment.RIGHT));

        document.add(new LineSeparator(new SolidLine()));

        document.add(new Paragraph(" "));

        //==================================================
        // Patient Information
        //==================================================

        document.add(
                new Paragraph("PATIENT INFORMATION")
                        .setBold()
                        .setFontSize(15));

        Table patientTable =
                new Table(UnitValue.createPercentArray(new float[]{35, 65}));

        patientTable.setWidth(UnitValue.createPercentValue(100));

        String billingText = lblBillingID.getText().trim();

        if (billingText.isEmpty()) {
            billingText = "000000";
        }

        patientTable.addCell(new Cell().add(new Paragraph("Invoice Number")));
        patientTable.addCell(new Cell().add(new Paragraph("INV-2026-" + billingText)));

        patientTable.addCell(new Cell().add(new Paragraph("Patient ID")));
        patientTable.addCell(new Cell().add(new Paragraph(lblPatientID.getText())));

        patientTable.addCell(new Cell().add(new Paragraph("Patient Name")));
        patientTable.addCell(new Cell().add(new Paragraph(lblPatientName.getText())));

        document.add(patientTable);

        document.add(new Paragraph(" "));

        //==================================================
        // Payment Information
        //==================================================

        document.add(
                new Paragraph("PAYMENT INFORMATION")
                        .setBold()
                        .setFontSize(15));

        Table paymentTable =
                new Table(UnitValue.createPercentArray(new float[]{35, 65}));

        paymentTable.setWidth(UnitValue.createPercentValue(100));

        paymentTable.addCell(new Cell().add(new Paragraph("Amount")));
        paymentTable.addCell(new Cell().add(new Paragraph(lblAmount.getText())));

        paymentTable.addCell(new Cell().add(new Paragraph("Payment Method")));
        paymentTable.addCell(new Cell().add(new Paragraph(lblPaymentMethod.getText())));

        paymentTable.addCell(new Cell().add(new Paragraph("Payment Date")));
        paymentTable.addCell(new Cell().add(new Paragraph(lblPaymentDate.getText())));

        document.add(paymentTable);

        document.add(new Paragraph(" "));

        document.add(new LineSeparator(new SolidLine()));

        document.add(new Paragraph(" "));

        //==================================================
        // Footer
        //==================================================

        document.add(
                new Paragraph(
                        "Thank you for choosing\n\n"
                        + "KAYZ INTERNATIONAL HOSPITAL\n\n"
                        + "CONFIDENTIAL MEDICAL BILLING DOCUMENT\n"
                        + "For Authorized Use Only.")
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph(" "));

        document.add(
                new Paragraph("Page 1 of 1")
                        .setTextAlignment(TextAlignment.RIGHT));

        document.close();

        JOptionPane.showMessageDialog(
                this,
                "Invoice exported successfully!\n\nLocation:\n"
                + file.getAbsolutePath());

        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop.getDesktop().open(file);
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "PDF Export Error:\n\n" + e.getMessage());

        e.printStackTrace();
    }



    }//GEN-LAST:event_btnEXPORTPDFActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Invoice().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCLOSE;
    private javax.swing.JButton btnEXPORTPDF;
    private javax.swing.JButton btnPRINTINVOICE;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblBarcode;
    private javax.swing.JLabel lblBillingID;
    private javax.swing.JLabel lblHospital;
    private javax.swing.JLabel lblPatientID;
    private javax.swing.JLabel lblPatientName;
    private javax.swing.JLabel lblPaymentDate;
    private javax.swing.JLabel lblPaymentMethod;
    private javax.swing.JLabel lblQRCode;
    private javax.swing.JPanel pnlInvoice;
    // End of variables declaration//GEN-END:variables
}
