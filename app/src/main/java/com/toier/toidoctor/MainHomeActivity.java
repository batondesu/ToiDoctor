package com.toier.toidoctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.toier.toidoctor.controllers.BookingController;
import com.toier.toidoctor.controllers.MainHomeController;
import com.toier.toidoctor.controllers.Patient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.firebase.firestore.EventListener;
import com.toier.toidoctor.controllers.UserController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainHomeActivity extends AppCompatActivity {
    private int notificationId = 1;
    private Button button;
    private ConstraintLayout booking;
    private ConstraintLayout medicalRecord;

    private boolean XXX = false;
    private String ID;
    private Date date;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //final int count;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        //createNotificationChannel();

        MainHomeController listDoctorController = new MainHomeController(this);
        ID = UserController.getInstance().getPhoneNumber();
        if (UserController.getInstance().isPatient()) {
            //Log.d("BUG", "vcvc");
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference appointmentsRef = db.collection("Appointments"); // Thay "appointments" bằng tên collection của bạn
            appointmentsRef.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e == null) {
                        int dem = 0;
                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    if (dc.getDocument().getBoolean("notice")) {
                                        // Ignore
                                    } else {
                                        Timestamp tp = dc.getDocument().getTimestamp("time");

                                       showNotification(tp ,XXX);
                                        //Log.d("TEST_NOTICE", dc.getDocument().getTimestamp("time").toString());
                                         updateField();
                                    }
                                case REMOVED:
                                    Log.d("chatevents", "onEvent:Removed ");
                                case MODIFIED:
                                    Log.d("chatevents", "onEvent:Modiefied ");

                            }
                        }
                    }
                }
            });

            LinearLayout parentLayout = (LinearLayout) findViewById(R.id.linearLayout);
            // Sử dụng LayoutInflater để tạo một đối tượng View từ layout con (child_layout.xml)
            LayoutInflater inflater = LayoutInflater.from(this);
            View childView = inflater.inflate(R.layout.fragment_clinnic, parentLayout, false);
            parentLayout.addView(childView);
            childView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainHomeActivity.this, ListDoctorActivity.class);
                    startActivity(intent);
                }
            });
            childView = inflater.inflate(R.layout.fragment_profile, parentLayout, false);
            parentLayout.addView(childView);
            childView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // sang profile
                }
            });

            listDoctorController.getAppointment(ID, XXX, new MainHomeController.OnAppointmentDataListener() {
                @Override
                public void onAppointmentDataReceived(Appointment appointment) {
                    if (appointment.getDoctor_id() != "") {
                        addDoctor(appointment.getDoctor_id());
                    }

                    if (appointment.getTimestamp() != null) {

                        Log.d("DD", "duoc khong");

                        Date data = appointment.getTimestamp().toDate();
                        Calendar cl = Calendar.getInstance();
                        cl.setTime(data);

                        int date = cl.get(Calendar.DAY_OF_MONTH);
                        int hour = cl.get(Calendar.HOUR_OF_DAY);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                        String month = dateFormat.format(cl.getTime());

                        TextView sc = (TextView) findViewById(R.id.home_schedule);
                        sc.setText(String.format("%d %s", date, month));

                        TextView sc1 = (TextView) findViewById(R.id.home_time);
                        sc1.setText(String.format("%d:00", hour));
                    } else {
                        Log.d("CCC", "duoc khong");
                    }
                }

                @Override
                public void onAppointmentDataError(String errorMessage) {
                }
            });

            TextView tv = (TextView) findViewById(R.id.top_dr);
            tv.setText("Top Bác sĩ uy tín");

            // Khởi tạo ListDoctorContr
            // Tìm ListView trong layout và thiết lập Adapter cho nó
            ListView listView = findViewById(R.id.listdoctor);
            listDoctorController.fetchDoctorsFromFirestore(listView);
        } else {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference appointmentsRef = db.collection("Appointments"); // Thay "appointments" bằng tên collection của bạn
            appointmentsRef.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e == null) {
                        int dem = 0;
                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    if (dc.getDocument().getBoolean("notice")) {
                                        // Ignore
                                    } else {
                                        Timestamp tp = dc.getDocument().getTimestamp("time");

                                        showNotification(tp, XXX);
                                        //Log.d("TEST_NOTICE", dc.getDocument().getTimestamp("time").toString());
                                        updateField();
                                    }
                                case REMOVED:
                                    Log.d("chatevents", "onEvent:Removed ");
                                case MODIFIED:
                                    Log.d("chatevents", "onEvent:Modiefied ");
                            }
                        }
                    }
                }
            });

            LinearLayout parentLayout = (LinearLayout) findViewById(R.id.linearLayout);
            // Sử dụng LayoutInflater để tạo một đối tượng View từ layout con (child_layout.xml)
            LayoutInflater inflater = LayoutInflater.from(this);
            View childView = inflater.inflate(R.layout.fragment_patient, parentLayout, false);
            parentLayout.addView(childView);
            childView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainHomeActivity.this, PatientProfileActivity.class);
                    startActivity(intent);
                }
            });
            childView = inflater.inflate(R.layout.fragment_profile, parentLayout, false);
            parentLayout.addView(childView);
            childView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // sang profile
                }
            });

            //set view
            listDoctorController.getAppointment(ID, XXX, new MainHomeController.OnAppointmentDataListener() {
                @Override
                public void onAppointmentDataReceived(Appointment appointment) {
                    if (appointment.getPatient_id() != "") {
                        addPatient(appointment.getPatient_id());
                    }

                    if (appointment.getTimestamp() != null) {

                        Log.d("DD", "duoc khong");

                        Date data = appointment.getTimestamp().toDate();
                        Calendar cl = Calendar.getInstance();
                        cl.setTime(data);

                        int date = cl.get(Calendar.DAY_OF_MONTH);
                        int hour = cl.get(Calendar.HOUR_OF_DAY);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                        String month = dateFormat.format(cl.getTime());

                        TextView sc = (TextView) findViewById(R.id.home_schedule);
                        sc.setText(String.format("%d %s", date, month));

                        TextView sc1 = (TextView) findViewById(R.id.home_time);
                        sc1.setText(String.format("%d:00", hour));
                    } else {
                        Log.d("CCC", "duoc khong");
                    }
                }

                @Override
                public void onAppointmentDataError(String errorMessage) {
                }
            });

            TextView tv = (TextView) findViewById(R.id.top_dr);
            tv.setText("Danh sách bệnh nhân");

            // Tìm ListView trong layout và thiết lập Adapter cho nó
            ListView listView = findViewById(R.id.listdoctor);
            listDoctorController.fetchPatientFromFirestore(ID, listView);
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

    public void addPatient(String ID) {
        MainHomeController control = new MainHomeController(this);
        control.getPatientData(ID, new MainHomeController.OnPatientDataListener() {
            @Override
            public void onPatientDataReceived(Patient patient) {
                TextView patientName = findViewById(R.id.home_name);
                patientName.setText(patient.getName());

                TextView tvDoctorMajor = findViewById(R.id.home_major);
                tvDoctorMajor.setText(patient.getPhoneNumber());

                TextView tvDoctorExp = findViewById(R.id.home_review);
                tvDoctorExp.setText(String.format("Tuổi: %d", patient.getAge()));

            }

            @Override
            public void onPatientDataError(String errorMessage) {
            }
        });
    }


    private void showNotification(Timestamp timestamp, boolean XXX) {

        String s = "";
        if ( XXX ) {
            s = "Bạn có một bệnh nhân mới";
        }
        else {
            s = "Đặt lịch khám thành công";
        }
        Log.d("XX", "co thông báo");
        // Xây dựng thông báo
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notification")
                .setSmallIcon(R.drawable.iconnoti) // Đặt icon cho thông báo
                .setContentTitle("Nhắc nhở") // Đặt tiêu đề thông báo
                .setContentText("Đặt lịch khám thành công") // Đặt nội dung thông báo
                //.setContentIntent(pendingIntent) // Đặt PendingIntent
                .setAutoCancel(true); // Tự động huỷ thông báo khi người dùng nhấn vào

        // Hiển thị thông báo
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "notification"; // Đặt tên cho channel
            CharSequence channelName = "Nhắc nhở"; // Đặt tên hiển thị của channel
            String channelDescription = "Bạn có một bệnh nhân mới"; // Mô tả channel (không bắt buộc)

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void updateField() {
        Log.d("ACV", "update");
        Map<String, Object> updates = new HashMap<>();
        updates.put("notice", true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Appointments");
        collectionRef
                .whereEqualTo("notice", false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if ( task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Cập nhật trường fieldName cho từng document
                                Log.d("ACV", "update");
                                collectionRef.document(document.getId()).update("notice",true)
                                        .addOnSuccessListener(aVoid -> {
                                            Log.d("ACV", "update2");
                                        })
                                        .addOnFailureListener(e -> {
                                            // Xử lý khi cập nhật thất bại
                                        });
                            }
                        }
                    }
                });
    }
}