package com.example.androidlearning.Domain

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

interface AppDataRepository {
    suspend fun get():List<AppData>
    suspend fun getAppFull(appId: String): AppData

    suspend fun getRating(id: String): Float {
        delay(150.milliseconds) // эмуляция сетевого запроса
        return (30..50).random() / 10f
    }
}