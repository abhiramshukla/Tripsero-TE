package com.tripsero.app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import com.tripsero.app.databinding.CardViewHomePageBinding
import com.tripsero.app.databinding.FragmentExploreBinding
import com.tripsero.app.model.Locations
import kotlinx.android.synthetic.main.fragment_explore.*

class ExploreFragment : Fragment() {

    private lateinit var exploreBinding: FragmentExploreBinding
    private val cardViewHomePageBindingActivities = arrayOfNulls<CardViewHomePageBinding>(3)
    private val cardViewHomePageBindingPlaces = arrayOfNulls<CardViewHomePageBinding>(3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exploreBinding = FragmentExploreBinding.inflate(layoutInflater)
        val locations = arguments!!.getSerializable("location") as Locations
        val destinationName = locations.name
        val coverImage = locations.icon
        val destinationInfoText = locations.about

        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(80,80,0,0)

        for (i in locations.activities!!.indices) {
            cardViewHomePageBindingActivities[i] = CardViewHomePageBinding.inflate(layoutInflater)
            cardViewHomePageBindingActivities[i]?.parentCardView?.layoutParams = params
            cardViewHomePageBindingActivities[i]?.text1?.text = locations.activities!![i].name
            Picasso.get().load(locations.activities!![i].icon).fit().into(cardViewHomePageBindingActivities[i]?.image1)

            exploreBinding.parentLinearLayoutActivities.addView(cardViewHomePageBindingActivities[i]?.root)
        }

        for (i in locations.places!!.indices) {
            cardViewHomePageBindingPlaces[i] = CardViewHomePageBinding.inflate(layoutInflater)
            cardViewHomePageBindingPlaces[i]?.parentCardView?.layoutParams = params
            cardViewHomePageBindingPlaces[i]?.text1?.text = locations.places!![i].name
            Picasso.get().load(locations.places!![i].icon).fit().into(cardViewHomePageBindingPlaces[i]?.image1)

            exploreBinding.parentLinearLayoutPlaces.addView(cardViewHomePageBindingPlaces[i]?.root)
        }

        /*exploreBinding.coverImage.viewTreeObserver.addOnGlobalLayoutListener( object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                cover_image.viewTreeObserver.removeOnGlobalLayoutListener(this)
                Picasso.get().load(coverImage).resize(exploreBinding.coverImage.measuredWidth, 0).into(exploreBinding.coverImage)
            }
        })*/

        exploreBinding.locationName.text = destinationName
        exploreBinding.destinationInfo.text = destinationInfoText
        Picasso.get().load(coverImage).into(exploreBinding.coverImage)
        return exploreBinding.root
    }
}