//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.toier.toidoctor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.*;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.*;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.errorprone.annotations.FormatString;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.*;
import com.toier.toidoctor.controller.BookingController;
import com.toier.toidoctor.R.id;
import com.toier.toidoctor.R.layout;

import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class BookingClinicActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private TextView textView;
    private int nDay;
    private String nMonth;
    private int nYear;
    private int nHour;
    private String ID;
    private int intm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_booking_clinic);

        BookingController booking = new BookingController(this);
        Intent intent = this.getIntent();
        ID = intent.getStringExtra("KEY_VALUE");
        //booking.addDoctorData();

        setPickTime(ID);

        if (intent != null) {
            Log.d("ACB", ID);
            booking.getDoctorData(ID, new BookingController.OnDoctorDataListener() {
                @Override
                public void onDoctorDataReceived(Doctor doctor) {
                    // Hiển thị tên của Doctor vào TextView
                    TextView tvDoctorName = findViewById(id.doctor_name);
                    tvDoctorName.setText(doctor.getName());

                    TextView tvDoctorMajor = findViewById(id.doctor_major);
                    tvDoctorMajor.setText(doctor.getMajor());

                    TextView tvDoctorPatient = findViewById(id.count_patient);
                    tvDoctorPatient.setText(String.format("+%d",doctor.getPatient()));

                    TextView tvDoctorExp = findViewById(id.count_exp);
                    tvDoctorExp.setText(String.format("+%d years",doctor.getExp()));

                    TextView tvDoctorRate = findViewById(id.doctor_rate1);
                    tvDoctorRate.setText(String.format("%.1f",doctor.getRate()));

                    TextView tvDoctorAbout = findViewById(id.about_doctor);
                    tvDoctorAbout.setText(doctor.getAbout_doctor());

                    TextView tvDoctorAddress = findViewById(id.address);
                    tvDoctorAddress.setText(doctor.getAddress());

                    TextView tvDoctorHospital = findViewById(id.hospital_name);
                    tvDoctorHospital.setText(doctor.getHospital_name());

                }
                @Override
                public void onDoctorDataError(String errorMessage) {
                    // Xử lý lỗi nếu có
                }
            });
        }

        Button bt = (Button) findViewById(id.set_booking);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent( BookingClinicActivity.this, AppointmentActivity.class);
                inte.putExtra("DOCTOR_ID", ID);
                inte.putExtra("DAY" , nDay);
                inte.putExtra("MONTH" , nMonth);
                inte.putExtra("YEAR" , nYear);
                inte.putExtra("HOUR" , nHour);
                inte.putExtra("INTMONTH", intm);
                startActivity(inte);
            }
        });
    }

    private void setPickTime(String ID) {

        int[][] check = new int[40][10];
        for (int i = 0; i < check.length; i++) {
            for (int j = 0; j < check[i].length; j++) {
                check[i][j] = 0;
            }
        }

        // lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int m = calendar.get(Calendar.MONTH);
        int years = calendar.get(Calendar.YEAR);
        intm = m;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        String month = dateFormat.format(calendar.getTime());

        // check click
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Appointments")
                .whereEqualTo("doctor_id", ID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                    if ( document.getTimestamp("time") != null ) {
                                        Timestamp timestamp = document.getTimestamp("time");
                                        timestamp.toDate();
                                        // Chuyển đổi Timestamp thành Date
                                        Date date = timestamp.toDate();

                                        // Lấy thông tin tháng từ Date
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(date);
                                        int month = calendar.get(Calendar.MONTH);
                                        int day = calendar.get(Calendar.DATE);
                                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                                        if (m == month) {
                                            check[day][(hours - 7) / 2] = 1;
                                        }
                                    }
                                    //Log.d("AAA", String.format("%d %d %d", ))
                                }

                            } else {
                                //listener.onDoctorDataError("Không tìm thấy thông tin Doctor");
                            }
                        } else {
                            //listener.onDoctorDataError("Lỗi khi lấy dữ liệu từ Firestore");
                        }
                    }
                });

        // set ngày cho textview
        LinearLayout linearLayout = findViewById(id.parent_layout);
        int childCount = linearLayout.getChildCount();

        for(int i = 0 ; i < childCount ; ++i) {
            int now = i;
            ConstraintLayout layout = (ConstraintLayout) linearLayout.getChildAt(i);
            TextView text = (TextView) layout.getChildAt(1);
            text.setText(String.format("%d\n%s",date + now,month));

            View view = (View) layout.getChildAt(0);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // đặt ngày thang nam hiện tại cho nDay
                    nDay = date + now;
                    nMonth = month;
                    nYear = years;

                    // Xử lý sự kiện khi layout con (child layout) được click
                    view.setBackgroundResource(R.drawable.rectangle8);
                    text.setTextColor(getResources().getColor(R.color.white));
                    for (int j = 0 ; j < childCount ; ++j){
                    if ( j != now ) {
                        ConstraintLayout layout1 = (ConstraintLayout) linearLayout.getChildAt(j);
                        TextView text1 = (TextView) layout1.getChildAt(1);
                        View view1 = (View) layout1.getChildAt(0);
                        view1.setBackgroundResource(R.drawable.rectangle6);
                        text1.setTextColor(getResources().getColor(R.color.black));
                    }
                    setHoursPicker(date, now, check);
                }}
            });
        }
    }

    private void setHoursPicker(int date, int now, int[][] check) {

        LinearLayout linearLayout1 = findViewById(id.parent_layout1);
        int childCount1 = linearLayout1.getChildCount();

        for (int j = 0 ; j < childCount1 ; ++j){
            ConstraintLayout layout1 = (ConstraintLayout) linearLayout1.getChildAt(j);
            TextView text1 = (TextView) layout1.getChildAt(1);
            View view1 = (View) layout1.getChildAt(0);
            view1.setBackgroundResource(R.drawable.rectangle10);
            text1.setTextColor(getResources().getColor(R.color.black));
        }

        for (int j = 0 ; j < childCount1 ; ++j){
            int now1= j;
            ConstraintLayout layout1 = (ConstraintLayout) linearLayout1.getChildAt(j);
            TextView text1 = (TextView) layout1.getChildAt(1);
            View view1 = (View) layout1.getChildAt(0);
            if (check[date + now][j] == 1) {
                view1.setBackgroundResource(R.drawable.rectangle7);
                text1.setTextColor(getResources().getColor(R.color.gray_2));
                Log.d("XXX", String.format("%d %d %d",now, now1, check[date+now][now1]));
            }
            else {
                layout1.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {

                        // set click vao thoi gian
                        view1.setBackgroundResource(R.drawable.rectangle9);
                        text1.setTextColor(getResources().getColor(R.color.white));

                        //đặt giời hiện tại cho nHour
                        nHour = now1;

                        for (int u = 0; u < childCount1 ; ++u)
                            if (check[date + now][u] == 1) {
                                Log.d("QQQ", String.format("%d %d",now, now1));
                            }
                            else if ( u != now1 ){
                                Log.d("QQQ", String.format("%d %d %d",now, now1, check[date+now][now1]));
                                ConstraintLayout layout2 = (ConstraintLayout) linearLayout1.getChildAt(u);
                                TextView text2 = (TextView) layout2.getChildAt(1);
                                View view2 = (View) layout2.getChildAt(0);
                                view2.setBackgroundResource(R.drawable.rectangle10);
                                text2.setTextColor(getResources().getColor(R.color.black));
                            }
                    }
                });
            }
        }
    }
}

