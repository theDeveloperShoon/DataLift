package com.datalift.model.data

import kotlinx.datetime.Instant

/**
 * Data class representing a workout.
 *
 * It utilizes [WorkoutResource] to provide a complete representation of a workout.
 *
 * This is a finalized version of a workout that the user has created.
 *
 * @property workoutId The ID of the workout.
 * @property workoutName The name of the workout.
 * @property date The date when the workout was created.
 * @property muscleGroups The muscle groups targeted by the workout.
 * @property exercises The exercises included in the workout.
 *
 * @see WorkoutResource
 */
data class Workout internal constructor(
    val workoutId: String,
    val workoutName: String,
    val date: Instant,
    val muscleGroups: List<MuscleGroup>,
    val exercises: List<Exercise>
) {
    constructor(workoutResource: WorkoutResource) : this(
        workoutId = workoutResource.workoutId,
        workoutName = workoutResource.workoutName,
        date = workoutResource.date,
        muscleGroups = workoutResource.muscleGroups.mapNotNull { MuscleGroup.fromDisplayName(it) },
        exercises = workoutResource.exercises
    )

    fun getExerciseCount(): Int = exercises.size
}

fun List<WorkoutResource>.mapToWorkout(): List<Workout> =
    map { Workout(it) }