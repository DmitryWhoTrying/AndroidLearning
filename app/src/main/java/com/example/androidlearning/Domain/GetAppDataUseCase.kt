package com.example.androidlearning.Domain

import jakarta.inject.Inject

interface GetAppDataUseCase{
    suspend operator fun invoke(): List<AppData>
}

class GetAppDataUseCaseImpl @Inject constructor(
    private val repository: AppDataRepository
) : GetAppDataUseCase{
    override suspend fun invoke(): List<AppData> {
        return repository.get()
    }
}