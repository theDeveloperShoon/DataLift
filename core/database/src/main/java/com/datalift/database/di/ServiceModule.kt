package com.datalift.database.di

import com.datalift.database.impl.AccountServiceImpl
import com.datalift.database.impl.PostServiceImpl
import com.datalift.database.impl.WorkoutServiceImpl
import com.datalift.database.service.AccountService
import com.datalift.database.service.PostService
import com.datalift.database.service.WorkoutService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds abstract fun provideAccountService(impl: AccountServiceImpl) : AccountService

    @Binds abstract fun providePostService(impl: PostServiceImpl) : PostService

    @Binds abstract fun provideWorkoutService(impl: WorkoutServiceImpl) : WorkoutService
}