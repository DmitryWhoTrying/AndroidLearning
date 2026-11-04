package com.example.androidlearning.DI

import com.example.androidlearning.Data.AppDataRepositoryImpl
import com.example.androidlearning.Domain.AppDataRepository
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
}