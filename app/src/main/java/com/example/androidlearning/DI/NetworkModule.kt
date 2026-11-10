package com.example.androidlearning.DI

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java
import com.example.androidlearning.Data.API.AppDataAPI

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //по хорошему из local.properties тянуть, но пока так
    //http://185.103.109.134
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://185.103.109.134")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAppDataAPI(retrofit: Retrofit): AppDataAPI =
        retrofit.create(AppDataAPI::class.java)
}