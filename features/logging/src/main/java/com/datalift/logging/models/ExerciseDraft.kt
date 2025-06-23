package com.datalift.logging.models

import android.os.Parcelable
import com.datalift.model.data.Exercise
import com.datalift.model.data.ExerciseResource
import com.datalift.model.data.ExerciseSet
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseData(
    val name: String,
    val bodyPart: String,
) : Parcelable

@Parcelize
data class ExerciseDraft(
    val name: String,
    val bodyPart: String,
    val sets: List<ParcelableExerciseSet>
) : Parcelable

@Parcelize
data class ParcelableExerciseSet(
    val reps: Long,
    val weight: Double
) : Parcelable

fun List<ParcelableExerciseSet>.mapToExerciseSet(): List<ExerciseSet> =
    map { ExerciseSet(it.reps, it.weight) }

fun ExerciseDraft.toExercise(): Exercise = Exercise(
        exerciseResource = ExerciseResource(
            title = this.name,
            bodyPart = this.bodyPart
        ),
        sets = this.sets.mapToExerciseSet()
    )