package com.datalift.model.data

/**
 * Data class representing an exercise resource.
 *
 * This is an exercise that is available from the ExerciseList collection
 * in the database.
 *
 * @property title The title of the exercise.
 * @property type The type of the exercise.
 * @property description A description of the exercise.
 * @property bodyPart The body part that the exercise is targeting.
 * @property equipment The equipment required to perform the exercise.
 * @property level The level of difficulty of the exercise.
 */
data class ExerciseResource(
    val title: String = "",
    val type: String = "",
    val description: String = "",
    val bodyPart: String = "",
    val equipment: String = "",
    val level: String = "",
)