
package com.toier.toidoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.toier.toidoctor.R.id;
import com.toier.toidoctor.R.layout;

public class AppointmentActivity extends AppCompatActivity {
    private TextView tvDisplayDateTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_appointment);
        this.tvDisplayDateTime = (TextView)this.findViewById(id.tvDisplayDateTime);
        Intent intent = this.getIntent();
        if (intent != null) {
            String selectedDateTime = intent.getStringExtra("SELECTED_DATE_TIME");
            if (selectedDateTime != null) {
                this.tvDisplayDateTime.setText("Ngày hẹn khám của bạn: \n" + selectedDateTime);
            }
        }

        Button button_back_main_activity = findViewById(R.id.tro_ve_trang_chu);
        button_back_main_activity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentActivity.this, MainHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
