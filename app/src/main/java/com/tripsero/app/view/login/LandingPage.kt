package com.tripsero.app.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tripsero.app.R

class LandingPage: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page)
    }

    fun toLoginPage(view: View) {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }
}