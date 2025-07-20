package com.datalift.model.data

import kotlinx.datetime.Instant

/**
 * Data class representing a workout resource.
 *
 * This is a workout that is available from the WorkoutList collection in the database.
 */
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
    constructor() : this(
        name = "",
        bodyPart = "",
        sets = emptyList()
    )
    constructor(exerciseResource: ExerciseResource) : this(
        name = exerciseResource.title,
        bodyPart = exerciseResource.bodyPart,
        sets = emptyList()
    )
    constructor(
        exerciseResource: ExerciseResource,
        sets: List<ExerciseSet>
    ) : this(
        name = exerciseResource.title,
        bodyPart = exerciseResource.bodyPart,
        sets = sets
    )

    fun getSetsCount() : Int = sets.size
    fun getRepsCount() : Long = sets.sumOf { it.reps }
}

data class ExerciseSet(
    val reps: Long,
    val weight: Double
){
    constructor(): this(
        reps = 0,
        weight = 0.0
    )
}

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