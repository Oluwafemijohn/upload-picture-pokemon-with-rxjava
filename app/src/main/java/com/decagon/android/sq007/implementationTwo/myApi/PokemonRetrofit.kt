package com.decagon.android.sq007.implementationTwo.myApi

import com.decagon.android.sq007.implementationTwo.myApi.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonRetrofit {

    // the retrofit method
    fun getUploadApi(): MyApi {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(logging).build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)
    }
}
