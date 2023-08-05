package com.toier.toidoctor.controller;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.*;
import com.google.firebase.firestore.*;
import com.toier.toidoctor.Appointment;
import com.toier.toidoctor.Doctor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BookingController {
    private Context context;

    public BookingController(Context context) {
        this.context = context;
    }

    // Phương thức này để lấy thông tin Doctor từ Firestore
    public void getDoctorData(String doctorId, OnDoctorDataListener listener) {
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


    public Timestamp convertToTimestamp(int day, int month, int year, int hour) {
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
        Timestamp timestamp = new Timestamp(date.getTime());

        return timestamp;
    }

    public void addDoctorData() {
        //name,  major,  review,  rate,  ID,  address,  hospital_name,  about_doctor,  exp,  patient
        AddBacSi("Cao Xuân Dũng", "Phụ khoa", 99, 4.1, "13" , "2B, ngõ 25, Phạm Thuận Duật", "Bệnh viện Mai Dịch" , "uy tín", 7,203);
        AddBacSi("Lê Thạch Lâm", "Tâm lý", 67, 4.7, "14" , "Nguyễn Chí Thanh, Láng Thượng, Đống Đa", "Bệnh viện Bạch Mai" , "Đăng C - Hiếu Hoàng", 7,163);
        AddBacSi("Nguyễn Trung Kiên", "Ngoại khoa", 197, 4.9, "15" , "Nguyễn Trãi, Thanh Xuân Trung, Thanh Xuân", "Bệnh viện Thanh Xuân" , "giàu", 8,503);
        AddBacSi("Lưu Trường Giang", "Nam khoa", 8, 3.9, "16" , "Văn Quán, Hà Đông", "Bệnh viện Thanh Xuân" , "Láng Lọ, giỏi làm", 11,20);
        AddBacSi("Nguyễn Quang Huy", "Thể chất", 200, 4.6, "17" , "207 Giải Phóng, Đồng Tâm, Hai Bà Trưng", "Bệnh viện Cầu Giấy" , "người đàn ông chung thủy, có 10 cho 10", 12,403);
        AddBacSi("Nguyễn Văn Nam", "Phục hồi chức năng", 77, 4.9, "18" , "Phố Trần Đại Nghĩa, Hai Bà Trưng", "Bệnh viện Ba Đình" , "lương y như từ mâu, người thầy thuốc tài giỏi", 8,150);
        AddBacSi("Phùng Thanh Đăng", "Cấp cứu", 301, 4.0, "19" , "Phố Trần Đại Nghĩa, Hai Bà Trưng", "Bệnh viện Ba Đình" , "con bố Long", 5,603);
        AddBacSi("Trần Như Long", "Truyền nhiễm", 123, 4.2, "20" , "136 Phạm Văn Đồng, Xuân Đỉnh, Bắc Từ Liêm", "Bệnh viện Cổ Nhuế" , "nhiều bạn ỏ Tài Chính", 5,246);


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