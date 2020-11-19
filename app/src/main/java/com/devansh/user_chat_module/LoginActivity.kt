package com.devansh.user_chat_module

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.devansh.user_chat_module.messages.LatestTextMessageActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        back_to_register_textview.setOnClickListener {
            finish()
        }


        login_button_login.setOnClickListener {
            performLogin()

        }

    }
     private fun performLogin() {
        val email = email_edittext_login.text.toString()
        val password = password_edittext_login.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
          Toast.makeText(this, "Please fill out email/pw.", Toast.LENGTH_SHORT).show()
          return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
              if (!it.isSuccessful) return@addOnCompleteListener

              val intent = Intent(this, LatestTextMessageActivity::class.java)
              intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
              startActivity(intent)
        }
        .addOnFailureListener {
              Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()
        }
}   }

