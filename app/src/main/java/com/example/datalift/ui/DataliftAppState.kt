package com.example.datalift.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.datalift.data.repository.UserRepository
import com.datalift.database.service.AccountService
import com.datalift.feed.navigation.navigateToFeed
import com.datalift.logging.navigation.navigateToWorkoutLog
import com.example.datalift.navigation.TopLevelDestinations
import com.example.datalift.navigation.TopLevelDestinations.FEED
import com.example.datalift.navigation.TopLevelDestinations.WORKOUTS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberDataliftAppState(
    userRepository: UserRepository,
    accountService: AccountService,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): DataliftAppState {
    return remember(
        navController,
    ) {
        DataliftAppState(
            navController = navController,
            corountineScope = coroutineScope,
            userRepository = userRepository,
            accountService = accountService
        )
    }
}


class DataliftAppState(
    val navController: NavHostController,
    accountService: AccountService,
    corountineScope: CoroutineScope,
    userRepository: UserRepository
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val isLoggedIn: StateFlow<Boolean> = userRepository.isLoggedIn.stateIn(
        scope = corountineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = accountService.loggedIn
    )

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            return currentEntry.value?.destination.also { destination ->
                if(destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestinations: TopLevelDestinations?
        @Composable get() {
            return TopLevelDestinations.entries.firstOrNull{ topLevelDestinations ->  
                currentDestination?.hasRoute(route = topLevelDestinations.route) == true
            }
        }

    val topLevelDestinations: List<TopLevelDestinations> = TopLevelDestinations.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestinations){
        trace("Navigation: ${topLevelDestination.name}"){
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id){
                    saveState = true
                }
                launchSingleTop = true
                restoreState= true
            }

            when(topLevelDestination){
                FEED -> navController.navigateToFeed(topLevelNavOptions)
                WORKOUTS -> navController.navigateToWorkoutLog(topLevelNavOptions)
//                ANALYSIS -> navController.navigateToAnalysis(topLevelNavOptions)
//                CHALLENGES -> navController.navigateToChallengesFeed(topLevelNavOptions)
            }
        }
    }
}