package com.example.fishing_app.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// GET -> adnotacja dla zapytania
// Query -> parametr zapytania
// Call -> odpowiedz HTTP
interface WeatherApi {

    // getCurrentWeather -> wysyla GET do API, potrzebny jest klucz API
    @GET("v1/current.json")
    fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String
    ): Call<WeatherResponse>
}