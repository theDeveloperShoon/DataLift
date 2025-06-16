package com.datalift.logging.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.datalift.logging.LoggedScreen
import kotlinx.serialization.Serializable

@Serializable data object LogRoute
@Serializable data object LoggingBaseRoute

fun NavGraphBuilder.loggingGraph(
    onWorkoutClick: (String) -> Unit,
    onAddWorkoutClick: () -> Unit,
    navUp: () -> Unit
) {
    navigation<LoggingBaseRoute>(startDestination = LogRoute){
        composable<LogRoute>{
            LoggedScreen(
                navigateToWorkout = onWorkoutClick,
            )
        }
    }

}