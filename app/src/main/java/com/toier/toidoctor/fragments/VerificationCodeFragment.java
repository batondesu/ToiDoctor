package com.toier.toidoctor.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.toier.toidoctor.controller.UserController;
import com.toier.toidoctor.LoginActivity;
import com.toier.toidoctor.R;

public class VerificationCodeFragment extends Fragment {
    private LoginActivity mLoginActivity;
    private EditText inputCode1;
    private EditText inputCode2;
    private EditText inputCode3;
    private EditText inputCode4;
    private EditText inputCode5;
    private EditText inputCode6;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mLoginActivity = (LoginActivity) getActivity();

        mLoginActivity.getBtnLogin().setText(R.string.text_link_verification);
        mLoginActivity.getTvPrimaryLogin().setText(R.string.text_verification_code);
        mLoginActivity.getTvSecondaryLogin().setVisibility(View.INVISIBLE);
        mLoginActivity.getTvLinkLogin().setVisibility(View.INVISIBLE);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_verification_code, null);
        inputCode1 = root.findViewById(R.id.inputCode1);
        inputCode2 = root.findViewById(R.id.inputCode2);
        inputCode3 = root.findViewById(R.id.inputCode3);
        inputCode4 = root.findViewById(R.id.inputCode4);
        inputCode5 = root.findViewById(R.id.inputCode5);
        inputCode6 = root.findViewById(R.id.inputCode6);

        setUpInput();

        mLoginActivity.getBtnLogin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginActivity.verifyCode(getCode());
                UserController.getInstance().createUser();
            }
        });

        return root;
    }

    private String getCode() {
        return inputCode1.getText().toString()
                + inputCode2.getText().toString()
                + inputCode3.getText().toString()
                + inputCode4.getText().toString()
                + inputCode5.getText().toString()
                + inputCode6.getText().toString();
    }

    private void setUpInput() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}