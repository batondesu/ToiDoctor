package com.toier.toidoctor.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toier.toidoctor.LoginActivity;
import com.toier.toidoctor.R;

public class SignUpFragment extends Fragment {

    private LoginActivity mLoginActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mLoginActivity = (LoginActivity) getActivity();

        mLoginActivity.getBtnLogin().setText(R.string.text_link_sign_up);
        mLoginActivity.getTvPrimaryLogin().setText(R.string.text_sign_up);

        mLoginActivity.getTvSecondaryLogin().setVisibility(View.VISIBLE);
        mLoginActivity.getTvLinkLogin().setVisibility(View.VISIBLE);
        mLoginActivity.getTvSecondaryLogin().setText(R.string.text_have_account);
        mLoginActivity.getTvLinkLogin().setText(R.string.text_link_sign_in);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }
}