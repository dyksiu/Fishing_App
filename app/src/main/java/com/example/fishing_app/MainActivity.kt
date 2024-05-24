package com.example.fishing_app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import com.example.fishing_app.model.RetrofitInstance
import com.example.fishing_app.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var buttonHandler: ButtonHandler
    private lateinit var dateTimeTextView: TextView
    private lateinit var weatherTextView: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val updateTimeTask = object : Runnable {
        override fun run() {
            updateDateTime()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dateTimeTextView = findViewById(R.id.dateTimeTextView)
        weatherTextView = findViewById(R.id.weatherTextView)

        buttonHandler = ButtonHandler(this)

        // konfiguracja przycisków widocznych na ekranie
        findViewById<Button>(R.id.button1).setOnClickListener {
            buttonHandler.startFirstActivity()
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            startActivity(Intent(this, FishingCatchActivity::class.java))
        }
        findViewById<Button>(R.id.button5).setOnClickListener {
            startActivity(Intent(this, ViewCatchesActivity::class.java))
        }

        handler.post(updateTimeTask)
        fetchWeather()
    }

    // funkcja odpowiedizalna za aktualizacje daty i czasu
    private fun updateDateTime() {
        val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        dateTimeTextView.text = currentDateTime
    }

    // funkcja odpowiedzialna za pobieranie pogody z API
    // -> https://www.weatherapi.com - API ktore jest wykorzystywane w aplikacji
    private fun fetchWeather() {
        val apiKey = "378a357439de4bc1b58101738242305"
        val location = "Poznan"

        RetrofitInstance.api.getCurrentWeather(apiKey, location).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weather = response.body()
                    weather?.let {
                        val weatherText = getString(R.string.weather_description,
                            it.location.name,
                            it.location.country,
                            it.current.temp_c,
                            it.current.condition.text
                        )
                        weatherTextView.text = weatherText
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                weatherTextView.text = getString(R.string.weather_error)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateTimeTask)
    }
}