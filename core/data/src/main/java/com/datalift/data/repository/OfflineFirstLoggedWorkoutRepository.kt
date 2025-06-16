package com.datalift.data.repository

import com.datalift.database.service.WorkoutService
import com.datalift.model.data.Workout
import com.datalift.model.data.mapToWorkout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstLoggedWorkoutRepository @Inject constructor(
    private val workoutService: WorkoutService
): WorkoutRepository {
    override fun getLoggedWorkouts(): Flow<List<Workout>> =
        workoutService.getLoggedWorkoutsForUser().map {
            it.mapToWorkout()
        }

    override fun getWorkout(workoutId: String): Flow<Workout> =
        workoutService.getWorkout(workoutId = workoutId).map {
            Workout(it)
        }

    override suspend fun deleteWorkout(workoutId: String) {
        workoutService.deleteWorkout(workoutId = workoutId)
    }
}