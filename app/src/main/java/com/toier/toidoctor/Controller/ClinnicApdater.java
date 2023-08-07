package com.toier.toidoctor.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.toier.toidoctor.Appointment;
import com.toier.toidoctor.Doctor;
import com.toier.toidoctor.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ClinnicApdater extends BaseAdapter {
    private Context context;
    private List<Appointment> list;

    public ClinnicApdater(Context context, ArrayList<Appointment> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.fragment_noti, null);
        } else viewProduct = convertView;


        Appointment appointment = list.get(position);

        BookingController.getDoctorData(appointment.getDoctor_id(), new BookingController.OnDoctorDataListener() {
            @Override
            public void onDoctorDataReceived(Doctor doctor) {
                ((TextView) viewProduct.findViewById(R.id.noti_name)).setText(doctor.getName());
                ((TextView) viewProduct.findViewById(R.id.noti_major)).setText(doctor.getMajor());
                ((TextView) viewProduct.findViewById(R.id.noti_review)).setText(String.format("(%d Đánh giá)", doctor.getReview()));
                ((TextView) viewProduct.findViewById(R.id.noti_rate)).setText(String.format("%d", doctor.getRate()));
            }

            @Override
            public void onDoctorDataError(String errorMessage) {

            }
        });

        Date data = appointment.getTimestamp().toDate();
        Calendar cl = Calendar.getInstance();
        cl.setTime(data);

        int date = cl.get(Calendar.DAY_OF_MONTH);
        int hour = cl.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        String month = dateFormat.format(cl.getTime());

        ((TextView) viewProduct.findViewById(R.id.noti_time)).setText(String.format("%d %s", date,month));
        ((TextView) viewProduct.findViewById(R.id.noti_schedule)).setText(String.format("%d:00",hour));

        return viewProduct;
    }
}
