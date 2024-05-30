package com.example.hospital.models;

public class Patient extends Person {
    private String patientId;
    private String bloodGroup;
    private String medicalHistory;


    public Patient() {
    }

    public Patient(String name, String age, String address, String gender, String phoneNumber, String patientId, String bloodGroup, String medicalHistory) {
        super(name, age, address, gender, phoneNumber);
        this.patientId = patientId;
        this.bloodGroup = bloodGroup;
        this.medicalHistory = medicalHistory;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String toString() {
        return "Patient{" + super.toString() +
                "patientId='" + patientId + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                '}';
    }
}
