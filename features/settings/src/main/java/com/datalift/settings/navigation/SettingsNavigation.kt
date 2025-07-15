package com.datalift.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.datalift.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable data object SettingsBaseRoute
@Serializable data object SettingsRoute

fun NavController.navigateToSettings() = navigate(route = SettingsRoute)

fun NavGraphBuilder.settingsGraph(
    navigateToLogin: () -> Unit,
    navUp: () -> Unit
){
    navigation<SettingsBaseRoute>(startDestination = SettingsRoute) {
        composable<SettingsRoute> {
            SettingsScreen(
                navigateToLogin = navigateToLogin,
                navUp = navUp,
            )
        }
    }
}