package com.example.androidlearning.Domain

data class AppData(
    val id: String? = null,
    val appName: String?,
    val appGenre: String?,
    val appDescription: String?,
    val imagePath: String?,
    val screenshots: List<String>? = null,
    val appSize: Double? = null,
    val appAgeRating: Int? = null,
    var screenshotUrlList: List<String>? = null,
    var rating: Float? = null
)
