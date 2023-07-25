
package com.toier.toidoctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.toier.toidoctor.Controller.BookingController;
import com.toier.toidoctor.R.id;
import com.toier.toidoctor.R.layout;

public class AppointmentActivity extends AppCompatActivity {
    private TextView tvDisplayDateTime;
    private BookingController booking = new BookingController(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_appointment);
        this.tvDisplayDateTime = (TextView)this.findViewById(id.tvDisplayDateTime);
        Intent intent = this.getIntent();
        if (intent != null) {
            String selectedDateTime = intent.getStringExtra("SELECTED_DATE_TIME");

            Log.d("QWE", "=>" + selectedDateTime);
            if (selectedDateTime != null) {
                this.tvDisplayDateTime.setText("Ngày hẹn khám của bạn: \n" + selectedDateTime);
            }
        }

        Button button_back_main_activity = findViewById(R.id.tro_ve_trang_chu);
        button_back_main_activity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent3 = new Intent(AppointmentActivity.this, MainHomeActivity.class);
                startActivity(intent3);
            }
        });




            String ID = getIntent().getStringExtra("ID_DOCTOR");
            Log.d("QWE", ID);
            booking.getDoctorData(ID, new BookingController.OnDoctorDataListener() {
                @Override
                public void onDoctorDataReceived(Doctor doctor) {
                    // Hiển thị tên của Doctor vào TextView
                    TextView tvDoctorName = findViewById(id.sc_name);
                    tvDoctorName.setText(doctor.getName());

                    TextView tvDoctorMajor = findViewById(id.sc_major);
                    tvDoctorMajor.setText(doctor.getMajor());

                    //TextView tvDoctorPatient = findViewById(id.sc);
                    //tvDoctorPatient.setText(String.format("+%d",doctor.getPatient()));

                    TextView tvDoctorReview = findViewById(id.sc_review);
                    tvDoctorReview.setText(String.format("%d Đánh giá",doctor.getReview()));

                    TextView tvDoctorRate = findViewById(id.sc_rate);
                    tvDoctorRate.setText(String.format("%.1f",doctor.getRate()));

                    TextView tvDoctorAbout = findViewById(id.about_doctor);
                    //tvDoctorAbout.setText(doctor.getAbout_doctor());

                    TextView tvDoctorAddress = findViewById(id.address);
                    //tvDoctorName.setText(doctor.getAddress());

                    TextView tvDoctorHospital = findViewById(id.hospital_name);
                    //tvDoctorName.setText(doctor.getHospital_name());

                }

                @Override
                public void onDoctorDataError(String errorMessage) {
                    // Xử lý lỗi nếu có
                }
            });
        }

}
