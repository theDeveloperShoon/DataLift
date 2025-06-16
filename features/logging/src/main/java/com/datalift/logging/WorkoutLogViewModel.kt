package com.datalift.logging

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datalift.data.repository.WorkoutRepository
import com.datalift.ui.WorkoutFeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutLogViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
): ViewModel() {
    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 0)

    var isRefreshing by mutableStateOf(false)
        private set

    fun onRefresh(){
        refreshTrigger.tryEmit(Unit)
    }

    val workoutFeedState: StateFlow<WorkoutFeedUiState> =
        refreshTrigger.onStart { emit(Unit) }
            .flatMapLatest {
                isRefreshing = true

                workoutRepository.getLoggedWorkouts()
                    .map(WorkoutFeedUiState::Success)
                    .onCompletion {
                        isRefreshing = false
                    }
                    .catch { WorkoutFeedUiState.Error }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = WorkoutFeedUiState.Loading
            )

    fun deleteWorkout(workoutId: String){
        viewModelScope.launch {
            workoutRepository.deleteWorkout(workoutId)
            refreshTrigger.emit(Unit)
        }
    }
}
