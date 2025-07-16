package com.datalift.database.model

import com.datalift.model.data.ExerciseResource
import com.google.firebase.firestore.PropertyName

data class ExerciseQueryResource(
    @PropertyName("Title")
    val title: String = "",

    @PropertyName("Type")
    val type: String = "",

    @PropertyName("Desc")
    val description: String? = "",

    @PropertyName("BodyPart")
    val bodyPart: String = "",

    @PropertyName("Equipment")
    val equipment: String? = "",

    @PropertyName("Level")
    val level: String = "",
) {
    constructor() : this(
        title = "",
        type = "",
        description = "",
        bodyPart = "",
        equipment = "",
    )
}

fun ExerciseQueryResource.toExerciseResource(): ExerciseResource = ExerciseResource(
    title = this.title,
    type = this.type,
    description = this.description ?: "",
    bodyPart = this.bodyPart,
    equipment = this.equipment ?: "",
)