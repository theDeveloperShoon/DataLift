package com.datalift.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.datalift.model.data.ExerciseResource
import com.datalift.ui.PreviewParameterData.exerciseResources

class ExerciseResourcePreviewParamterProvider : PreviewParameterProvider<List<ExerciseResource>> {
    override val values: Sequence<List<ExerciseResource>> = sequenceOf(exerciseResources)
}