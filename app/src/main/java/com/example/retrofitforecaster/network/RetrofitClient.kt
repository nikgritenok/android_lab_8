package com.example.retrofitforecaster.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)  // Увеличьте время соединения
    .readTimeout(30, TimeUnit.SECONDS)     // Увеличьте время чтения
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.openweathermap.org/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(WeatherApiService::class.java)