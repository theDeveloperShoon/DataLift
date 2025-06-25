package com.datalift.logging.models

import android.os.Parcelable
import com.datalift.model.data.Exercise
import com.datalift.model.data.ExerciseResource
import com.datalift.model.data.ExerciseSet
import kotlinx.parcelize.Parcelize
import java.util.UUID
import kotlin.math.abs

data class ExerciseSetDraft(
    val id: String = UUID.randomUUID().toString(),
    val reps: Long,
    val weightWhole: Int,
    val weightDecimal: Int
){
    fun formattedWeightString(): String = "$weightWhole.$weightDecimal lbs"
    fun toParcelableExerciseSet() = ParcelableExerciseSet(
        reps = reps,
        weight = weightWhole + (weightDecimal / 10.0)
    )
}

internal fun ExerciseSet.toExerciseSetDraft() = ExerciseSetDraft(
    reps = reps,
    weightWhole = weight.toInt(),
    weightDecimal = ((abs(weight) * 10).toInt()) % 10
)

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