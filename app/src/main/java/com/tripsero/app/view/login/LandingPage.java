package com.tripsero.app.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.tripsero.app.R;

public class LandingPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);


    }

    public void to_login_pg(View view) {
        startActivity(new Intent(this, Login.class));
    }


}
