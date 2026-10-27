package com.example.androidlearning.Domain

interface GetAppDataUseCase{
    suspend operator fun invoke(): List<AppData>
}

class GetAppDataUseCaseImpl(private val repository: AppDataRepository): GetAppDataUseCase{
    override suspend fun invoke(): List<AppData> {
        return repository.get()
    }
}