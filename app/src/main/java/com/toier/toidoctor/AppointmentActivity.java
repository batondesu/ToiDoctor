
package com.toier.toidoctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.toier.toidoctor.controllers.BookingController;
import com.toier.toidoctor.R.id;
import com.toier.toidoctor.R.layout;

import com.google.firebase.Timestamp;

public class AppointmentActivity extends AppCompatActivity {
    private TextView tvDisplayDateTime;
    private BookingController booking = new BookingController(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_appointment);


        Intent intent = this.getIntent();
        String ID = intent.getStringExtra("DOCTOR_ID");
        Log.d("VVV", ID);
        booking.getDoctorData(ID, new BookingController.OnDoctorDataListener() {
            @Override
            public void onDoctorDataReceived(Doctor doctor) {
                // Hiển thị tên của Doctor vào TextView
                TextView tvDoctorName = findViewById(id.sc_doctor_name);
                tvDoctorName.setText(doctor.getName());

                TextView tvDoctorMajor = findViewById(id.sc_majors);
                tvDoctorMajor.setText(doctor.getMajor());

                TextView tvDoctorRate = findViewById(id.sc_rate);
                tvDoctorRate.setText(String.format("%.1f",doctor.getRate()));

                TextView tvDoctorReview = findViewById(id.sc_review);
                tvDoctorReview.setText(String.format("(%d Đánh giá)",doctor.getReview()));

                TextView tvDoctorAddress = findViewById(id.sc_address);
                tvDoctorAddress.setText(doctor.getAddress());

                TextView tvDoctorHospital = findViewById(id.sc_hospital_name);
                tvDoctorHospital.setText(doctor.getHospital_name());

            }
            @Override
            public void onDoctorDataError(String errorMessage) {
                // Xử lý lỗi nếu có
            }
        });

        Button button_back_main_activity = findViewById(R.id.tro_ve_trang_chu);
        button_back_main_activity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent3 = new Intent(AppointmentActivity.this, MainHomeActivity.class);
                startActivity(intent3);
            }
        });

        int day = intent.getIntExtra("DAY", 0);
        String month = intent.getStringExtra("MONTH");
        int year = intent.getIntExtra("YEAR", 0);
        int hour = intent.getIntExtra("HOUR", 0);
        int intM = intent.getIntExtra("INTMONTH" , 0);


        TextView tv = (TextView) findViewById(id.sc_day);
        tv.setText(String.format("%d %s, %d", day, month, year));

        TextView tv1 = findViewById(id.sc_hour);
        tv1.setText(String.format("%d:00", hour*2 + 7));

        Timestamp timestamp = booking.convertToTimestamp(day, intM + 1, year, hour*2 + 7);

        Log.d("FFF", timestamp.toString() );

        booking.addBookingData(ID, "1", timestamp);
    }

}
