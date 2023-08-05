package com.toier.toidoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.toier.toidoctor.controller.ListDoctor;
import com.toier.toidoctor.controller.MainHomeController;

import java.util.ArrayList;


public class MainHomeActivity extends AppCompatActivity {

    private Button button;
    private ConstraintLayout booking;
    private ConstraintLayout medicalRecord;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

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

        // Khởi tạo ListDoctorController
        MainHomeController listDoctorController = new MainHomeController(this);

        // Tìm ListView trong layout và thiết lập Adapter cho nó
        ListView listView = findViewById(R.id.listdoctor);
        listDoctorController.fetchDoctorsFromFirestore(listView);

        //click vao 1 bac si bat ki

    }
}