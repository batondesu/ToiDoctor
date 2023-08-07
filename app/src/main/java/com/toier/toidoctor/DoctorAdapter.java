package com.toier.toidoctor;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends ArrayAdapter<Doctor> implements Filterable {

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Doctor> filteredList = new ArrayList<>();

                // Nếu từ khoá tìm kiếm là null hoặc rỗng, hiển thị toàn bộ danh sách
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(doctorList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    // Lọc danh sách bác sĩ có chứa từ khoá
                    for (Doctor doctor : doctorList) {
                        if (doctor.getName().toLowerCase().contains(filterPattern) ||
                                doctor.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(doctor);
                        }
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // Cập nhật danh sách bác sĩ hiển thị trên ListView
                clear();
                addAll((List<Doctor>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    private List<Doctor> doctorList;
    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        super(context, 0, doctorList);
        this.doctorList = doctorList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Doctor doctor = this.getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.doctor, parent, false);
        }

//        TextView doctorName = convertView.findViewById(R.id.doctor);
//        TextView doctorDepartment = convertView.findViewById(R.id.majors);
//        TextView doctorRate = convertView.findViewById(R.id.rate);
//        TextView doctorReview = convertView.findViewById(R.id.review);
//
//        doctorName.setText(doctor.getName());
//        doctorDepartment.setText(doctor.getMajor());
//        doctorRate.setText(NumberFormat.getInstance().format(doctor.getRate()) + "*");
//        doctorReview.setText(NumberFormat.getInstance().format(doctor.getReview()) + " đánh giá");

        ((TextView) convertView.findViewById(R.id.doctor)).setText(String.format(doctor.getName()));
        ((TextView) convertView.findViewById(R.id.majors)).setText(String.format(doctor.getMajor()));
        ((TextView) convertView.findViewById(R.id.review)).setText(String.format("(%d Đánh giá)", doctor.getReview()));
        ((TextView) convertView.findViewById(R.id.rate)).setText(String.format(String.valueOf(doctor.getRate())));


        return convertView;
    }
}

