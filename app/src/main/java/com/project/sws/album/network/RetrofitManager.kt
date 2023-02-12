package com.project.sws.album.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    private val retrofit: Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(ITUNES_PATH)
        .build()

    val apiServices: ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }
}

