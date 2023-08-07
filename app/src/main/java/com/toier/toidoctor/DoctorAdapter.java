package com.toier.toidoctor;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends ArrayAdapter<Doctor> {

    private class DoctorFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Doctor> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                // Nếu không có từ khoá tìm kiếm, hiển thị toàn bộ danh sách
                filteredList.addAll(doctorList);
            } else {
                // Nếu có từ khoá tìm kiếm, lọc danh sách theo từ khoá
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Doctor doctor : doctorList) {
                    if (doctor.getName().toLowerCase().contains(filterPattern)) {
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
            // Cập nhật danh sách hiển thị sau khi lọc
            clear();
            addAll((List<Doctor>) results.values);
            notifyDataSetChanged();
        }
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.doctor_item_layout, parent, false);
        }

        TextView doctorName = convertView.findViewById(R.id.doctorName);
        TextView doctorDepartment = convertView.findViewById(R.id.doctorDepartment);
        TextView doctorRate = convertView.findViewById(R.id.doctorRate);
        TextView doctorReview = convertView.findViewById(R.id.doctorReview);

        doctorName.setText(doctor.getName());
        doctorDepartment.setText(doctor.getMajor());
        doctorRate.setText(NumberFormat.getInstance().format(doctor.getRate()) + "*");
        doctorReview.setText(NumberFormat.getInstance().format(doctor.getReview()) + " đánh giá");


        return convertView;
    }

    public void showDoctorInfoDialog(Doctor doctor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(doctor.getName());
        builder.setMessage("Khoa khám: " + doctor.getMajor() + "\nID: " + doctor.getID() + "\nRate: " + doctor.getRate() + "\nReview: " + doctor.getReview()
        + "\nAddress: " + doctor.getAddress());

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateList(List<Doctor> newList) {
        this.doctorList = newList;
        notifyDataSetChanged();
    }
}

