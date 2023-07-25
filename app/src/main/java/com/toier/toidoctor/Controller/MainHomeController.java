package com.toier.toidoctor.Controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.toier.toidoctor.BookingClinicActivity;
import com.toier.toidoctor.Doctor;
import com.toier.toidoctor.ListDoctorActivity;
import com.toier.toidoctor.MainHomeActivity;
import com.toier.toidoctor.PatientProfileActivity;
import com.toier.toidoctor.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainHomeController {

    private Context context;

    private ArrayList<Doctor> listDoctor1;

    public MainHomeController(Context context) {
        this.context = context;
        this.listDoctor1 = new ArrayList<>();
    }

    public void setData(ListView listView) {
        // Tạo Adapter và thiết lập cho ListView
        //ArrayList<Doctor> listDoctor = new ArrayList<>();
        //fetchDoctorsFromFirestore();
        Log.d("ABC", String.format("Size: %d",listDoctor1.size()) );
        for(int i = 0 ; i < listDoctor1.size() ; ++i) {
            Log.d("ABC", listDoctor1.get(i).getName().toString() );
        }

        /*ArrayList<Doctor> doctorList1 = new ArrayList<>();
        doctorList1.add(new Doctor("John Doe", "Cardiologist", 1,1,"","","","",0,0));
        doctorList1.add(new Doctor("Jane Smith", "Pediatrician",1,1,"","","","",0,0));*/

        ListDoctor listDoctorAdapter = new ListDoctor(context, listDoctor1);
        listView.setAdapter(listDoctorAdapter);
    }

    // Hàm để lấy dữ liệu Doctor từ Firestore và thêm vào listDoctor
    public void fetchDoctorsFromFirestore(ListView listView) {

        // Truy cập vào collection "doctors" trong Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference doctorsRef = db.collection("Doctors");

        doctorsRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        //listDoctor.clear(); // Xóa danh sách hiện tại để tránh trùng lặp
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            //Log.d("XXX" , "dm e DangLeHai"  );


                            // Lấy dữ liệu từ Firestore và tạo đối tượng Doctor
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


                            // Thêm đối tượng Doctor vào listDoctor
                            listDoctor1.add(doctor);
                            //Log.d("ABC", String.format("Size: %d",listDoctor.size()) );
                        }
                        //Log.d("ABC", String.format("Size: %d",listDoctor1.size()) );
                        Collections.sort(listDoctor1, new Comparator<Doctor>() {
                            @Override
                            public int compare(Doctor doctor1, Doctor doctor2) {
                                // Sử dụng Double.compare() để so sánh hai giá trị Double (rate)
                                return Double.compare(doctor2.getRate(), doctor1.getRate());
                            }
                        });
                        ListDoctor listDoctorAdapter = new ListDoctor(context, listDoctor1);
                        listView.setAdapter(listDoctorAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Doctor doctor  = (Doctor) listDoctorAdapter.getItem(position);
                                        Intent intent = new Intent(context, BookingClinicActivity.class);
                                intent.putExtra("KEY_VALUE", doctor.getDoctor_ID());
                                context.startActivity(intent);
                            }
                        });
                    } else {
                        // Không có dữ liệu trong collection "doctors"
                    }
                } else {
                    // Xử lý lỗi khi lấy dữ liệu từ Firestore
                }
            }
        });
    }
}
