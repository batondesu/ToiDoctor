package com.toier.toidoctor;

import java.lang.ref.Reference;

public class Doctor {
    private String name;
    private String department;
    private String about_doctor;
    private Reference id;
    private int count_patient;
    private int exp;
    private int rate;
    private int review;
    private String hospital;

    public Doctor() {
        this.name = name;
        this.id = id;
        this.hospital = hospital;
    }

    public Doctor(String name) {
        this.name = name;
    }

    public int getCount_patient() {
        return count_patient;
    }

    public int getExp() {
        return exp;
    }

    public int getRate() {
        return rate;
    }

    public int getReview() {
        return review;
    }

    public Reference getId() {
        return id;
    }

    public String getAbout_doctor() {
        return about_doctor;
    }

    public String getDepartment() {
        return department;
    }

    public String getHospital() {
        return hospital;
    }

    public String getName() {
        return name;
    }

    public void setAbout_doctor(String about_doctor) {
        this.about_doctor = about_doctor;
    }

    public void setCount_patient(int count_patient) {
        this.count_patient = count_patient;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setId(Reference id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setReview(int review) {
        this.review = review;
    }
}

