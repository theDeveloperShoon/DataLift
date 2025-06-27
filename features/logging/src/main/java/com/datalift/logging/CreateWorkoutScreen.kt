package com.datalift.logging

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.designsystem.theme.DataliftTheme
import com.datalift.logging.models.toExercise
import com.datalift.model.data.Exercise
import com.datalift.model.data.MuscleGroup
import com.datalift.model.data.Workout
import com.datalift.ui.WorkoutPreviewParameterProvider
import com.datalift.ui.exerciseItemList
import com.datalift.ui.muscleGroupChips

@Composable
internal fun CreateWorkoutScreen(
    navUp: () -> Unit,
    navigateToExerciseSearch: () -> Unit,
    draftingWorkoutViewModel: DraftingWorkoutViewModel = hiltViewModel()
){
    val uiState by draftingWorkoutViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved == true) {
            navUp()
        }
    }

    CreateWorkoutScreen(
        navUp = navUp,
        workoutName = uiState.title,
        addExercise = navigateToExerciseSearch,
        saveWorkout = draftingWorkoutViewModel::saveWorkout,
        changeWorkoutName = draftingWorkoutViewModel::updateTitle,
        selectMuscleGroup = draftingWorkoutViewModel::updateMuscleGroups,
        selectExercise = {}, // TODO: Make a screen based off of AddExerciseScreen and pass in the exercise
        exercises = uiState.exercises.map { it.toExercise() } // TODO: Change to use exercise
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateWorkoutScreen(
    navUp: () -> Unit,
    workoutName: String,
    addExercise: () -> Unit,
    saveWorkout: () -> Unit,
    changeWorkoutName: (String) -> Unit,
    selectMuscleGroup: (MuscleGroup, Boolean) -> Unit,
    selectExercise: (Exercise) -> Unit,
    exercises: List<Exercise>,
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Create Workout")
                },
                navigationIcon = {
                    IconButton(
                        onClick = navUp
                    ) {
                        Icon(
                            imageVector = DataliftIcons.Close,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = saveWorkout,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Save Workout")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            CreateWorkoutNameLabel()
            Spacer(modifier = Modifier.height(8.dp))
            CreateWorkoutNameBox(
                workoutName = workoutName,
                changeWorkoutName = changeWorkoutName,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            MuscleGroupChipsRowed(
                selectMuscleGroup = selectMuscleGroup
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExerciseLabel()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                exerciseItemList(
                    exercises = exercises,
                    selectExercise = selectExercise
                )
                item {
                    AddExerciseButton(
                        addExercise = addExercise,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun AddExerciseButton(
    addExercise: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = addExercise,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = modifier,
    ) {
        Text(
            text = "Add Exercise"
        )
    }
}


@Composable
fun CreateWorkoutNameLabel(){
    Text(
        text = "Workout Name",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CreateWorkoutNameBox(
    workoutName: String,
    changeWorkoutName: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        value = workoutName,
        onValueChange = changeWorkoutName,
        placeholder = { Text("Title your workout") },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        modifier = modifier
    )
}

@Composable
fun MuscleGroupChipsRowed(
    selectMuscleGroup: (MuscleGroup, Boolean) -> Unit
) {
    Column{
        MuscleGroupLabel()
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            muscleGroupChips(
                selectMuscleGroup = selectMuscleGroup
            )
        }
    }
}

@Composable
fun MuscleGroupLabel(){
    Text(
        text = "Muscle Groups",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ExerciseLabel(){
    Text(
        text = "Exercises",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
fun CreateWorkoutScreenPreview(
    @PreviewParameter(WorkoutPreviewParameterProvider::class)
    workouts: List<Workout>
) {
    DataliftTheme {
        CreateWorkoutScreen(
            workoutName = "Test Workout",
            changeWorkoutName = {},
            selectMuscleGroup = { _, _ -> },
            navUp = {},
            selectExercise = {},
            addExercise = {},
            saveWorkout = {},
            exercises = workouts[0].exercises
        )
    }
}