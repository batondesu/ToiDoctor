package com.toier.toidoctor.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toier.toidoctor.LoginActivity;
import com.toier.toidoctor.R;
public class VerificationCodeFragment extends Fragment {
    private LoginActivity mLoginActivity;

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
        return inflater.inflate(R.layout.fragment_verification_code, container, false);
    }
}