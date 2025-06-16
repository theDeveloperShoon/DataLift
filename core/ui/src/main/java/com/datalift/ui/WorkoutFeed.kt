package com.datalift.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.datalift.designsystem.components.DataliftLoadingIcon
import com.datalift.designsystem.theme.DataliftTheme
import com.datalift.model.data.Workout

fun LazyListScope.workoutFeed(
    feedState: WorkoutFeedUiState,
    onWorkoutClick: (String) -> Unit,
    onWorkoutDelete: (String) -> Unit,
){
    when (feedState) {
        WorkoutFeedUiState.Error -> Unit // TODO: Change to provide an error
        WorkoutFeedUiState.Loading -> {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DataliftLoadingIcon(
                        contentDesc = "Loading Workouts",
                    )
                }
            }
        }
        is WorkoutFeedUiState.Success -> {
            items(feedState.workouts) { workout ->
                WorkoutEntry(
                    workout = workout,
                    onWorkoutClick = { onWorkoutClick(workout.workoutId) },
                    onWorkoutDelete = { onWorkoutDelete(workout.workoutId) }
                )
            }
        }
    }
}

sealed interface WorkoutFeedUiState {
    data object Loading : WorkoutFeedUiState
    data class Success(val workouts: List<Workout>) : WorkoutFeedUiState
    data object Error : WorkoutFeedUiState
}

@Preview
@Composable
private fun WorkoutFeedLoadingPreview(){
    DataliftTheme {
        LazyColumn {
            workoutFeed (
                feedState = WorkoutFeedUiState.Loading,
                onWorkoutClick = {},
                onWorkoutDelete = {}
            )
        }
    }
}

@Preview
@Composable
private fun WorkoutFeedContentPreview(
    @PreviewParameter(WorkoutPreviewParameterProvider::class)
    workouts: List<Workout>
){
    CompositionLocalProvider(
        LocalInspectionMode provides true
    ) {
        DataliftTheme {
            LazyColumn {
                workoutFeed(
                    feedState = WorkoutFeedUiState.Success(workouts),
                    onWorkoutClick = {},
                    onWorkoutDelete = {}
                )
            }
        }
    }
}