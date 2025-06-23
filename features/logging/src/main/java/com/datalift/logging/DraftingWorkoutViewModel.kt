package com.datalift.logging

import androidx.lifecycle.ViewModel
import com.datalift.model.data.MuscleGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DraftingWorkoutViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutDraftUiState())
    val uiState: StateFlow<WorkoutDraftUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String){
        _uiState.value = _uiState.value.copy(
            title = title
        )
    }

    fun updateMuscleGroups(muscleGroup: MuscleGroup, addToUiState: Boolean){
        if(addToUiState){
            if (!_uiState.value.muscleGroups.contains(muscleGroup)){
                _uiState.update { currentState ->
                    val newMuscleGroups = currentState.muscleGroups + muscleGroup
                    currentState.copy(
                        muscleGroups = newMuscleGroups
                    )
                }
            }
        } else {
            if(_uiState.value.muscleGroups.contains(muscleGroup)){
                _uiState.update { currentState ->
                    val newMuscleGroups = currentState.muscleGroups.filter { it != muscleGroup }
                    currentState.copy(
                        muscleGroups = newMuscleGroups
                    )
                }
            }
        }
    }

    fun saveWorkout(){
        //TODO: Save workout to database
    }
}

data class WorkoutDraftUiState(
    val title: String = "",
    val muscleGroups: List<MuscleGroup> = emptyList(),
    val exercises: List<DraftExercise> = emptyList()
)

data object DraftExercise