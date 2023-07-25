package com.toier.toidoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.toier.toidoctor.enums.TypeFragment;
import com.toier.toidoctor.fragments.EnterInfoFragment;
import com.toier.toidoctor.fragments.SignInFragment;
import com.toier.toidoctor.fragments.SignUpFragment;
import com.toier.toidoctor.fragments.VerificationCodeFragment;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView tvPrimaryLogin;
    private TextView tvSecondaryLogin;
    private TextView tvLinkLogin;

    public FirebaseUser currentUser;//used to store current user of account
    public FirebaseAuth mAuth;//Used for firebase authentication

    private TypeFragment typeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

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

}