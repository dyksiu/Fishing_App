package com.example.fishing_app.model

// odpowiedz z API:
// -> Location -> lokalizacja
// -> Current -> aktualna pogoda

// reszty nie opisuje -> tak bylo na stronce tego API
data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime_epoch: Long,
    val localtime: String
)

data class Current(
    val temp_c: Double,
    val condition: Condition,
    val wind_kph: Double,
    val wind_degree: Int,
    val wind_dir: String,
    val pressure_mb: Double,
    val precip_mm: Double,
    val humidity: Int,
    val cloud: Int,
    val feelslike_c: Double,
    val vis_km: Double,
    val uv: Double,
    val gust_kph: Double
)

data class Condition(
    val text: String,
    val icon: String,
    val code: Int
)