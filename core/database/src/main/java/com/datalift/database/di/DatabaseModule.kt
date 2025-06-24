package com.datalift.database.di

import android.content.Context
import androidx.room.Room
import com.datalift.database.room.DataliftDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesDataliftDatabase(
        @ApplicationContext context: Context,
    ): DataliftDatabase = Room.databaseBuilder(
        context,
        DataliftDatabase::class.java,
        "datalift-database"
    ).build()
}