package com.toier.toidoctor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.toier.toidoctor.controller.BookingController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private Context context;
    private List<Appointment> appointmentList = new ArrayList<>();

    public AppointmentAdapter() {
    }

    public void setAppointmentList(List<Appointment> appointments) {
        appointmentList = appointments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_noti, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);

        BookingController.getDoctorData(appointment.getDoctor_id(), new BookingController.OnDoctorDataListener() {
            @Override
            public void onDoctorDataReceived(Doctor doctor) {
                // Hiển thị tên của Doctor vào TextView
                holder.nameView.setText(doctor.getName());
                holder.rateView.setText(String.valueOf(doctor.getRate()));
                holder.reviewView.setText(String.format("(%d Đánh giá)",doctor.getReview()));
                holder.majorView.setText(doctor.getMajor());
            }
            @Override
            public void onDoctorDataError(String errorMessage) {
                // Xử lý lỗi nếu có
            }
        });

        if ( appointment.getTimestamp() != null ) {

            Log.d("DD", "duoc khong");

            Date data = appointment.getTimestamp().toDate();
            Calendar cl = Calendar.getInstance();
            cl.setTime(data);

            int date = cl.get(Calendar.DAY_OF_MONTH);
            int hour = cl.get(Calendar.HOUR_OF_DAY);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
            String month = dateFormat.format(cl.getTime());

            holder.scheduleView.setText(String.format("%d %s", date, month));
            holder.timeView.setText(String.format("%d:00", hour));
        }
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView majorView;
        TextView rateView;
        TextView reviewView;
        TextView scheduleView;
        TextView timeView;
        // Khai báo các thành phần khác của appointment (nếu có)

        public ViewHolder(View view) {
            super(view);
            nameView = (TextView) view.findViewById(R.id.noti_name);
            majorView = (TextView) view.findViewById(R.id.noti_major);
            rateView = (TextView) view.findViewById(R.id.noti_rate);
            reviewView = (TextView) view.findViewById(R.id.noti_review);
            scheduleView = (TextView) view.findViewById(R.id.noti_schedule);
            timeView = (TextView) view.findViewById(R.id.noti_time);
        }
    }
}
