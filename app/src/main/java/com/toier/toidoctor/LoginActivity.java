package com.toier.toidoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.toier.toidoctor.fragment.EnterInfoFragment;
import com.toier.toidoctor.fragment.SignInFragment;
import com.toier.toidoctor.fragment.SignUpFragment;
import com.toier.toidoctor.fragment.VerificationCodeFragment;

enum TypeFragment{
    SIGN_IN,
    SIGN_UP,
    INFO,
    VERIFICATION_CODE
}

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView tvPrimaryLogin;
    private TextView tvSecondaryLogin;
    private TextView tvLinkLogin;

    private TypeFragment typeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btn_login);
        tvPrimaryLogin = (TextView) findViewById(R.id.text_view_primary_login);
        tvSecondaryLogin = (TextView) findViewById(R.id.text_view_secondary_login);
        tvLinkLogin = (TextView) findViewById(R.id.text_link_login);

        transactionToFragment(TypeFragment.SIGN_IN);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (typeFragment) {
                    case SIGN_IN:
                        transactionToMainHome();
                        break;
                    case SIGN_UP:
                        transactionToFragment(TypeFragment.INFO);
                        break;
                    case INFO:
                        transactionToFragment(TypeFragment.VERIFICATION_CODE);
                        break;
                    case VERIFICATION_CODE:
                        transactionToMainHome();
                        break;
                }
            }
        });

        tvLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (typeFragment) {
                    case SIGN_IN:
                        transactionToFragment(TypeFragment.SIGN_UP);
                        break;
                    case SIGN_UP:
                        transactionToFragment(TypeFragment.SIGN_IN);
                        break;
                }
            }
        });
    }

    private void transactionToMainHome() {
        Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
        startActivity(intent);
    }

    private void transactionToFragment(TypeFragment newTypeFragment) {
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
}