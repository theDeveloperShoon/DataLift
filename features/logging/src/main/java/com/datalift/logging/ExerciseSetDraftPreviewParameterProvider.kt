package com.datalift.logging

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.datalift.logging.models.ExerciseSetDraft
import com.datalift.logging.models.toExerciseSetDraft
import com.datalift.ui.PreviewParameterData.workouts

class ExerciseSetDraftPreviewParameterProvider : PreviewParameterProvider<List<ExerciseSetDraft>> {
    override val values: Sequence<List<ExerciseSetDraft>> = sequenceOf(
        workouts[0].exercises[0].sets.map { it.toExerciseSetDraft() }
    )
}