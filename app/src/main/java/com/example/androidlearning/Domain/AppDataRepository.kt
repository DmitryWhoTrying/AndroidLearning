package com.example.androidlearning.Domain

interface AppDataRepository {
    suspend fun get():List<AppData>
}