package com.datalift.logging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datalift.data.repository.WorkoutRepository
import com.datalift.logging.models.ExerciseDraft
import com.datalift.logging.models.toExercise
import com.datalift.model.data.MuscleGroup
import com.datalift.model.data.WorkoutDraft
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DraftingWorkoutViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
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

    fun addExercise(exercise: ExerciseDraft){
        _uiState.update { currentState ->
            val newExercises = currentState.exercises + exercise
            currentState.copy(
                exercises = newExercises
            )
        }
    }

    fun saveWorkout(){
        if(_uiState.value.isSaving) return
        if(_uiState.value.title.isEmpty()){
            _uiState.value = _uiState.value.copy(
                titleError = true
            )
            return
        }
        if(_uiState.value.exercises.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                exercisesError = true
            )
            return
        }

        _uiState.value = _uiState.value.copy(
            isSaving = true
        )

        viewModelScope.launch {
            workoutRepository.saveWorkout(_uiState.value.convertToWorkoutDraft())

            _uiState.value = _uiState.value.copy(
                isSaving = false,
                isSaved = true
            )
        }
    }
}

data class WorkoutDraftUiState(
    val title: String = "",
    val muscleGroups: List<MuscleGroup> = emptyList(),
    val exercises: List<ExerciseDraft> = emptyList(),
    val titleError: Boolean = false,
    val exercisesError: Boolean = false,
    val isSaving: Boolean = false,
    val isSaved: Boolean? = null
){
    fun convertToWorkoutDraft(): WorkoutDraft =
        WorkoutDraft(
            workoutName = title,
            muscleGroups = muscleGroups.map { it.displayName },
            exercises = exercises.map { it.toExercise() }
        )
}