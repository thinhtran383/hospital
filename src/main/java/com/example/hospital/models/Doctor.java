package com.example.hospital.models;


public class Doctor extends Person {
    private String doctorId;
    private String specialization;
    private String department;
    private String email;

    public Doctor() {
    }

    public Doctor(String name, String age, String address, String gender, String phoneNumber, String doctorId, String specialization, String department, String email) {
        super(name, age, address, gender, phoneNumber);
        this.doctorId = doctorId;
        this.specialization = specialization;
        this.department = department;
        this.email = email;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                super.toString() +
                "doctorId='" + doctorId + '\'' +
                ", specialization='" + specialization + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
