package com.example.retrofitforecaster.network

import com.example.retrofitforecaster.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ): WeatherResponse
}