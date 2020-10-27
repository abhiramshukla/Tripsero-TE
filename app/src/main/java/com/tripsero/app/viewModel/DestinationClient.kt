package com.tripsero.app.viewModel

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DestinationClient {

    private const val BASE_URL: String = "https://api.jsonbin.io/"

    val getClient: DestinationInterface?
        get() {
            val gSon = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gSon))
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(DestinationInterface::class.java)
        }
}