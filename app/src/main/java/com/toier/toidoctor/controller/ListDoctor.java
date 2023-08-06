package com.toier.toidoctor.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.toier.toidoctor.Doctor;
import com.toier.toidoctor.R;

import java.util.ArrayList;

public class ListDoctor extends BaseAdapter {
    private Context context;
    private ArrayList<Doctor> doctorList;

    public ListDoctor(Context context, ArrayList<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @Override
    public int getCount() {
        return doctorList.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.doctor, null);
        } else viewProduct = convertView;


        Doctor doctor = doctorList.get(position);

        ((TextView) viewProduct.findViewById(R.id.doctor)).setText(String.format(doctor.getName()));
        ((TextView) viewProduct.findViewById(R.id.majors)).setText(String.format(doctor.getMajor()));
        ((TextView) viewProduct.findViewById(R.id.review)).setText(String.format("(%d Đánh giá)", doctor.getReview()));
        ((TextView) viewProduct.findViewById(R.id.rate)).setText(String.format(String.valueOf(doctor.getRate())));


        return viewProduct;
    }
}