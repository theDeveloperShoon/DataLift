package com.datalift.logging

sealed interface RecentSearchQueriesUiState {
    data object Loading : RecentSearchQueriesUiState

    data class Success(
        val recentSearchQueries: List<String> = emptyList(),
    ) : RecentSearchQueriesUiState
}