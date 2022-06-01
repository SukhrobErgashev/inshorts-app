package dev.sukhrob.inshorts.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sukhrob.inshorts.domain.repository.InShortsRepository
import dev.sukhrob.inshorts.domain.repository.InShortsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindRepository(impl: InShortsRepositoryImpl): InShortsRepository

}