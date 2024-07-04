package com.example.medimate

import com.example.medimate.API.API_Builder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val client: OkHttpClient = OkHttpClient.Builder().build()

    val api: API_Builder = Retrofit.Builder()
        .client(client).baseUrl(API_Builder.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build().create(API_Builder::class.java)

}