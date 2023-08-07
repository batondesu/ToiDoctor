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
import com.toier.toidoctor.controller.patientMedicalRecord;

import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    public Button isChoosed;

    private void setButton(String id, String time, String detail) {
        Log.d("ABC", "id = " + id);
        final Button btnTest =  new Button(this);
        final Button btnTest_v = patientMedicalRecord.createButton(this, btnTest, id, time);
        LinearLayout layout = (LinearLayout) findViewById(R.id.updatedDate);
        layout.addView(btnTest_v);
        Log.d("ABC", "btnid = " + btnTest.getId());

        btnTest_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChoosed != null) {
                    isChoosed.setBackgroundResource(R.drawable.rectangle6); // Set the background color to green
                    Log.d("ABC", String.valueOf(isChoosed.getId()));
                    isChoosed.setTextColor(getResources().getColor(R.color.black));

                }
                int whiteColor = 0xFFFFFFFF;
                btnTest_v.setBackgroundResource(R.drawable.rectangle8); // Set the background color to green
                btnTest_v.setTextColor(whiteColor);
                TextView result = (TextView) findViewById(R.id.resultdetail);
                result.setText(detail);
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
        TextView patientName = (TextView) findViewById(R.id.textView3);
        TextView patientAge = (TextView) findViewById(R.id.textView7);
        TextView patientDetail = (TextView) findViewById(R.id.resultdetail);


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