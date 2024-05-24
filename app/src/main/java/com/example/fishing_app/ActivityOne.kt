package com.example.fishing_app

import FishListFragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import android.view.View
import androidx.cardview.widget.CardView
import com.example.fishing_app.model.RetrofitInstance
import com.example.fishing_app.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ActivityOne : AppCompatActivity() {
    private lateinit var buttonBar1: Button
    private lateinit var buttonBar2: Button
    private lateinit var buttonBar3: Button
    private lateinit var cardView: CardView
    private lateinit var mainView: View
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
        setContentView(R.layout.activity_one)

        mainView = findViewById(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dateTimeTextView = findViewById(R.id.dateTimeTextView)
        weatherTextView = findViewById(R.id.weatherTextView)
        cardView = findViewById(R.id.cardView)

        buttonBar1 = findViewById(R.id.buttonBar1)
        buttonBar2 = findViewById(R.id.buttonBar2)
        buttonBar3 = findViewById(R.id.buttonBar3)


        buttonBar1.setOnClickListener {
            changeBackgroundToBlack()
            hideElements()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main, FishListFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        buttonBar2.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://angloo.com/kalendarz-bran/"))
            startActivity(intent)
        }
        buttonBar3.setOnClickListener {
            val intent = Intent(this, Activity_Hook::class.java)
            startActivity(intent)
        }

        handler.post(updateTimeTask)
        fetchWeather()

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                resetBackgroundToImage()
                showElements()
            }
        }
    }

    // funkcja ktora ukrywa przyciski i cardView z danymi z API
    private fun hideElements() {
        buttonBar1.visibility = View.GONE
        buttonBar2.visibility = View.GONE
        buttonBar3.visibility = View.GONE
        cardView.visibility = View.GONE
    }

    // przywraca przyciski i CardView z danimi z API
    private fun showElements() {
        buttonBar1.visibility = View.VISIBLE
        buttonBar2.visibility = View.VISIBLE
        buttonBar3.visibility = View.VISIBLE
        cardView.visibility = View.VISIBLE
    }

    // zmiana koloru tla na czarne w momencie wybrania aktywnosci z haczykiem
    private fun changeBackgroundToBlack() {
        mainView.setBackgroundColor(resources.getColor(android.R.color.black))
    }

    // powrot do oryginalnego tla
    private fun resetBackgroundToImage() {
        mainView.setBackgroundResource(R.drawable.tutorial_layout)
    }

    // aktualizacja tekstu wyswietlajacego biezaca date i godzine
    private fun updateDateTime() {
        val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        dateTimeTextView.text = currentDateTime
    }

    // pobieranie danych pogodowych z API i aktualizacja tekstu
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

    // cykl zycia aktywnosci -> zabezpieczenie przed memory leakiem
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateTimeTask)
    }

    override fun onResume() {
        super.onResume()
        showElements()
    }
}
