package com.datalift.database.service

import com.datalift.model.data.ExerciseResource
import com.datalift.model.data.WorkoutResource
import kotlinx.coroutines.flow.Flow

interface WorkoutService {
    fun getLoggedWorkoutsForUser() : Flow<List<WorkoutResource>>
    fun getWorkout(workoutId: String) : Flow<WorkoutResource>
    fun queryExercise(query: String) : Flow<List<ExerciseResource>>
    suspend fun deleteWorkout(workoutId: String)
}