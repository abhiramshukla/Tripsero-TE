package com.tripsero.app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.tripsero.app.R
import com.tripsero.app.databinding.FragmentUserProfilePageBinding

class UserProfilePageFragment : Fragment() {

    private lateinit var fragmentUserProfilePageBinding: FragmentUserProfilePageBinding
    private var user = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentUserProfilePageBinding = FragmentUserProfilePageBinding.inflate(layoutInflater)
        return fragmentUserProfilePageBinding.root
    }
    
}