package com.tripsero.app.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.tripsero.app.R
import com.tripsero.app.databinding.LoginBinding
import com.tripsero.app.view.HomeScreenActivity

class LoginPage : AppCompatActivity() {

    private lateinit var loginBinding: LoginBinding
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = LoginBinding.inflate(layoutInflater)
        etEmail = loginBinding.etEmail
        etPassword = loginBinding.etPassword
        setContentView(loginBinding.root)
    }

    fun tvResetPasswordClick(view: View) {
        val intent = Intent(this, ResetPassword::class.java)
        startActivity(intent)
    }

    fun btnLoginClick(view: View) {
        email = etEmail.text.toString().trim()
        password = etPassword.text.toString().trim()

        if (email.equals("")) {
            etEmail.error = getString(R.string.enter_your_email)
        } else if (password.equals("")) {
            etPassword.error = getString(R.string.enter_your_password)
        } else {
            val firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                    override fun onComplete(p0: Task<AuthResult>) {

                        if (p0.isSuccessful) {
                            val intent = Intent(this@LoginPage, HomeScreenActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@LoginPage,
                                "Login Failed : " + p0.exception,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
        }
    }

    fun tvSignupClick(view: View) {
        val int = Intent(this, SignUp1::class.java)
        startActivity(int)
    }
}