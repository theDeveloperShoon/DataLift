package com.datalift.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.datalift.login.LoginScreen
import com.datalift.login.SignupScreen
import kotlinx.serialization.Serializable

@Serializable data object LoginBaseRoute
@Serializable data object LoginRoute
@Serializable data object ForgotPasswordRoute
@Serializable data object SignUpRoute

fun NavController.navigateToLogin() = navigate(route = LoginRoute)
fun NavController.navigateToSignUp() = navigate(route = SignUpRoute)

fun NavGraphBuilder.loginGraph(
    navigateToHome: () -> Unit,
    navigateToSignup : () -> Unit,
    navUp: () -> Unit
){
    navigation<LoginBaseRoute>(startDestination = LoginRoute){
        composable<LoginRoute>{
            LoginScreen(
                navigateToSignup = navigateToSignup,
                navigateToForgotPassword = {},
                navigateToHome = navigateToHome
            )
        }

        composable<SignUpRoute>{
            SignupScreen(
                navigateUp = navUp,
                navigateToHome = navigateToHome
            )
        }
    }
}