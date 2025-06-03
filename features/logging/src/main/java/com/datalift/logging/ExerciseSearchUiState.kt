package com.datalift.logging

sealed interface ExerciseSearchUiState {
    data object Loading : ExerciseSearchUiState

    data class Success(
        val exercises: List<String> = emptyList(),
    ) : ExerciseSearchUiState

    data object EmptyQuery: ExerciseSearchUiState

    data object LoadFailed : ExerciseSearchUiState

    data object SearchNotReady : ExerciseSearchUiState

}