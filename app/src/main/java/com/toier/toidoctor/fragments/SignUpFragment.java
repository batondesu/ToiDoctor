package com.toier.toidoctor.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.toier.toidoctor.LoginActivity;
import com.toier.toidoctor.R;
import com.toier.toidoctor.controllers.UserControlller;
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
                createNewAccount();
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

    private void createNewAccount() {
        String email = editTextEnterPhone.getText().toString().trim();
        String pwd = editTextEnterPassword.getText().toString();
        String repwd = editTextReEnterPassword.getText().toString();

        if (!pwd.equals(repwd)) {
            Toast.makeText(getActivity(),"Mật khẩu nhập lại không đúng",Toast.LENGTH_SHORT).show();
        } else {
            mLoginActivity.mAuth.createUserWithEmailAndPassword(email,pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())//If account creation successful print message and send user to Login Activity
                            {
                                Toast.makeText(getActivity(),"Account created successfully",Toast.LENGTH_SHORT).show();
                            }
                            else//Print the error message incase of failure
                            {
                                String msg = task.getException().toString();
                                Toast.makeText(getActivity(),"Error: "+msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            mLoginActivity.transactionToFragment(TypeFragment.SIGN_IN);
        }
    }
}