package com.toier.toidoctor;

import java.io.Serializable;

public class Doctor implements Serializable {
    String name;
    String major;
    double rate;
    int review;
    String ID;

    int exp;
    String hospital_name;
    String about_doctor;
    int patient;
    String address;

    public Doctor() {
    }

    public Doctor(String name, String major, int review, double rate, String doctor_ID, String address, String hospital_name, String about_doctor, int exp, int patient) {
        this.name = name;
        this.rate = rate;
        this.major = major;
        this.review = review;
        this.ID = doctor_ID;
        this.patient = patient;
        this.exp = exp;
        this.address = address;
        this.hospital_name = hospital_name;
        this.about_doctor = about_doctor;
    }

    public void setAbout_doctor(String about_doctor) {
        this.about_doctor = about_doctor;
    }

    public String getAbout_doctor() {
        return about_doctor;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public int getPatient() {
        return patient;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExp() {
        return exp;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public double getRate() {
        return rate;
    }

    public int getReview() {
        return review;
    }

    public String getMajor() {
        return major;
    }

    public String getName() {
        return name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setReview(int review) {
        this.review = review;
    }
}
