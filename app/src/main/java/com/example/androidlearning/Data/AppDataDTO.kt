package com.example.androidlearning.Data

data class AppDataDTO(
    val appName: String,
    val appGenre: String?,
    val appDescription: String?,
    val imagePath: String?,
    val appSize: Double,
    val appRating: Double,
)
