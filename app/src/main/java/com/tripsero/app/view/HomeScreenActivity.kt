package com.tripsero.app.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tripsero.app.R
import com.tripsero.app.databinding.ActivityHomeScreenBinding
import com.tripsero.app.view.login.LandingPage

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var homeScreenBinding: ActivityHomeScreenBinding
    private lateinit var bottomNavigation: BottomNavigationView
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeScreenBinding = ActivityHomeScreenBinding.inflate(layoutInflater)
        bottomNavigation = homeScreenBinding.bottomNavigation
        setSupportActionBar(homeScreenBinding.toolbar)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser != null) {
            moveToHomePageFragment(firebaseUser!!)
        } else {
            val intent = Intent(baseContext, LandingPage::class.java)
            startActivity(intent)
        }

        bottomNavigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId) {
                R.id.home -> {
                    moveToHomePageFragment(firebaseUser!!)
                }
                R.id.chats -> {
                    TODO()
                }
                R.id.profile -> {
                    val profileActivityIntent = Intent(this, ProfileActivity::class.java)
                    startActivity(profileActivityIntent)
                }
                R.id.people -> {
                    TODO()
                }
            }
            true
        }
        setContentView(homeScreenBinding.root)
    }

    override fun onBackPressed() {
        val mFragment = supportFragmentManager.findFragmentById(R.id.contain)
        if (mFragment is HomePageFragment) {
            this.finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun moveToHomePageFragment(user: FirebaseUser) {
        val bundle = Bundle()
        bundle.putString("username", user.displayName)
        bundle.putString("photoUrl", user.photoUrl.toString())
        val homePageFragment = HomePageFragment()
        homePageFragment.arguments = bundle
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.contain, homePageFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}