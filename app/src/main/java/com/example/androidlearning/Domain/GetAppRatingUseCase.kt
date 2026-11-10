package com.example.androidlearning.Domain

import javax.inject.Inject

interface GetAppRatingUseCase{
    suspend operator fun invoke(appId: String): Float
}

class GetAppRatingUseCaseImpl @Inject
    constructor(private val repository: AppDataRepository)
    : GetAppRatingUseCase
{
    override suspend fun invoke(appId: String): Float {
        return repository.getRating(appId)
    }
}