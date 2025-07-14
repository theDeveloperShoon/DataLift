package com.example.datalift.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.datalift.feed.navigation.feedGraph
import com.datalift.feed.navigation.navigateToFeed
import com.datalift.feed.navigation.navigateToPost
import com.datalift.logging.navigation.loggingGraph
import com.datalift.logging.navigation.navigateToCreateWorkout
import com.datalift.logging.navigation.navigateToExerciseLogging
import com.datalift.logging.navigation.navigateToExerciseSearch
import com.datalift.logging.navigation.navigateToWorkout
import com.datalift.logging.navigation.popBackToAddWorkout
import com.example.datalift.ui.DataliftAppState

@Composable
fun DataliftNavHost(
    appState: DataliftAppState,
    modifier: Modifier = Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    userLoggedIn: Boolean = false,
    loginUser: () -> Unit,
    logoutUser: () -> Unit,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = if(userLoggedIn) FeedBaseRoute else LoginRoute,
        modifier = modifier
    ){
        loginScreen(
            navController = navController,
            onShowSnackbar = onShowSnackbar,
            loginUser = loginUser

        feedGraph(
            onPostClick = navController::navigateToPost,
            navUp = navController::navigateUp,
        )

        loggingGraph(
            onWorkoutClick = navController::navigateToWorkout,
            onAddWorkoutClick = navController::navigateToCreateWorkout,
            navigateToExerciseSearch = navController::navigateToExerciseSearch,
            navigateToExerciseLogging = navController::navigateToExerciseLogging,
            popBackToAddWorkout = navController::popBackToAddWorkout,
            navUp = navController::navigateUp,
            getBackStackEntry = navController::getBackStackEntry
        )

        signUpGraph(
            navController = navController
        )

        workoutGraph(
            navController = navController
        )

        feedSection(
            navController = navController
        )

        analysisScreen()

        settingsSection(
            navController = navController,
            logoutUser = logoutUser
        )

        friendsRoute(
            navUp = navController::navigateUp,
            navigationToProfile = navController::navigateToProfile
        )

        profileRoute(
            navUp = navController::navigateUp
        )

        challengesRoute(
            navUp = navController::navigateUp,
            getBackStackEntry = navController::getBackStackEntry,
            navigateToChallengeFeed = navController::navigateToChallengesFeed,
            navigateToChallenge = navController::navigateToChallenge,
            navigateToChallengeCreation = navController::navigateToChallengeCreation,
            navigationToProfile = navController::navigateToProfile
        )
    }
}