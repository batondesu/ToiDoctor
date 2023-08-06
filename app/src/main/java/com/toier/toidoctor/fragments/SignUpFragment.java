package com.toier.toidoctor.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputEditText;
import com.toier.toidoctor.controller.UserController;
import com.toier.toidoctor.LoginActivity;
import com.toier.toidoctor.R;
import com.toier.toidoctor.enums.TypeFragment;

public class SignUpFragment extends Fragment {

    private LoginActivity mLoginActivity;
    private TextInputEditText editTextEnterPhone;
    private TextInputEditText editTextEnterPassword;
    private TextInputEditText editTextReEnterPassword;

    private CheckBox checkBoxAgreeTerm;

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

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up, null);
        editTextEnterPhone = root.findViewById(R.id.endter_phone_number);
        editTextEnterPassword = root.findViewById(R.id.endter_pasword);
        editTextReEnterPassword = root.findViewById(R.id.re_endter_pasword);
        checkBoxAgreeTerm = root.findViewById(R.id.agree_term);
        mLoginActivity.getBtnLogin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkForm()) {

                } else {
                    UserController.getInstance()
                            .checkExistUser(
                                    editTextEnterPhone.getText().toString(),
                                    new UserController.FirestoreCallback() {
                                        @Override
                                        public void checkExistUser(boolean ok) {
                                            if (ok) {
                                                // Exited User
                                            } else {
                                                UserController.getInstance().addPhoneAndPassword(editTextEnterPhone.getText().toString(), editTextEnterPassword.getText().toString());
                                                mLoginActivity.transactionToFragment(TypeFragment.INFO);
                                            }
                                        }
                                    });
                }
            }
        });

        mLoginActivity.getTvLinkLogin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginActivity.transactionToFragment(TypeFragment.SIGN_IN);
            }
        });

        return root;
    }

    private boolean checkForm() {
        if (!editTextEnterPassword.getText().toString().equals(editTextReEnterPassword.getText().toString())) {
            return false;
        } else if (!checkBoxAgreeTerm.isChecked()) {
            return false;
        }

        return true;
    }
}