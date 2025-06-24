package com.datalift.data.di

import com.datalift.data.repository.CompositeUserRepository
import com.datalift.data.repository.DefaultRecentExerciseSearchRepository
import com.datalift.data.repository.OfflineFirstLoggedWorkoutRepository
import com.datalift.data.repository.OfflineFirstPostRepository
import com.datalift.data.repository.PostRepository
import com.datalift.data.repository.RecentExerciseSearchRepository
import com.datalift.data.repository.UserRepository
import com.datalift.data.repository.WorkoutRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindUserRepository(
        userRepository: CompositeUserRepository
    ) : UserRepository

    @Binds
    internal abstract fun bindPostRepository(
        postRepository: OfflineFirstPostRepository
    ) : PostRepository

    @Binds
    internal abstract fun bindsWorkoutRepository(
        workoutRepository: OfflineFirstLoggedWorkoutRepository
    ) : WorkoutRepository

    @Binds
    internal abstract fun bindsRecentExerciseSearchRepository(
        recentExerciseSearchRepository: DefaultRecentExerciseSearchRepository
    ) : RecentExerciseSearchRepository
}