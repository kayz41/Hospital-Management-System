package com.mycompany.userauthentication;

public class PharmacyDispensingModel {

    private int dispensingID;
    private int patientID;
    private String patientName;
    private int doctorID;
    private String doctorName;
    private int medicineID;
    private String medicineName;
    private int quantity;
    private String dispensingDate;
    private String status;
private int recordID;


public int getRecordID() {
    return recordID;
}

public void setRecordID(int recordID) {
    this.recordID = recordID;
}

    public int getDispensingID() {
        return dispensingID;
    }

    public void setDispensingID(int dispensingID) {
        this.dispensingID = dispensingID;
    }


    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }


    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }


    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }


    public int getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }


    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String getDispensingDate() {
        return dispensingDate;
    }

    public void setDispensingDate(String dispensingDate) {
        this.dispensingDate = dispensingDate;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}