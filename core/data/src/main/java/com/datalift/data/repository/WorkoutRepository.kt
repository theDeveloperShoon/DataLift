package com.datalift.data.repository

import com.datalift.model.data.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getLoggedWorkouts() : Flow<List<Workout>>
    fun getWorkout(workoutId: String) : Flow<Workout>
    suspend fun deleteWorkout(workoutId: String)
}