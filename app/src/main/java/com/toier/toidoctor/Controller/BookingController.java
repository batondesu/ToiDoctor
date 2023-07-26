package com.toier.toidoctor.Controller;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.*;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.*;
import com.google.firebase.firestore.*;
import com.toier.toidoctor.Appointment;
import com.toier.toidoctor.BookingClinicActivity;
import com.toier.toidoctor.Doctor;
import com.toier.toidoctor.R.id;
import com.toier.toidoctor.R.layout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
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
                                    doctor.setDoctor_ID(document.get("ID").toString());
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
                                    Log.d("XXX", doctor.getName().toString());
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

    public void addBookingData(String doctorId, String patientId, String schedule) {
        // Lấy tham chiếu đến Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Tạo một đối tượng Booking để đưa lên Firestore
        Appointment booking = new Appointment(doctorId, patientId, schedule);

        // Thêm dữ liệu vào collection "bookings" hoặc collection tùy chọn khác
        db.collection("Appointments") // Thay "bookings" bằng tên collection bạn muốn lưu dữ liệu
                .add(booking)
                .addOnSuccessListener(documentReference -> {
                    // Xử lý thành công khi thêm dữ liệu vào Firestore
                    // (documentReference.getId() trả về ID của tài liệu vừa được thêm)
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi nếu thêm dữ liệu không thành công
                });
    }
}