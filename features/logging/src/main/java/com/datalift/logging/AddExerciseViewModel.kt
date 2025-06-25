package com.datalift.logging

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.datalift.logging.models.ExerciseDraft
import com.datalift.logging.models.ExerciseSetDraft
import com.datalift.logging.navigation.ExerciseLoggingRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddExerciseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val exercise = savedStateHandle.toRoute<ExerciseLoggingRoute>()

    private val _uiState = MutableStateFlow(
        AddExerciseUiState(
            exerciseName = exercise.name,
            exerciseBodyPart = exercise.bodyPart
        )
    )
    val uiState: StateFlow<AddExerciseUiState> = _uiState

    fun updateSetWeight(setID: String, weight: Int){
        _uiState.update { currentState ->
            val updatedSets = currentState.sets.map {
                if(it.id == setID){
                    it.copy(weightWhole = weight)
                } else {
                    it
                }
            }
            currentState.copy(sets = updatedSets)
        }
    }

    fun updateSetWeightDecimal(setID: String, weightDecimal: Int){
        _uiState.update { currentState ->
            val updatedSets = currentState.sets.map {
                if (it.id == setID) {
                    it.copy(weightDecimal = weightDecimal)
                } else {
                    it
                }
            }
            currentState.copy(sets = updatedSets)
        }
    }

    fun addSet(){
        _uiState.update { currentState ->
            val newSet = ExerciseSetDraft(
                reps = 0,
                weightWhole = 0,
                weightDecimal = 0
            )
            currentState.copy(sets = currentState.sets + newSet)
        }
    }

    fun removeSet(setID: String) {
        _uiState.update { currentState ->
            val updatedSets = currentState.sets.filter { it.id != setID }
            currentState.copy(sets = updatedSets)
        }
    }
}

data class AddExerciseUiState(
    val exerciseName: String = "",
    val exerciseBodyPart: String = "",
    val sets: List<ExerciseSetDraft> = emptyList()
){
    fun convertToExerciseDraft() : ExerciseDraft =
        ExerciseDraft(
            name = exerciseName,
            bodyPart = exerciseBodyPart,
            sets = sets.map { it.toParcelableExerciseSet() }
        )
}