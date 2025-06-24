package com.datalift.logging

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datalift.data.repository.RecentExerciseSearchRepository
import com.datalift.data.repository.WorkoutRepository
import com.datalift.domain.GetRecentExerciseSearchQueriesUseCase
import com.datalift.model.data.ExerciseResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseSearchViewModel @Inject constructor(
    getRecentSearchQueriesUseCase: GetRecentExerciseSearchQueriesUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val workoutRepository: WorkoutRepository,
    private val recentExerciseSearchRepository: RecentExerciseSearchRepository
) : ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    val searchUiState: StateFlow<ExerciseSearchUiState> =
        searchQuery.flatMapLatest { query ->
            if(query.trim().length < SEARCH_QUERY_MIN_LENGTH){
                flowOf(ExerciseSearchUiState.EmptyQuery)
            } else {
                workoutRepository.queryExercise(query)
                    .map<List<ExerciseResource>, ExerciseSearchUiState> { exercises ->
                        ExerciseSearchUiState.Success(exercises)
                    }.catch {
                        emit(ExerciseSearchUiState.LoadFailed)
                    }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ExerciseSearchUiState.Loading
        )

    val recentSearchQueriesUiState: StateFlow<RecentSearchQueriesUiState> =
        getRecentSearchQueriesUseCase()
            .map(RecentSearchQueriesUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RecentSearchQueriesUiState.Loading
            )

    fun updateSearchQuery(query: String){
        savedStateHandle[SEARCH_QUERY] = query
    }

    fun onSearchTrigger(query: String){
        if(query.isBlank()) return
        viewModelScope.launch {
            recentExerciseSearchRepository.insertOrReplaceRecentSearch(query)
        }
    }
}

private const val SEARCH_QUERY = "exerciseSearchQuery"
private const val SEARCH_QUERY_MIN_LENGTH = 1