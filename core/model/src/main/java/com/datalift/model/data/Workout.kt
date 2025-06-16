package com.datalift.model.data

import kotlinx.datetime.Instant

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