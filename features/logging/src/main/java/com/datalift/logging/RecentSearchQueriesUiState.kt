package com.datalift.logging

import com.datalift.data.model.RecentExerciseSearchQuery

sealed interface RecentSearchQueriesUiState {
    data object Loading : RecentSearchQueriesUiState

    data class Success(
        val recentSearchQueries: List<RecentExerciseSearchQuery> = emptyList(),
    ) : RecentSearchQueriesUiState
}