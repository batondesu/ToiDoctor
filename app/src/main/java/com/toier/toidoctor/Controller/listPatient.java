package com.toier.toidoctor.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.toier.toidoctor.Doctor;
import com.toier.toidoctor.R;

import java.util.ArrayList;
import java.util.List;

public class ListPatient extends BaseAdapter {
    private Context context;
    private List<patient> patientList;

    public ListPatient(Context context, ArrayList<patient> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    @Override
    public int getCount() {
        return patientList.size();
    }

    @Override
    public Object getItem(int position) {
        return patientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.patient, null);
        } else viewProduct = convertView;


        patient patient1 = patientList.get(position);

        Log.d("UIX", patient1.getName());

        ((TextView) viewProduct.findViewById(R.id.patient)).setText(String.format(patient1.getName()));
        ((TextView) viewProduct.findViewById(R.id.phone)).setText(String.format(patient1.getPhoneNumber()));
        ((TextView) viewProduct.findViewById(R.id.age)).setText(String.format("Tuổi: %d", patient1.getAge()));

        return viewProduct;
    }
}