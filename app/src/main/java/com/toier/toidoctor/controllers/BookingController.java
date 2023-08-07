package com.toier.toidoctor.controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.*;
import com.google.firebase.firestore.*;
import com.toier.toidoctor.Doctor;

import com.google.firebase.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookingController {
    private Context context;

    public BookingController(Context context) {
        this.context = context;
    }

    // Phương thức này để lấy thông tin Doctor từ Firestore
    public static void getDoctorData(String doctorId, OnDoctorDataListener listener) {
        //Log.d("TEST_", "co");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Doctors")
                .whereEqualTo("ID", doctorId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                //listDoctor.clear(); // Xóa danh sách hiện tại để tránh trùng lặp
                                for (DocumentSnapshot document : querySnapshot.getDocuments()) {

                                    //get thong tin doctor
                                    Doctor doctor = new Doctor();
                                    doctor.setAddress(document.get("address").toString());
                                    doctor.setHospital_name(document.get("hospital_name").toString());
                                    doctor.setID(document.get("ID").toString());
                                    doctor.setAbout_doctor(document.get("about_doctor").toString());
                                    doctor.setName(document.get("name").toString());
                                    doctor.setMajor(document.get("major").toString());
                                    double rate = document.getDouble("rate");
                                    doctor.setRate(rate);
                                    long res = document.getLong("review");
                                    doctor.setReview((int) res);
                                    long res1 = document.getLong("exp");
                                    doctor.setExp((int) res1);
                                    long res2 = document.getLong("patient");
                                    doctor.setPatient((int) res2);
                                    //Log.d("TEST_", "co");
                                    listener.onDoctorDataReceived(doctor);
                                    //Log.d("XXX", doctor.getName().toString());
                                }

                            } else {
                                listener.onDoctorDataError("Không tìm thấy thông tin Doctor");
                            }
                        } else {
                            listener.onDoctorDataError("Lỗi khi lấy dữ liệu từ Firestore");
                        }
                    }
                });
    }

    // Tạo interface để lắng nghe dữ liệu Doctor
    public interface OnDoctorDataListener {
        void onDoctorDataReceived(Doctor doctor);

        void onDoctorDataError(String errorMessage);
    }

    public void addBookingData(String doctorId, String patientId, Timestamp time) {
        // Lấy tham chiếu đến Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("doctor_id", doctorId);
        user.put("patient_id", patientId);
        user.put("time", time);
        user.put("notice", false);

        // Add a new document with a generated ID
        db.collection("Appointments")
            .add(user)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("TAG", "Error adding document", e);
                }
            });
    }


    public static Timestamp convertToTimestamp(int day, int month, int year, int hour) {
        // Tạo một đối tượng Calendar và thiết lập các thông số
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // Vì Calendar.MONTH bắt đầu từ 0 (0 = tháng 1)
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Lấy Date từ Calendar
        Date date = calendar.getTime();

        // Tạo Timestamp từ Date
        Timestamp timestamp = new Timestamp(date);

        return timestamp;
    }

    public void AddBacSi(String name, String major, int review, double rate, String ID, String address, String hospital_name, String about_doctor, int exp, int patient) {
        // Lấy tham chiếu đến Firestore
        // Lấy tham chiếu đến Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("ID", ID);
        user.put("about_doctor", about_doctor);
        user.put("name", name);
        user.put("major", major);
        user.put("review", review);
        user.put("rate", rate);
        user.put("address", address);
        user.put("hospital_name", hospital_name);
        user.put("exp", exp);
        user.put("patient", patient);

        // Add a new document with a generated ID
        db.collection("Doctors")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

}