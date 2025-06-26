package com.datalift.data.repository

import com.datalift.model.data.ExerciseResource
import com.datalift.model.data.Workout
import com.datalift.model.data.WorkoutDraft
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getLoggedWorkouts() : Flow<List<Workout>>
    fun getWorkout(workoutId: String) : Flow<Workout>
    fun queryExercise(string: String) : Flow<List<ExerciseResource>>
    suspend fun saveWorkout(workout: WorkoutDraft)
    suspend fun deleteWorkout(workoutId: String)
}