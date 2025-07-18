package com.datalift.logging.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.datalift.logging.AddExerciseScreen
import com.datalift.logging.AddExerciseViewModel
import com.datalift.logging.CreateWorkoutScreen
import com.datalift.logging.DraftingWorkoutViewModel
import com.datalift.logging.ExerciseSearchScreen
import com.datalift.logging.ExerciseSearchViewModel
import com.datalift.logging.LoggedScreen
import com.datalift.logging.WorkoutViewScreen
import com.datalift.logging.models.ExerciseDraft
import kotlinx.serialization.Serializable

@Serializable data object LogRoute
@Serializable data object CreateWorkoutRoute
@Serializable data object ExerciseSearchRoute
@Serializable data object LoggingBaseRoute
@Serializable data class WorkoutViewRoute(val workoutId: String)
@Serializable
data class ExerciseLoggingRoute(
    val name: String,
    val bodyPart: String
)

const val ADDED_EXERCISE_RESULT_KEY = "addedExerciseResult"

fun NavController.navigateToWorkoutLog(navOptions: NavOptions) =
    navigate(route = LogRoute, navOptions)

fun NavController.navigateToWorkout(workoutId: String) =
    navigate(route = WorkoutViewRoute(workoutId))

fun NavController.navigateToCreateWorkout() = navigate(route = CreateWorkoutRoute)

fun NavController.navigateToExerciseSearch() =
    navigate(route = ExerciseSearchRoute)

fun NavController.navigateToExerciseLogging(name: String, bodyPart: String) =
    navigate(
        route = ExerciseLoggingRoute(
            name = name,
            bodyPart = bodyPart
        )
    )

fun NavController.popBackToAddWorkout() =
    popBackStack(route = CreateWorkoutRoute, inclusive = false)

fun NavGraphBuilder.loggingGraph(
    onWorkoutClick: (String) -> Unit,
    onAddWorkoutClick: () -> Unit,
    navigateToExerciseSearch: () -> Unit,
    navigateToExerciseLogging: (String, String) -> Unit,
    popBackToAddWorkout: () -> Unit,
    navUp: () -> Unit,
    getBackStackEntry: (Any) -> NavBackStackEntry
) {
    navigation<LoggingBaseRoute>(startDestination = LogRoute){
        composable<LogRoute>{
            LoggedScreen(
                navigateToWorkout = onWorkoutClick,
                navigateToCreateWorkout = onAddWorkoutClick,
            )
        }

        composable<WorkoutViewRoute>{
            WorkoutViewScreen(
                navUp = navUp
            )
        }

        //TODO: Add EditWorkoutScreen



        composable<CreateWorkoutRoute>{ backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                getBackStackEntry(LoggingBaseRoute)
            }

            val savedStateHandle = parentEntry.savedStateHandle

            val draftingWorkoutViewModel: DraftingWorkoutViewModel = hiltViewModel(parentEntry)

            LaunchedEffect(savedStateHandle) {
                savedStateHandle
                    .getStateFlow<ExerciseDraft?>(ADDED_EXERCISE_RESULT_KEY, null)
                    .collect { exercise ->
                        if(exercise != null){
                            draftingWorkoutViewModel.addExercise(exercise)
                            savedStateHandle[ADDED_EXERCISE_RESULT_KEY] = null
                        }
                    }

            }

            CreateWorkoutScreen(
                navUp = navUp,
                navigateToExerciseSearch = navigateToExerciseSearch,
                draftingWorkoutViewModel = draftingWorkoutViewModel
            )
        }

        // TODO: Add EditExerciseScreen

        composable<ExerciseSearchRoute>{backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                getBackStackEntry(LoggingBaseRoute)
            }

            val searchViewModel: ExerciseSearchViewModel = hiltViewModel(parentEntry)

            ExerciseSearchScreen(
                navUp = navUp,
                onSaveExercise = { exercise ->
                    Log.d("Logging Graph", "Saving exercise: $exercise")
                    navigateToExerciseLogging(
                        exercise.title,
                        exercise.bodyPart,
                    )
                },
                exerciseViewModel = searchViewModel
            )
        }

        composable<ExerciseLoggingRoute>{backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                getBackStackEntry(LoggingBaseRoute)
            }

            val savedStateHandle = parentEntry.savedStateHandle

            val addExerciseViewModel: AddExerciseViewModel = hiltViewModel()

            AddExerciseScreen(
                navBack = navUp,
                saveExercise = { exerciseDraft ->
                    savedStateHandle[ADDED_EXERCISE_RESULT_KEY] = exerciseDraft
                    popBackToAddWorkout()
                },
                viewModel = addExerciseViewModel
            )
        }
    }

}