package com.datalift.database.di

import com.datalift.database.room.DataliftDatabase
import com.datalift.database.room.dao.RecentExerciseSearchQueryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun provideRecentExerciseSearchQueryDao(
        database: DataliftDatabase,
    ): RecentExerciseSearchQueryDao = database.recentExerciseSearchQueryDao()
}