package com.toier.toidoctor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;

public class DoctorAdapter extends ArrayAdapter<Doctor> {
    private OnDoctorClickListener doctorClickListener;

    public interface OnDoctorClickListener {
        void onDoctorClick(Doctor doctor);
    }

    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        super(context, 0, doctorList);
    }

    public void setOnDoctorClickListener(OnDoctorClickListener listener) {
        this.doctorClickListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Doctor doctor = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.doctor_item_layout, parent, false);
        }

        TextView doctorName = convertView.findViewById(R.id.doctorName);
        TextView doctorDepartment = convertView.findViewById(R.id.doctorDepartment);
        TextView doctorRate = convertView.findViewById(R.id.doctorRate);
        TextView doctorReview = convertView.findViewById(R.id.doctorReview);

        doctorName.setText(doctor.getName());
        doctorDepartment.setText(doctor.getDepartment());
        doctorRate.setText(NumberFormat.getInstance().format(doctor.getRate()) + "*");
        doctorReview.setText(NumberFormat.getInstance().format(doctor.getReview()) + " đánh giá");


//        convertView.setOnClickListener(v -> {
//            Intent intent = new Intent(getContext(), BookingClinicActivity.class);
//            intent.putExtra("selected_doctor", (CharSequence) doctor);
//            getContext().startActivity(intent);
//        });

        convertView.setOnClickListener(v -> {
            if (doctorClickListener != null) {
                doctorClickListener.onDoctorClick(doctor);
            }
        });

        return convertView;
    }
}

