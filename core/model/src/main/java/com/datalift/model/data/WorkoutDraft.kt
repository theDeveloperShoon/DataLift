package com.datalift.model.data

/**
 * Data class representing a workout draft.
 *
 * This is a draft of a workout that the user is creating.
 *
 * @property workoutName The name of the workout.
 * @property muscleGroups The muscle groups targeted by the workout.
 * @property exercises The exercises included in the workout.
 */
data class WorkoutDraft(
    val workoutName: String,
    val muscleGroups: List<String>,
    val exercises: List<Exercise>

)
