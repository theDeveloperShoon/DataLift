package com.datalift.logging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.datalift.designsystem.components.DataliftLoadingIcon
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.designsystem.theme.DataliftTheme
import com.datalift.model.data.Exercise
import com.datalift.model.data.Workout
import com.datalift.ui.LocalTimeZone
import com.datalift.ui.WorkoutPreviewParameterProvider
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun WorkoutViewScreen(
    navUp: () -> Unit,
    workoutViewModel: WorkoutDetailViewModel = hiltViewModel()
){
    val uiState by workoutViewModel.uiState.collectAsStateWithLifecycle()
    WorkoutViewScreen(
        navUp = navUp,
        uiState =uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WorkoutViewScreen(
    navUp: () -> Unit,
    uiState: WorkoutDetailUiState
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Workout Details")
                },
                navigationIcon = {
                    IconButton (
                        onClick = navUp
                    ){
                        Icon(
                            imageVector = DataliftIcons.NavigateUp,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when(uiState) {
                WorkoutDetailUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ){
                        Text(
                            text = "Error has occurred loading the page",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                WorkoutDetailUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        DataliftLoadingIcon(
                            contentDesc = ""
                        )
                    }
                }
                is WorkoutDetailUiState.Success -> {
                    WorkoutTitle(title = uiState.workout.workoutName)
                    Spacer(modifier = Modifier.height(8.dp))
                    WorkoutDate(instant = uiState.workout.date)
                    Spacer(modifier = Modifier.height(8.dp))
                    ExerciseLabel()
                    ExerciseList(uiState.workout.exercises)
                }
            }
        }
    }
}

@Composable
fun WorkoutTitle(
    title: String
){
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun WorkoutDate(
    instant: Instant
){
    val formattedDate = dateFormatted(instant)
    Text(
        text = formattedDate
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

@Composable
fun ExerciseList(
    list: List<Exercise>
){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        items(list) { exercise ->
            ExerciseEntry(
                exercise = exercise,
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }

}

@Composable
fun ExerciseEntry(
    exercise: Exercise,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
    ) {
        ExerciseName(exercise.name)
        ExerciseRepsAndSets(
            reps = exercise.getRepsCount(),
            sets = exercise.getSetsCount()
        )
    }
}

@Composable
private fun ExerciseName(name: String){
    Text(
        text = name,
        style = MaterialTheme.typography.labelLarge
    )
}

@Composable
private fun ExerciseRepsAndSets(
    reps: Long,
    sets: Int
){
    Text(
        text = "$reps reps in $sets sets",
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
private fun dateFormatted(date: Instant): String = DateTimeFormatter
    .ofPattern("MMMM dd, yyyy 'at' HH:mm")
    .withLocale(Locale.getDefault())
    .withZone(LocalTimeZone.current.toJavaZoneId())
    .format(date.toJavaInstant())


@Preview
@Composable
private fun WorkoutViewScreenPreviewSuccess(
    @PreviewParameter(WorkoutPreviewParameterProvider::class)
    workouts: List<Workout>
){
    DataliftTheme {
        WorkoutViewScreen(
            navUp = {},
            uiState = WorkoutDetailUiState.Success(workouts[0])
        )
    }
}

@Preview
@Composable
private fun WorkoutViewScreenPreviewError(){
    DataliftTheme {
        WorkoutViewScreen(
            navUp = {},
            uiState = WorkoutDetailUiState.Error
        )
    }
}

@Preview
@Composable
private fun WorkoutViewScreenPreviewLoading(){
    DataliftTheme {
        WorkoutViewScreen(
            navUp = {},
            uiState = WorkoutDetailUiState.Loading
        )
    }
}