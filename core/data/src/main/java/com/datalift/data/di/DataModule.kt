package com.datalift.data.di

import com.datalift.data.repository.CompositeUserRepository
import com.datalift.data.repository.OfflineFirstPostRepository
import com.datalift.data.repository.PostRepository
import com.datalift.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindPostRepository(
        postRepository: OfflineFirstPostRepository
    ) : PostRepository

    @Binds
    internal abstract fun bindUserRepository(
        userRepository: CompositeUserRepository
    ) : UserRepository
}