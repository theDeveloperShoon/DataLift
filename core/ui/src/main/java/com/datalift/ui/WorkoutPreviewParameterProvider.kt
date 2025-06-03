package com.datalift.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.datalift.model.data.Workout
import com.datalift.ui.PreviewParameterData.workouts

class WorkoutPreviewParameterProvider : PreviewParameterProvider<List<Workout>>{
    override val values: Sequence<List<Workout>> = sequenceOf(workouts)
}