package com.datalift.data.di

import com.datalift.data.repository.CompositeUserResourceRepository
import com.datalift.data.repository.UserResourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UserResourceRepositoryModule {
    @Binds
    fun bindsUserResourceRepository(
        userResourceRepository: CompositeUserResourceRepository
    ) : UserResourceRepository
}