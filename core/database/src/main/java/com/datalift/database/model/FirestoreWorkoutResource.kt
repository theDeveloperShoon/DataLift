package com.datalift.database.model

import com.datalift.model.data.Exercise
import com.datalift.model.data.WorkoutResource
import com.google.firebase.Timestamp
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant

data class FirestoreWorkoutResource(
    val workoutId: String = "",
    val workoutName: String = "",
    val date: Timestamp = Timestamp.now(),
    val muscleGroups: List<String> = emptyList(),
    val exercises: List<Exercise> = emptyList()
)

fun FirestoreWorkoutResource.toWorkoutResource(): WorkoutResource =
    WorkoutResource(
        workoutId = this.workoutId,
        workoutName = this.workoutName,
        date = this.date.toInstant().toKotlinInstant(),
        muscleGroups = this.muscleGroups,
        exercises = this.exercises
    )

fun WorkoutResource.toFirestoreWorkoutResource(): FirestoreWorkoutResource =
    FirestoreWorkoutResource(
        workoutId = this.workoutId,
        workoutName = this.workoutName,
        date = Timestamp(this.date.toJavaInstant()),
        muscleGroups = this.muscleGroups,
        exercises = this.exercises
    )