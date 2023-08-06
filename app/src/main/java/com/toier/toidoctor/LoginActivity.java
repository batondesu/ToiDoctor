package com.toier.toidoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.toier.toidoctor.enums.TypeFragment;
import com.toier.toidoctor.fragments.EnterInfoFragment;
import com.toier.toidoctor.fragments.SignInFragment;
import com.toier.toidoctor.fragments.SignUpFragment;
import com.toier.toidoctor.fragments.VerificationCodeFragment;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView tvPrimaryLogin;
    private TextView tvSecondaryLogin;
    private TextView tvLinkLogin;
    public FirebaseAuth mAuth;//Used for firebase authentication

    public String verificationId;

    private TypeFragment typeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        btnLogin = (Button) findViewById(R.id.btn_login);
        tvPrimaryLogin = (TextView) findViewById(R.id.text_view_primary_login);
        tvSecondaryLogin = (TextView) findViewById(R.id.text_view_secondary_login);
        tvLinkLogin = (TextView) findViewById(R.id.text_link_login);

        transactionToFragment(TypeFragment.SIGN_IN);
    }

    public void transactionToMainHome() {
        Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
        startActivity(intent);
    }

    public void transactionToFragment(TypeFragment newTypeFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (newTypeFragment) {
            case SIGN_IN:
                fragmentTransaction.replace(R.id.content_frame_login, new SignInFragment());
                break;
            case SIGN_UP:
                fragmentTransaction.replace(R.id.content_frame_login, new SignUpFragment());
                break;
            case INFO:
                fragmentTransaction.replace(R.id.content_frame_login, new EnterInfoFragment());
                break;
            case VERIFICATION_CODE:
                fragmentTransaction.replace(R.id.content_frame_login, new VerificationCodeFragment());
                break;
        }

        fragmentTransaction.commit();
        typeFragment = newTypeFragment;
    }

    public Button getBtnLogin() {
        return btnLogin;
    }

    public TextView getTvPrimaryLogin() {
        return tvPrimaryLogin;
    }

    public TextView getTvSecondaryLogin() {
        return tvSecondaryLogin;
    }

    public TextView getTvLinkLogin() {
        return tvLinkLogin;
    }

    public void signInWithCredential(PhoneAuthCredential credential) {
        this.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            transactionToMainHome();
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(this.mAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    public void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(this.verificationId, code);
        signInWithCredential(credential);
    }
}