package com.tripsero.app.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.tripsero.app.R;

public class SignUp1 extends AppCompatActivity {

    TextInputLayout fullName, username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_1);

        fullName = findViewById(R.id.signup_fullname);
        email = findViewById(R.id.signup_email);
        username = findViewById(R.id.signup_username);
        password = findViewById(R.id.signup_password);

    }

    public void callNextSigupScreen(View view) {

        if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()) {
            return;
        }

        String getfullName = fullName.getEditText().getText().toString();
        String getemail = email.getEditText().getText().toString();
        String getusername = username.getEditText().getText().toString();
        String getpassword = password.getEditText().getText().toString();


        //Pass data to 2nd activity
        Intent intent = new Intent(SignUp1.this, SignUp2.class);
        intent.putExtra("name", getfullName);
        intent.putExtra("email", getemail);
        intent.putExtra("username", getusername);
        intent.putExtra("password", getpassword);

        startActivity(intent);


    }


    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        //String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        }
        //else if (val.length() > 20) {
        //   username.setError("Username is too large!");
        //   return false;
        // } else if (!val.matches(checkspaces)) {
        //   username.setError("No White spaces are allowed!");
        //  return false;
        //
        //   }
        else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();

        //  String checkPassword = "^" +
        //"(?=.*[0-9])" +         //at least 1 digit
        //"(?=.*[a-z])" +         //at least 1 lower case letter
        //"(?=.*[A-Z])" +         //at least 1 upper case letter
        //  "(?=.*[a-zA-Z])" +      //any letter
        //"(?=.*[@#$%^&+=])" +    //at least 1 special character
        //  "(?=S+$)" +           //no white spaces
        //     ".{4,}" +               //at least 4 characters
        //    "$";

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        }
        //else if (!val.matches(checkPassword)) {
        // password.setError("Password should contain 4 characters!");
        // return false;
        // }
        else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}