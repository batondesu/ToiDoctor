package com.toier.toidoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.toier.toidoctor.Controller.patientMedicalRecord;

import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    private Button isChoosed;
    private void setButton(String id, String time, String detail) {
        Button btnTest =  new Button(this);
        final Button btnTest_v = patientMedicalRecord.createButton(this, btnTest, id, time);
        btnTest_v.setBackgroundResource(R.drawable.rectangle6);
        LinearLayout layout = (LinearLayout) findViewById(R.id.updatedDate);
        layout.addView(btnTest);

        btnTest_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChoosed != null) {
                    isChoosed.setBackgroundResource(R.drawable.rectangle6);
                    isChoosed.setTextColor(getResources().getColor(R.color.black));
                }
                btnTest_v.setBackgroundResource(R.drawable.rectangle8);
                TextView result = (TextView) findViewById(R.id.resultdetail);
                result.setText(detail);
                btnTest_v.setTextColor(getResources().getColor(R.color.white));
                isChoosed = btnTest_v;


            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button btnCall = (Button) findViewById(R.id.phoneCall);
        Button btnMessage = (Button) findViewById(R.id.sendMessage);
        TextView patientName = (TextView) findViewById(R.id.name);
        TextView patientAge = (TextView) findViewById(R.id.age);
        TextView patientDetail = (TextView) findViewById(R.id.resultdetail);
        Log.d("ABC", getIntent().getStringExtra("name"));


        patientName.setText(getIntent().getStringExtra("name"));
        patientAge.setText(getIntent().getStringExtra("age") + " tuổi");
        //patientDetail.setText(getIntent().getStringExtra("content"));
        /*ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        patientName.setLayoutParams(layoutParams);*/

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
                                if (user_id.equals(getIntent().getStringExtra("id"))) {
                                    db.collection("Patients/" + document.getId() + "/medicalRecord")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            Log.d("ABC", document.getId() + " => " + document.getData());
                                                            Timestamp timestamp = (Timestamp) document.getData().get("created_at");
                                                            Date date = timestamp.toDate();
                                                            String time = date.getDate() + "/"  + String.valueOf(date.getMonth()+1) + "/" +  String.valueOf(1900 + date.getYear());
                                                            setButton(document.getId(), time, String.valueOf(document.getData().get("detail")));
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
                            }
                        } else {
                            Log.w("ABC", "Error getting documents.", task.getException());
                        }
                    }
                });


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + getIntent().getStringExtra("phoneNumber")));
                startActivity(intent);
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + getIntent().getStringExtra("phoneNumber")));
                intent.putExtra("sms_body", "Đây là tin nhắn đến từ bác sĩ chủ trị của bạn\n Xin chào ...");
                startActivity(intent);
            }
        });



    }
}