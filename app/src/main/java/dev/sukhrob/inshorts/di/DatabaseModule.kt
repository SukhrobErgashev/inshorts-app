package dev.sukhrob.inshorts.di;

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sukhrob.inshorts.data.local.database.InShortsDatabase
import dev.sukhrob.inshorts.data.local.database.dao.ArticlesDao

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): InShortsDatabase =
        InShortsDatabase.init(context)

    @Provides
    fun provideDao(database: InShortsDatabase): ArticlesDao = database.getInShortsDao()

}
