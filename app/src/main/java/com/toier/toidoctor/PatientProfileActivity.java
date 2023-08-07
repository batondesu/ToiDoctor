package com.toier.toidoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.toier.toidoctor.controllers.PatientProfile;
import com.toier.toidoctor.controllers.UserController;

import java.util.Date;

public class PatientProfileActivity extends AppCompatActivity {
    private Button button;


    private void setButton(String id, String name, String phoneNumber) {
        Button btnTest =  new Button(this);
        btnTest = PatientProfile.createButton(this, btnTest, id, name);
        LinearLayout layout = (LinearLayout) findViewById(R.id.patientList);
        layout.addView(btnTest);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("age", "17");
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        LinearLayout layout = (LinearLayout) findViewById(R.id.patientList);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

//      Lấy thông tin bệnh nhân
        db.collection("Patients")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete  (@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("ABC", document.getId() + " => " + document.getData());
                                String user_id = String.valueOf(document.getData().get("User"));
                                db.collection("Users")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Timestamp timestamp = (Timestamp) document.getData().get("birthday");
                                                        Date date = timestamp.toDate();
                                                        Log.d("ABC", String.valueOf(date.getDate()));
                                                        if (UserController.getInstance().isPatient()) {
                                                            if (document.getId().equals(user_id) && UserController.getInstance().getPhoneNumber().equals(String.valueOf(document.getData().get("phoneNumber")))) {

                                                                setButton(user_id, String.valueOf(document.getData().get("name")), String.valueOf(document.getData().get("phoneNumber")));
                                                            }
                                                        }
                                                        else {
                                                            if (document.getId().equals(user_id) ) {

                                                                setButton(user_id, String.valueOf(document.getData().get("name")), String.valueOf(document.getData().get("phoneNumber")));
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    Log.w("ABC", "Error getting documents.", task.getException());
                                                }
                                            }
                                        });
                                //setButton(document.getId(), String.valueOf(document.getData().get("name")), String.valueOf(document.getData().get("phoneNumber")));
                                //setName( String.valueOf(document.getData().get("name")));
                                //setPhoneNumber( String.valueOf(document.getData().get("phoneNumber")));
                                //setButton(document.getId(), getName(), getPhoneNumber());

                            }
                        } else {
                            Log.w("ABC", "Error getting documents.", task.getException());
                        }
                    }
                });

        //set the properties for button
        /*Button btnTag1 = new Button(this);
        btnTag1 = patientprofile.createButton(this, btnTag1, "button1", "Phùng Quang Tiến");
        layout.addView(btnTag1);
        Button btnTag2 = new Button(this);
        btnTag2 = patientprofile.createButton(this, btnTag2, "button2", "Lê Hải Đăng");
        layout.addView(btnTag2);
        Button btnTag3 = new Button(this);
        btnTag3 = patientprofile.createButton(this, btnTag3, "button3", "Nguyễn Văn Nam");
        layout.addView(btnTag3);
        Button btnTag4 = new Button(this);
        btnTag4 = patientprofile.createButton(this, btnTag4, "button4", "Đào Duy Chiến");
        layout.addView(btnTag4);
        Button btnTag5 = new Button(this);
        btnTag5 = patientprofile.createButton(this, btnTag5, "button5", "Nguễn Minh Chiến");
        layout.addView(btnTag5);
        Button btnTag6 = new Button(this);
        btnTag6 = patientprofile.createButton(this, btnTag6, "button6", "Nguyễn Đức Hiếu");
        layout.addView(btnTag6);
        Button btnTag7 = new Button(this);
        btnTag7 = patientprofile.createButton(this, btnTag7, "button7", "Lê Việt Hưng");
        layout.addView(btnTag7);
        Button btnTag8 = new Button(this);
        btnTag8 = patientprofile.createButton(this, btnTag8, "button8", "Lê Anh Nuôi");
        layout.addView(btnTag8);
        Button btnTag9 = new Button(this);
        btnTag9 = patientprofile.createButton(this, btnTag9, "button9", "David");
        layout.addView(btnTag9);
        */

        /*btnTag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", "Phùng Quang Tiến");
                intent.putExtra("phoneNumber", "000000");
                intent.putExtra("age", "17");
                intent.putExtra("content", "Sỏi thận");
                startActivity(intent);
            }
        });
        btnTag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", "Lê Hải Đăng");
                intent.putExtra("phoneNumber", "000000");
                intent.putExtra("age", "18");
                intent.putExtra("content", "Đau đầu");
                startActivity(intent);
            }
        });
        btnTag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", "Nguyễn Văn Nam");
                intent.putExtra("phoneNumber", "000000");
                intent.putExtra("age", "19");
                intent.putExtra("content", "Sỏi thận");
                startActivity(intent);
            }
        });
        btnTag4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", "Đào Duy Chiến");
                intent.putExtra("phoneNumber", "000000");
                intent.putExtra("age", "28");
                intent.putExtra("content", "Sỏi thận");
                startActivity(intent);
            }
        });
        btnTag5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", "Nguyễn Minh Chiến");
                intent.putExtra("phoneNumber", "000000");
                intent.putExtra("age", "17");
                intent.putExtra("content", "Sỏi thận");
                startActivity(intent);
            }
        });
        btnTag6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", "Nguyễn Đức Hiếu");
                intent.putExtra("phoneNumber", "000000");
                intent.putExtra("age", "24");
                intent.putExtra("content", "Sỏi thận");
                startActivity(intent);
            }
        });
        btnTag7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", "Lê Việt Hưng");
                intent.putExtra("phoneNumber", "000000");
                intent.putExtra("age", "14");
                intent.putExtra("content", "Sỏi thận");
                startActivity(intent);
            }
        });
        btnTag8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", "Lê Anh Nuôi");
                intent.putExtra("phoneNumber", "000000");
                intent.putExtra("age", "35");
                intent.putExtra("content", "Sỏi thận");
                startActivity(intent);
            }
        });
        btnTag9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", "David");
                intent.putExtra("phoneNumber", "000000");
                intent.putExtra("age", "20");
                intent.putExtra("content", "Sỏi thận");
                startActivity(intent);
            }
        });*/

    }
}