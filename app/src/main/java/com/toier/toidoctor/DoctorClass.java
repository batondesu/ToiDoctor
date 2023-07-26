package com.toier.toiDoctorClass;

import java.lang.ref.Reference;

public class DoctorClass {
    private String name;
    private String department;
    private String about_DoctorClass;
    private Reference id;
    private int count_patient;
    private int exp;
    private int rate;
    private int review;
    private String hospital;

    public DoctorClass() {
        this.name = name;
        this.id = id;
        this.hospital = hospital;
    }
    
    public DoctorClass(String name, String department) {
        this.name = name;
        this.department = department;
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

    public String getAbout_DoctorClass() {
        return about_DoctorClass;
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

    public void setAbout_DoctorClass(String about_DoctorClass) {
        this.about_DoctorClass = about_DoctorClass;
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

