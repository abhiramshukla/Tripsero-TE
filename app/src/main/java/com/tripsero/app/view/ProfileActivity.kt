package com.tripsero.app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tripsero.app.R
import com.tripsero.app.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private lateinit var activityProfileBinding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)

        activityProfileBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomeScreenActivity::class.java)
                    startActivity(intent)
                }
                R.id.chats -> {
                    TODO()
                }
                R.id.profile -> {
                    if (firebaseUser != null) {
                        moveToProfileFragment()
                    } else {
                        val intent = Intent(this, HomeScreenActivity::class.java)
                        startActivity(intent)
                    }
                }
                R.id.people -> {
                    TODO()
                }
            }
            true
        }

        setContentView(activityProfileBinding.root)
    }

    override fun onBackPressed() {
        val pFragment = supportFragmentManager.findFragmentById(R.id.profile_contain)
        if (pFragment is UserProfilePageFragment) {
            val intent  = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }

    private fun moveToProfileFragment() {
        val userProfilePageFragment = UserProfilePageFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.profile_contain, userProfilePageFragment)
        transaction.addToBackStack("ProfilePageFragment")
        transaction.commit()
    }
}