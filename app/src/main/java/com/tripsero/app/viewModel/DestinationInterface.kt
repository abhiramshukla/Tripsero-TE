package com.tripsero.app.viewModel

import com.tripsero.app.model.Destinations
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface DestinationInterface {

    @GET
    fun getDestinationInformation(@Url url: String): Call<Destinations>
}