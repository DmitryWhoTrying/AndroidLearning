package com.example.androidlearning.Data.API

import retrofit2.http.GET
import retrofit2.http.Path

interface AppDataAPI{
    @GET("/catalog")
    suspend fun getAll() : List<AppDataCompact>

    @GET("/catalog/{id}")
    suspend fun getById(@Path("id") id: String) : AppDataFull
}