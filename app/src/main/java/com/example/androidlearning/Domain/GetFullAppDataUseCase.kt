package com.example.androidlearning.Domain

import jakarta.inject.Inject

interface GetFullAppDataUseCase{
    suspend operator fun invoke(appId: String): AppData
}

class GetFullAppDataUseCaseImpl @Inject constructor(
    private val repository: AppDataRepository
): GetFullAppDataUseCase{
    override suspend fun invoke(appId: String): AppData {
        return repository.getAppFull(appId)
    }
}