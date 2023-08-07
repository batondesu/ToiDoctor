package com.toier.toidoctor.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.toier.toidoctor.Appointment;
import com.toier.toidoctor.BookingClinicActivity;
import com.toier.toidoctor.Doctor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainHomeController {

    private Context context;

    private ArrayList<Doctor> listDoctor1;
    private ArrayList<patient> listPatient;
    private ArrayList<Appointment> listApp;

    private Date d;

    private Appointment schedule = new Appointment();
    public MainHomeController(Context context) {
        this.context = context;
        this.listDoctor1 = new ArrayList<>();
        this.listPatient = new ArrayList<>();
        this.listApp = new ArrayList<>();
    }


    // Hàm để lấy dữ liệu Doctor từ Firestore và thêm vào listDoctor
    public void fetchDoctorsFromFirestore(ListView listView) {
        List<Doctor> top = new ArrayList<Doctor>();
        //Log.d("XXX" , "dm e DangLeHai"  );
        // Truy cập vào collection "doctors" trong Firestore
        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
        CollectionReference doctorsRef1 = db1.collection("Doctors");

        doctorsRef1.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot2 = task.getResult();
                    Log.d("XXX" , "dm e DangLeHai2"  );
                    if (querySnapshot2 != null && !querySnapshot2.isEmpty()) {
                        Log.d("XXX" , "dm e DangLeHai"  );
                        for (DocumentSnapshot document : querySnapshot2.getDocuments()) {

                            // Lấy dữ liệu từ Firestore và tạo đối tượng Doctor
                            Doctor doctor = new Doctor();
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


                            // Thêm đối tượng Doctor vào listDoctor

                            top.add(doctor);
                            //listDoctor1.add(doctor);
                            //Log.d("ABC", String.format("Size: %d",listDoctor.size()) );
                        }
                        //Log.d("ABC", String.format("Size: %d",listDoctor1.size()) );
                        Collections.sort(top, new Comparator<Doctor>() {
                            @Override
                            public int compare(Doctor doctor1, Doctor doctor2) {
                                // Sử dụng Double.compare() để so sánh hai giá trị Double (rate)
                                return Double.compare(doctor2.getRate(), doctor1.getRate());
                            }
                        });

                        for (int i = 0 ; i < Math.min(10,top.size()); ++i) {
                            listDoctor1.add(top.get(i));
                        }
                        ListDoctor listDoctorAdapter = new ListDoctor(context, listDoctor1);
                        listView.setAdapter(listDoctorAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Doctor doctor  = (Doctor) listDoctorAdapter.getItem(position);
                                        Intent intent = new Intent(context, BookingClinicActivity.class);
                                intent.putExtra("KEY_VALUE", doctor.getID());
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

    public void fetchPatientFromFirestore(String ID, ListView listView) {
        int[] check = new int[100];
        // Truy cập vào collection "doctors" trong Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
        CollectionReference doctorsRef = db.collection("Appointments");
        CollectionReference patientRef = db1.collection("Patients");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        doctorsRef.whereEqualTo("doctor_id", ID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                //listDoctor.clear(); // Xóa danh sách hiện tại để tránh trùng lặp
                                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                    //Log.d("XXX" , "dm e DangLeHai"  );
                                    Timestamp cur = document.getTimestamp("time");
                                    if ( date.after(cur.toDate()) ) {
                                        String patient_id = document.getString("patient_id").toString();
                                        int auto = Integer.valueOf(patient_id);
                                        if ( check[auto] != 1) {
                                            Log.d("IOS", patient_id);
                                            patientRef.whereEqualTo("ID", patient_id)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                                            if (task1.isSuccessful()) {
                                                                QuerySnapshot querySnapshot1 = task1.getResult();
                                                                if (querySnapshot1 != null && !querySnapshot1.isEmpty()) {
                                                                    //listDoctor.clear(); // Xóa danh sách hiện tại để tránh trùng lặp
                                                                    for (DocumentSnapshot document1 : querySnapshot1.getDocuments()) {
                                                                        patient patient1 = new patient();
                                                                        patient1.setPhoneNumber(document1.getString("phone"));
                                                                        patient1.setName(document1.getString("name").toString());
                                                                        long res = document1.getLong("age");
                                                                        //Log.d("IOS", String.valueOf(res));
                                                                        patient1.setAge((int) res);
                                                                        listPatient.add(patient1);
                                                                    }
                                                                    //set listView
                                                                    ListPatient listPatientAdapter = new ListPatient(context, listPatient);

                                                                    listView.setAdapter(listPatientAdapter);
                                                                /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                                    @Override
                                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                        Doctor doctor  = (Doctor) listPatientAdapter.getItem(position);
                                                                        Intent intent = new Intent(context, BookingClinicActivity.class);
                                                                        intent.putExtra("KEY_VALUE", doctor.getID());
                                                                        context.startActivity(intent);
                                                                    }
                                                                });*/
                                                                }
                                                            }
                                                        }
                                                    });
                                            check[auto] = 1;
                                        }
                                    }

                                }
                                //Log.d("ABC", String.format("Size: %d",listDoctor1.size()) );
                            } else {
                                // Không có dữ liệu trong collection "doctors"
                            }
                        } else {
                            // Xử lý lỗi khi lấy dữ liệu từ Firestore
                        }
                    }
                });
    }

    public interface OnAppointmentDataListener {
        void onAppointmentDataReceived(Appointment appointment);

        void onAppointmentDataError(String errorMessage);
    }

    //public void getDoctorData(String doctorId, OnDoctorDataListener listener) {
    public void getAppointment(String ID, boolean role, OnAppointmentDataListener listener) {

        String check = "";
        if ( !role ) {
            check = "patient_id";
        }
        else {
            check = "doctor_id";
        }
        Date ans = new Date();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        d = BookingController.convertToTimestamp(31,12,3000,1).toDate();
        //Log.d("CCC", "hoi kho");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Appointments")
                .whereEqualTo(check, ID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                    if (document.getTimestamp("time") != null) {
                                        Timestamp cur = document.getTimestamp("time");
                                        //Log.d("CCC", String.format("%d",schedule.getTimestamp().compareTo(cur)));
                                        //Log.d("CCC", schedule.getTimestamp().toDate().toString());
                                        if (d.after(cur.toDate()) && now.before(cur.toDate())) {

                                            schedule.setTimestamp(cur);
                                            Log.d("LLL", schedule.getTimestamp().toDate().toString());
                                            schedule.setDoctor_id(document.get("doctor_id").toString());
                                            schedule.setPatient_id(document.get("patient_id").toString());
                                            d = cur.toDate();
                                        }
                                    }
                                }
                                listener.onAppointmentDataReceived(schedule);
                            }
                        }
                    }
                });
    }


    public interface OnPatientDataListener {
        void onPatientDataReceived(patient patient);

        void onPatientDataError(String errorMessage);
    }

    public void getPatientData(String patientID, MainHomeController.OnPatientDataListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Patients")
                .whereEqualTo("ID", patientID)
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
                                    patient patient1 = new patient();

                                    patient1.setName(document.getString("name").toString());
                                    patient1.setPhoneNumber(document.getString("phone").toString());
                                    long res = document.getLong("age");
                                    patient1.setAge((int) res);

                                    listener.onPatientDataReceived(patient1);
                                    //Log.d("XXX", doctor.getName().toString());
                                }

                            } else {
                                //listener.onDoctorDataError("Không tìm thấy thông tin Doctor");
                            }
                        } else {
                            //listener.onDoctorDataError("Lỗi khi lấy dữ liệu từ Firestore");
                        }
                    }
                });
    }

    public void fetchAppointFromFirestore(ListView listView) {
        List<Appointment> top = new ArrayList<Appointment>();
        //Log.d("XXX" , "dm e DangLeHai"  );

        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();

        // Truy cập vào collection "doctors" trong Firestore
        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
        CollectionReference doctorsRef1 = db1.collection("Appointments");

        doctorsRef1.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot2 = task.getResult();
                            //Log.d("XXX" , "dm e DangLeHai2"  );
                            if (querySnapshot2 != null && !querySnapshot2.isEmpty()) {
                                //Log.d("XXX" , "dm e DangLeHai"  );
                                for (DocumentSnapshot document : querySnapshot2.getDocuments()) {
                                    if (document.getTimestamp("time") != null) {
                                        Timestamp cur = document.getTimestamp("time");
                                        //Log.d("CCC", String.format("%d",schedule.getTimestamp().compareTo(cur)));
                                        //Log.d("CCC", schedule.getTimestamp().toDate().toString());
                                        if (d.before(cur.toDate())) {
                                            Appointment schedule = new Appointment();
                                            schedule.setTimestamp(cur);
                                            schedule.setDoctor_id(document.get("doctor_id").toString());
                                            schedule.setPatient_id(document.get("patient_id").toString());
                                            listApp.add(schedule);
                                        }
                                    }
                                }
                                //Log.d("ABC", String.format("Size: %d",listDoctor1.size()) );
                                Collections.sort(listApp, new Comparator<Appointment>() {
                                    @Override
                                    public int compare(Appointment appointment, Appointment t1) {
                                        return appointment.getTimestamp().compareTo(t1.getTimestamp());
                                    }
                                });
                                ClinnicApdater listAdapter = new ClinnicApdater(context, listApp);
                                listView.setAdapter(listAdapter);
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
