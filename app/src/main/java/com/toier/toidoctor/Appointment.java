package com.toier.toidoctor;

import java.sql.Timestamp;

public class Appointment {
    private String doctor_id;
    private String patient_id;
    private String schedule;

    private Timestamp timestamp;
    public Appointment(){}

    public Appointment(String doctor_id, String patient_id, Timestamp timestamp) {
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.timestamp = timestamp;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
