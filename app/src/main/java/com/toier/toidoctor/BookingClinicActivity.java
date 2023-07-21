//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.toier.toidoctor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.toier.toidoctor.R.id;
import com.toier.toidoctor.R.layout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookingClinicActivity extends AppCompatActivity {
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private TextView tvSelectedDateTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_booking_clinic);
        this.calendar = Calendar.getInstance();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        this.timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Button btnSelectDateTime = (Button)this.findViewById(id.booking);
        btnSelectDateTime.setOnClickListener((v) -> {
            this.showDateTimePicker();
        });
    }

    private void showDateTimePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(1, year);
            selectedCalendar.set(2, monthOfYear);
            selectedCalendar.set(5, dayOfMonth);
            Calendar todayCalendar = Calendar.getInstance();
            if (selectedCalendar.before(todayCalendar)) {
                Toast.makeText(this, "Ngày đã chọn không hợp lệ", Toast.LENGTH_SHORT).show();
            } else {
                this.showTimePicker(selectedCalendar);
            }

        }, this.calendar.get(1), this.calendar.get(2), this.calendar.get(5));
        datePickerDialog.show();
    }

    private void showTimePicker(Calendar calendar) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            int timeCheck = minute - minute / 15 * 15;
            if (timeCheck <= 7) {
                minute = minute / 15 * 15;
            } else {
                minute = minute / 15 * 15 + 15;
            }

            calendar.set(11, hourOfDay);
            calendar.set(12, minute);
            String selectedDateTime = this.dateFormat.format(calendar.getTime()) + " " + this.timeFormat.format(calendar.getTime());
            Toast.makeText(this, "Bạn đã chọn: " + selectedDateTime, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AppointmentActivity.class);
            intent.putExtra("SELECTED_DATE_TIME", selectedDateTime);
            this.startActivity(intent);
        }, calendar.get(11), calendar.get(12), true);
        timePickerDialog.show();
    }
}
