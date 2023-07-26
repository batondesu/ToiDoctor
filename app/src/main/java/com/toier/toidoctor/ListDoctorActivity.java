package com.toier.toidoctor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListDoctorActivity extends AppCompatActivity {

    private ListView listView;
    private QueryDocumentSnapshot selectedHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_doctors);

        listView = findViewById(R.id.listView);

        showHospitalDialog();
    }

    private void showHospitalDialog() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Hospitals")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<QueryDocumentSnapshot> hospitalList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        hospitalList.add(document);
                    }
                    showHospitalDialog(hospitalList);
                });
    }

    private void showHospitalDialog(List<QueryDocumentSnapshot> hospitalList) {
        List<String> hospitalNames = new ArrayList<>();

        for (QueryDocumentSnapshot hospitalSnapshot : hospitalList) {
            String hospitalName = hospitalSnapshot.getString("name");
            hospitalNames.add(hospitalName);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn bệnh viện");
        builder.setItems(hospitalNames.toArray(new String[0]), (dialog, which) -> {
            selectedHospital = hospitalList.get(which);
            showDepartmentDialog();
        });

        builder.show();
    }

    private void showDepartmentDialog() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Hospitals").document(selectedHospital.getId()).collection("departments")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<QueryDocumentSnapshot> departmentList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        departmentList.add(document);
                    }
                    showDepartmentDialog(departmentList);
                });
    }

    private void showDepartmentDialog(List<QueryDocumentSnapshot> departmentList) {
        List<String> departmentsName = new ArrayList<>();

        for (QueryDocumentSnapshot document : departmentList) {
            String departmentName = document.getString("name");
            departmentsName.add(departmentName);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn khoa khám");
        builder.setItems(departmentsName.toArray(new String[0]), (dialog, which) -> {
            QueryDocumentSnapshot selectedDepartment = departmentList.get(which);
            showDoctorsList(selectedHospital, selectedDepartment);
        });

        builder.show();
    }

    private void showDoctorsList(QueryDocumentSnapshot selectedHospital, QueryDocumentSnapshot selectedDepartment) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Hospitals").document(selectedHospital.getId()).collection("departments")
                .document(selectedDepartment.getId()).collection("doctors")
                .orderBy("rate", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<com.toier.toiDoctorClass.DoctorClass> doctorList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        com.toier.toiDoctorClass.DoctorClass doctor = document.toObject(com.toier.toiDoctorClass.DoctorClass.class);
                        doctorList.add(doctor);
                    }

                    DoctorAdapter adapter = new DoctorAdapter(ListDoctorActivity.this, doctorList);

                    adapter.setOnDoctorClickListener(doctor -> {
                        Intent intent = new Intent(ListDoctorActivity.this, BookingClinicActivity.class);
                        intent.putExtra("selected_doctor", (CharSequence) doctor);
                        startActivity(intent);
                    });

                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                });
    }
}

