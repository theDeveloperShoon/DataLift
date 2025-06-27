package com.datalift.logging

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.datalift.data.repository.WorkoutRepository
import com.datalift.logging.navigation.WorkoutViewRoute
import com.datalift.model.data.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    workoutRepository: WorkoutRepository
) : ViewModel() {
    
    private val workout = savedStateHandle.toRoute<WorkoutViewRoute>()

    val uiState: StateFlow<WorkoutDetailUiState> =
        workoutRepository
            .getWorkout(workout.workoutId)
            .map(WorkoutDetailUiState::Success)
            .catch { WorkoutDetailUiState.Error }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = WorkoutDetailUiState.Loading
            )


}

sealed interface WorkoutDetailUiState{
    data object Loading : WorkoutDetailUiState
    data class Success(val workout: Workout) : WorkoutDetailUiState
    data object Error : WorkoutDetailUiState
}