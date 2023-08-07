package com.toier.toidoctor.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.toier.toidoctor.controller.UserController;
import com.toier.toidoctor.LoginActivity;
import com.toier.toidoctor.R;
import com.toier.toidoctor.enums.TypeFragment;


public class SignInFragment extends Fragment {
    private LoginActivity mLoginActivity;
    private TextInputEditText editTextEnterPhone;
    private TextInputEditText editTextEnterPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mLoginActivity = (LoginActivity) getActivity();

        mLoginActivity.getBtnLogin().setText(R.string.text_link_sign_in);
        mLoginActivity.getTvPrimaryLogin().setText(R.string.text_sign_in);

        mLoginActivity.getTvSecondaryLogin().setVisibility(View.VISIBLE);
        mLoginActivity.getTvLinkLogin().setVisibility(View.VISIBLE);
        mLoginActivity.getTvSecondaryLogin().setText(R.string.text_dont_have_account);
        mLoginActivity.getTvLinkLogin().setText(R.string.text_link_sign_up);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sign_in, null);
        editTextEnterPhone = root.findViewById(R.id.endter_phone_number);
        editTextEnterPassword = root.findViewById(R.id.endter_pasword);
        mLoginActivity.getBtnLogin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserController
                        .getInstance()
                        .checkVerificationUser(
                                editTextEnterPhone.getText().toString(),
                                editTextEnterPassword.getText().toString(),
                                new UserController.FirestoreCallback() {
                                    @Override
                                    public void checkExistUser(boolean ok) {
                                        if (ok) {
                                            mLoginActivity.transactionToMainHome();
                                            UserController.getInstance().getCurrentUserFromDB(editTextEnterPhone.getText().toString());
                                        } else {
                                            Toast.makeText(mLoginActivity, "Số điện thoại hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
            }
        });

        mLoginActivity.getTvLinkLogin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginActivity.transactionToFragment(TypeFragment.SIGN_UP);
            }
        });

        return root;
    }
    private void AllowUserToLogin() {
        String phone = editTextEnterPhone.getText().toString().trim();
        String pwd = editTextEnterPassword.getText().toString();

        mLoginActivity.mAuth.signInWithEmailAndPassword(phone,pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())//If account login successful print message and send user to main Activity
                            {
                                mLoginActivity.transactionToMainHome();
                                Toast.makeText(getActivity(),"Welcome to Reference Center",Toast.LENGTH_SHORT).show();
                            }
                            else//Print the error message incase of failure
                            {
                                String msg = task.getException().toString();
                                Toast.makeText(getActivity(),"Error: "+msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }
}