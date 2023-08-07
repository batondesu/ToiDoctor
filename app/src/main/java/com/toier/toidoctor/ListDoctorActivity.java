package com.toier.toidoctor;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

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

        Button buttonFilter = findViewById(R.id.filter);
        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHospitalDialog();
            }
        });

        onListViewSearch();
    }

    private void onListViewSearch() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference doctorRef = db.collection("Doctors");

        List<Doctor> doctorList = new ArrayList<>();
        List<Doctor> filterList = new ArrayList<>();

        doctorRef.get().addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               QuerySnapshot querySnapshot = task.getResult();
               if (querySnapshot != null) {
                   for (QueryDocumentSnapshot document : querySnapshot) {
                       Doctor doctor = document.toObject(Doctor.class);
                       doctorList.add(doctor);
                   }
               }
           }

            DoctorAdapter adapter = new DoctorAdapter(this, doctorList);
            ListView listView1 = findViewById(R.id.listView);
            listView1.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Doctor doctor = adapter.getItem(i);
                    //adapter.showDoctorInfoDialog(doctor);
                    Intent intent = new Intent(ListDoctorActivity.this, BookingClinicActivity.class);
                    intent.putExtra("KEY_VALUE", doctor.getID());
                    startActivity(intent);
                }
            });

            adapter.notifyDataSetChanged();

            SearchView searchView = findViewById(R.id.searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.getFilter().filter(s);
                    return false;
                }
            });
        });
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
        db.collection("Hospitals").document(selectedHospital.getId()).collection("department")
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
    // ....

    private void showDoctorsList(QueryDocumentSnapshot selectedHospital, QueryDocumentSnapshot selectedDepartment) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Hospitals").document(selectedHospital.getId()).collection("department")
                .document(selectedDepartment.getId()).collection("doctors")
                .orderBy("rate", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Doctor> doctorList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Doctor doctor = document.toObject(Doctor.class);
                        doctorList.add(doctor);
                    }

                    DoctorAdapter adapter = new DoctorAdapter(ListDoctorActivity.this, doctorList);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Doctor doctor = adapter.getItem(i);
                            //adapter.showDoctorInfoDialog(doctor);
                            Intent intent = new Intent(ListDoctorActivity.this, BookingClinicActivity.class);
                            intent.putExtra("KEY_VALUE", doctor.getID());
                            startActivity(intent);
                        }
                    });

                    adapter.notifyDataSetChanged();
                });
    }
}

