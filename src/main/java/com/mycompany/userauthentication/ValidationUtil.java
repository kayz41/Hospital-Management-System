package com.mycompany.userauthentication;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ValidationUtil {

    // ==========================================
    // Check required text field
    // ==========================================

    public static boolean required(
            JTextField field,
            String fieldName) {

        if (field.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    fieldName + " is required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            field.requestFocus();

            return false;
        }

        return true;
    }


    // ==========================================
    // Check required ComboBox
    // ==========================================

    public static boolean required(
            JComboBox<?> comboBox,
            String fieldName) {

        if (comboBox.getSelectedItem() == null
                || comboBox.getSelectedItem()
                        .toString()
                        .trim()
                        .isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Please select " + fieldName + ".",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            comboBox.requestFocus();

            return false;
        }

        return true;
    }


    // ==========================================
    // Check integer
    // ==========================================

    public static boolean integer(
            JTextField field,
            String fieldName) {

        if (field.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    fieldName + " is required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            field.requestFocus();

            return false;
        }

        try {

            Integer.parseInt(
                    field.getText().trim());

            return true;

        }

        catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    fieldName + " must be a valid number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            field.requestFocus();

            return false;
        }
    }


    // ==========================================
    // Check decimal number
    // ==========================================

    public static boolean decimal(
            JTextField field,
            String fieldName) {

        if (field.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    fieldName + " is required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            field.requestFocus();

            return false;
        }

        try {

            Double.parseDouble(
                    field.getText().trim());

            return true;

        }

        catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    fieldName + " must be a valid number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            field.requestFocus();

            return false;
        }
    }


    // ==========================================
    // Check positive integer
    // ==========================================

    public static boolean positiveInteger(
            JTextField field,
            String fieldName) {

        if (!integer(field, fieldName)) {
            return false;
        }

        int value =
                Integer.parseInt(
                        field.getText().trim());

        if (value <= 0) {

            JOptionPane.showMessageDialog(
                    null,
                    fieldName
                    + " must be greater than zero.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            field.requestFocus();

            return false;
        }

        return true;
    }


    // ==========================================
    // Check positive decimal
    // ==========================================

    public static boolean positiveDecimal(
            JTextField field,
            String fieldName) {

        if (!decimal(field, fieldName)) {
            return false;
        }

        double value =
                Double.parseDouble(
                        field.getText().trim());

        if (value <= 0) {

            JOptionPane.showMessageDialog(
                    null,
                    fieldName
                    + " must be greater than zero.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            field.requestFocus();

            return false;
        }

        return true;
    }


    // ==========================================
    // Check email
    // ==========================================

    public static boolean email(
            JTextField field) {

        String email =
                field.getText().trim();

        if (email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Email address is required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            field.requestFocus();

            return false;
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            JOptionPane.showMessageDialog(
                    null,
                    "Please enter a valid email address.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            field.requestFocus();

            return false;
        }

        return true;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // ==========================================
// Check optional date
// Format: yyyy-MM-dd
// ==========================================

public static boolean optionalDate(
        JTextField field,
        String fieldName) {

    String value =
            field.getText().trim();

    // Empty is allowed because
    // PaymentDate can be NULL for unpaid bills.
    if (value.isEmpty()) {

        return true;
    }

    try {

        java.time.LocalDate.parse(
                value,
                java.time.format.DateTimeFormatter
                        .ofPattern("yyyy-MM-dd")
        );

        return true;

    }

    catch (java.time.format.DateTimeParseException e) {

        JOptionPane.showMessageDialog(
                null,
                fieldName
                + " must use the format yyyy-MM-dd.",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);

        field.requestFocus();

        return false;
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}