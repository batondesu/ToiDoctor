package com.toier.toidoctor;

public class Appointment {
    private String doctor_id;
    private String patient_id;
    private String schedule;

    public Appointment(){}

    public Appointment(String doctor_id, String patient_id, String schedule) {
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.schedule = schedule;
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

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
