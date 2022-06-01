package dev.sukhrob.inshorts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sukhrob.inshorts.data.remote.api.InShortsApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @[Provides Singleton]
    fun provideApi(retrofit: Retrofit): InShortsApi = retrofit.create(InShortsApi::class.java)

}