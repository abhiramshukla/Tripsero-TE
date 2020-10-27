package com.tripsero.app.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tripsero.app.R;

import java.util.Calendar;

public class SignUp2 extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_2);

        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);
    }

    public void call3rdSigupScreen(View view) {

        if (!validateGender() || !validateAge()) {
            return;
        }
        selectedGender=findViewById(radioGroup.getCheckedRadioButtonId());
        String _gender =selectedGender.getText().toString();
        int day =datePicker.getDayOfMonth();
        int month =datePicker.getMonth();
        int year =datePicker.getYear();

        String _date =day+ "/" + month + "/" + year;

        String _fullName=getIntent().getStringExtra("name");
        String _username= getIntent().getStringExtra("username");
        String _email =getIntent().getStringExtra("email");
        String _password =getIntent().getStringExtra("password");

        Intent intent = new Intent(SignUp2.this, SignUp3.class);
        intent.putExtra("name", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("username",_username);
        intent.putExtra("password", _password);
        intent.putExtra("gender",_gender);
        intent.putExtra("dob",_date);
        startActivity(intent);


    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 10) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

}