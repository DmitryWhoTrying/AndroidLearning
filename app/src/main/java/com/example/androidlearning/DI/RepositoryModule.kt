package com.example.androidlearning.DI

import com.example.androidlearning.Data.AppDataRepositoryImpl
import com.example.androidlearning.Data.AppDetailsRepositoryImpl
import com.example.androidlearning.Domain.AppDataRepository
import com.example.androidlearning.Domain.AppDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppDataRepository(
        impl: AppDataRepositoryImpl
    ): AppDataRepository

    @Binds
    @Singleton
    abstract fun bindAppDetailsRepository(
        impl: AppDetailsRepositoryImpl
    ): AppDetailsRepository
}