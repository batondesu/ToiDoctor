package com.toier.toidoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
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