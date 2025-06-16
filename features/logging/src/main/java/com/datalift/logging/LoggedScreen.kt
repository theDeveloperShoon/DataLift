package com.datalift.logging

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.datalift.designsystem.components.DevicePreviews
import com.datalift.model.data.Workout
import com.datalift.ui.WorkoutFeedUiState
import com.datalift.ui.WorkoutPreviewParameterProvider
import com.datalift.ui.workoutFeed

@Composable
internal fun LoggedScreen(
    navigateToWorkout: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutLogViewModel = hiltViewModel()
){
    val uiState by viewModel.workoutFeedState.collectAsStateWithLifecycle()
    val isRefreshing = viewModel.isRefreshing

    LoggedScreen(
        uiState = uiState,
        isRefreshing = isRefreshing,
        onRefresh = viewModel::onRefresh,
        onWorkoutClick = navigateToWorkout,
        onWorkoutDelete = viewModel::deleteWorkout,
        modifier = modifier
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoggedScreen(
    uiState: WorkoutFeedUiState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onWorkoutClick: (String) -> Unit,
    onWorkoutDelete: (String) -> Unit,
    modifier: Modifier = Modifier
){
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        val state = rememberLazyListState()

        LazyColumn(
            state = state,
            modifier = Modifier.fillMaxSize()
        ) {
            workoutFeed(
                feedState = uiState,
                onWorkoutClick = onWorkoutClick,
                onWorkoutDelete = onWorkoutDelete,
            )
        }
    }
}

@DevicePreviews
@Composable
private fun LoggedScreenLoading(){
    LoggedScreen(
        uiState = WorkoutFeedUiState.Loading,
        isRefreshing = false,
        onRefresh = {},
        onWorkoutClick = {},
        onWorkoutDelete = {}
    )
}

@DevicePreviews
@Composable
private fun LoggedScreenPopulatedRefreshing(
    @PreviewParameter(WorkoutPreviewParameterProvider::class)
    workoutList: List<Workout>
){
    LoggedScreen(
        uiState = WorkoutFeedUiState.Success(workoutList),
        isRefreshing = true,
        onRefresh = {},
        onWorkoutClick = {},
        onWorkoutDelete = {}
    )
}

@DevicePreviews
@Composable
private fun LoggedScreenPopulated(
    @PreviewParameter(WorkoutPreviewParameterProvider::class)
    workoutList: List<Workout>
){
    LoggedScreen(
        uiState = WorkoutFeedUiState.Success(workoutList),
        isRefreshing = false,
        onRefresh = {},
        onWorkoutClick = {},
        onWorkoutDelete = {}
    )
}