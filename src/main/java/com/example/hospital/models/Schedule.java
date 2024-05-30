package com.example.hospital.models;

public class Schedule {
    private String scheduleId;
    private String patientId;
    private String doctorId;
    private String datetime;
    private String location;
    private String doctorName;
    private String patientName;
    public Schedule() {
    }



    public Schedule(String scheduleId,String patientId, String doctorId, String date, String location) {
        this.scheduleId = scheduleId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.datetime = date;
        this.location = location;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", datetime='" + datetime + '\'' +
                ", location='" + location + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", patientName='" + patientName + '\'' +
                '}';
    }
}
