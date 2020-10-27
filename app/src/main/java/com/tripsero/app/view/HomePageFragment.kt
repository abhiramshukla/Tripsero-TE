package com.tripsero.app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.tripsero.app.R
import com.tripsero.app.databinding.CardViewHomePageBinding
import com.tripsero.app.databinding.FragmentHomePageBinding
import com.tripsero.app.model.Destinations
import com.tripsero.app.model.Locations
import com.tripsero.app.viewModel.DestinationClient
import com.tripsero.app.viewModel.DestinationInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePageFragment : Fragment(), View.OnClickListener {

    private lateinit var homePageBinding: FragmentHomePageBinding
    private var destinations: Destinations? = null
    private lateinit var destinationService: DestinationInterface
    private var cardViewHomePageBinding = arrayOfNulls<CardViewHomePageBinding>(6)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homePageBinding = FragmentHomePageBinding.inflate(layoutInflater)

        val username = arguments!!.getString("username")
        val photoUrl = arguments!!.getString("photoUrl")

        homePageBinding.salutations.text = getString(R.string.salutations) + username
        //Picasso.get().load(photoUrl).into(homePageBinding.profilePic)

        val url = "https://api.jsonbin.io/b/5f797c747243cd7e8249f6d3/1"
        if (DestinationClient.getClient != null) {
            destinationService = DestinationClient.getClient!!
        }

        destinationService.getDestinationInformation(url)
            .enqueue(object : Callback<Destinations> {
                override fun onResponse(
                    call: Call<Destinations>,
                    response: Response<Destinations>
                ) {
                    destinations = response.body()!!
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Response Successful", Toast.LENGTH_LONG).show()
                        fillingContent(destinations!!)
                    }
                }

                override fun onFailure(call: Call<Destinations>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
            })

        return homePageBinding.root
    }

    private fun fillingContent(destinations: Destinations) {

        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(80,80,0,0)

        for (i in destinations.locations!!.indices) {
            cardViewHomePageBinding[i] = CardViewHomePageBinding.inflate(layoutInflater)
            cardViewHomePageBinding[i]?.parentCardView?.layoutParams = params

            if (i < 3) {
                homePageBinding.parentLinearLayout.addView(cardViewHomePageBinding[i]?.root)
            } else {
                homePageBinding.parentLinearLayout2.addView(cardViewHomePageBinding[i]?.root)
            }

            Picasso.get().load(destinations.locations!![i].icon).fit().into(cardViewHomePageBinding[i]?.image1)
            cardViewHomePageBinding[i]?.text1?.text = destinations.locations!![i].name

            cardViewHomePageBinding[i]?.image1?.setOnClickListener{
                moveToExplore(destinations.locations!![i])
            }
        }
    }

    private fun moveToExplore(locations: Locations) {
        val bundle = Bundle()
        bundle.putSerializable("location", locations)

        val exploreFragment = ExploreFragment()
        exploreFragment.arguments = bundle

        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.contain, exploreFragment)
        fragmentTransaction.addToBackStack("ExploreFragment")
        fragmentTransaction.commit()
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}