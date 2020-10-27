package com.tripsero.app.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tripsero.app.R;
import com.tripsero.app.view.HomeScreenActivity;

public class Login extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }

    public void tvResetPasswordClick(View view) {
        startActivity(new Intent(this, ResetPassword.class));
    }

    public void btnLoginClick(View view) {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();



        if (email.equals("")) {
            etEmail.setError(getString(R.string.enter_your_email));
        } else if (password.equals("")) {
            etPassword.setError(getString(R.string.enter_your_password));
        } else {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        startActivity(new Intent(Login.this, HomeScreenActivity.class));
                        finish();

                    } else {
                        Toast.makeText(Login.this, "Login Failed : " +
                                task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }




    public void tvSignupClick(View view) {
        startActivity(new Intent(Login.this ,SignUp1.class));
    }

}