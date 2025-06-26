package com.datalift.model.data

data class WorkoutDraft(
    val workoutName: String,
    val muscleGroups: List<String>,
    val exercises: List<Exercise>

)
