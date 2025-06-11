package com.datalift.logging

import com.datalift.model.data.ExerciseResource

sealed interface ExerciseSearchUiState {
    data object Loading : ExerciseSearchUiState

    data class Success(
        val exercises: List<ExerciseResource> = emptyList(),
    ) : ExerciseSearchUiState

    data object EmptyQuery: ExerciseSearchUiState

    data object LoadFailed : ExerciseSearchUiState

    data object SearchNotReady : ExerciseSearchUiState

}