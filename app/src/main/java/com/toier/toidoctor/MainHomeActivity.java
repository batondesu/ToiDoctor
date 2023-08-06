package com.toier.toidoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.toier.toidoctor.controller.BookingController;
import com.toier.toidoctor.controller.MainHomeController;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainHomeActivity extends AppCompatActivity {

    private Button button;
    private ConstraintLayout booking;
    private ConstraintLayout medicalRecord;

    private boolean role = false;
    private String ID = "1";
    private Date date;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        MainHomeController listDoctorController = new MainHomeController(this);
        if (!role) {
            listDoctorController.getAppointment(ID, role, new MainHomeController.OnAppointmentDataListener() {
                @Override
                public void onAppointmentDataReceived(Appointment appointment) {
                    if (appointment.getDoctor_id() != "") {
                        addDoctor(appointment.getDoctor_id());
                    }

                    if ( appointment.getTimestamp() != null ) {

                        Log.d("DD", "duoc khong");

                        Date data = appointment.getTimestamp().toDate();
                        Calendar cl = Calendar.getInstance();
                        cl.setTime(data);

                        int date = cl.get(Calendar.DAY_OF_MONTH);
                        int hour = cl.get(Calendar.HOUR);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                        String month = dateFormat.format(cl.getTime());

                        TextView sc = (TextView) findViewById(R.id.home_schedule);
                        sc.setText(String.format("%d %s", date, month));

                        TextView sc1 = (TextView) findViewById(R.id.home_time);
                        sc1.setText(String.format("%d:00", hour));
                    }
                    else {
                        Log.d("CCC", "duoc khong");
                    }
                }
                @Override
                public void onAppointmentDataError(String errorMessage) {
                }
            });


            button = (Button)findViewById(R.id.allButton_pt);
            booking = (ConstraintLayout) findViewById(R.id.bookingClinic1);
            medicalRecord = (ConstraintLayout) findViewById(R.id.medical_record);

            // chuyển sang danh sách bác sĩ
            booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainHomeActivity.this, ListDoctorActivity.class);
                    startActivity(intent);
                }
            });

            // chuyển sang danh sách bệnh nhân
            medicalRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainHomeActivity.this, PatientProfileActivity.class);
                    startActivity(intent);
                }
            });
            // chuyển sang danh sách bác sĩ
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainHomeActivity.this, ListDoctorActivity.class);
                    startActivity(intent);
                }
            });
            // Khởi tạo ListDoctorContr
            // Tìm ListView trong layout và thiết lập Adapter cho nó
            ListView listView = findViewById(R.id.listdoctor);
            listDoctorController.fetchDoctorsFromFirestore(listView);
        }
    }

    public void addDoctor(String ID) {
        BookingController control = new BookingController(this);
        control.getDoctorData(ID, new BookingController.OnDoctorDataListener() {
            @Override
            public void onDoctorDataReceived(Doctor doctor) {
                // Hiển thị tên của Doctor vào TextView
                TextView tvDoctorName = findViewById(R.id.home_name);
                tvDoctorName.setText(doctor.getName());

                TextView tvDoctorMajor = findViewById(R.id.home_major);
                tvDoctorMajor.setText(doctor.getMajor());

                TextView tvDoctorExp = findViewById(R.id.home_review);
                tvDoctorExp.setText(String.format("(%d Đánh giá)", doctor.getReview()));

                TextView tvDoctorRate = findViewById(R.id.home_rate);
                tvDoctorRate.setText(String.format("%.1f", doctor.getRate()));
            }

            @Override
            public void onDoctorDataError(String errorMessage) {
                // Xử lý lỗi nếu có
            }
        });
    }
}