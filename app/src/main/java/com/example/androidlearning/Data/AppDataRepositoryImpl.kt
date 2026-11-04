package com.example.androidlearning.Data

import com.example.androidlearning.Data.API.AppDataAPI
import com.example.androidlearning.Domain.AppData
import com.example.androidlearning.Domain.AppDataRepository
import jakarta.inject.Inject

class AppDataRepositoryImpl @Inject constructor(
    private val api: AppDataAPI,
) : AppDataRepository {

    override suspend fun get(): List<AppData> {
        val data = api.getAll()
        return data.map{it -> AppDataMapper.map(it)}
    }
}