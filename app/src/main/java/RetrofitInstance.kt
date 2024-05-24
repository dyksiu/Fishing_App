package com.example.fishing_app.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// wykorzystany wzorzec Singleton -> jedna instancja Retrofit dla calej aplikacji
object RetrofitInstance {
    // ta zmienna przechowuje podstawowy URL API -> do niego beda kierowane wszystkie zadania
    // API jest darmowe -> trzeba bylo sie zalogowac aby wygenerowac API KEY
    private const val BASE_URL = "https://api.weatherapi.com/"

    // "by lazy" -> zapewnia ze Retrofit bedzie tworzony tylko wtedy gdy jest potrzebny
    //              zeby nie zasmiecac aplikacji
    val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}