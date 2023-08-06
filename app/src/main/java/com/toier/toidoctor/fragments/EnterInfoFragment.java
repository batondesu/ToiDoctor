package com.toier.toidoctor.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.toier.toidoctor.AppointmentActivity;
import com.toier.toidoctor.Controller.UserController;
import com.toier.toidoctor.LoginActivity;
import com.toier.toidoctor.R;
import com.toier.toidoctor.enums.Sex;
import com.toier.toidoctor.enums.TypeFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EnterInfoFragment extends Fragment {
    private LoginActivity mLoginActivity;
    private TextInputEditText editTextEnterName;
    private TextInputEditText editTextEnterEmail;
    private ImageButton imgBtnBoy;
    private ImageButton imgBtnGirl;
    private TextView textViewSchedule;

    private Calendar calendar;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM\nyyyy", Locale.getDefault());

    private int sex;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mLoginActivity = (LoginActivity) getActivity();

        mLoginActivity.getBtnLogin().setText(R.string.text_link_continue);
        mLoginActivity.getTvPrimaryLogin().setText(R.string.text_your_info);
        mLoginActivity.getTvSecondaryLogin().setVisibility(View.INVISIBLE);
        mLoginActivity.getTvLinkLogin().setVisibility(View.INVISIBLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_enter_info, null);
        editTextEnterName = root.findViewById(R.id.enter_name);
        editTextEnterEmail = root.findViewById(R.id.enter_email);
        imgBtnBoy = root.findViewById(R.id.img_btn_boy);
        imgBtnGirl = root.findViewById(R.id.img_btn_girl);
        textViewSchedule = root.findViewById(R.id.text_view_schedule);

        sex = 0;
        setStyleImageButton(imgBtnBoy, false);
        setStyleImageButton(imgBtnGirl, false);

        this.calendar = Calendar.getInstance();
        textViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (v, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(1, year);
                    calendar.set(2, monthOfYear);
                    calendar.set(5, dayOfMonth);
                    textViewSchedule.setText(dateFormat.format(calendar.getTime()));
                }, calendar.get(1), calendar.get(2), calendar.get(5));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
        imgBtnBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = 1;
                setStyleImageButton(imgBtnBoy, true);
                setStyleImageButton(imgBtnGirl, false);
            }
        });

        imgBtnGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = -1;
                setStyleImageButton(imgBtnBoy, false);
                setStyleImageButton(imgBtnGirl, true);
            }
        });
        mLoginActivity.getBtnLogin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginActivity.sendVerificationCode(formatPhoneNumber(UserController.getInstance().getPhoneNumber()));
                UserController.getInstance().addInfo(editTextEnterName.getText().toString(), editTextEnterEmail.getText().toString(), (sex == 1 ? Sex.MALE : Sex.FEMALE));
                mLoginActivity.transactionToFragment(TypeFragment.VERIFICATION_CODE);
            }
        });

        return root;
    }

    private String formatPhoneNumber(String phone) {
        return "+84" + phone.substring(1);
    }

    private void setStyleImageButton(ImageButton imageButton, boolean able) {
        imageButton.setColorFilter(getResources().getColor((able ? R.color.primary_1 : R.color.black)), PorterDuff.Mode.SRC_IN);
    }
}