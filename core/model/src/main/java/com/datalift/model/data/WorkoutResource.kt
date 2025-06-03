package com.datalift.model.data

import kotlinx.datetime.Instant

data class WorkoutResource(
    val workoutId: String,
    val workoutName: String,
    val date: Instant,
    val muscleGroups: List<String>,
    val exercises: List<Exercise>
)

data class Exercise(
    val name: String,
    val bodyPart: String,
    val sets: List<ExerciseSet>,
){
    fun getSetsCount() : Int = sets.size
    fun getRepsCount() : Long = sets.sumOf { it.reps }
}

data class ExerciseSet(
    val reps: Long,
    val weight: Double
)

enum class MuscleGroup(val displayName: String) {
    Cardio("Cardio"),
    Push("Push"),
    Pull("Pull"),
    Legs("Legs"),
    Chest("Chest"),
    Arms("Arms"),
    Core("Core"),
    FullBody("FullBody");

    companion object {
        fun fromDisplayName(displayName: String): MuscleGroup? {
            return entries.find{ it.displayName.equals(displayName, ignoreCase = true) }
        }
    }
}