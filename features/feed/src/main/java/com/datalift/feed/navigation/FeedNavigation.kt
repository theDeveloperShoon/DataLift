package com.datalift.feed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.datalift.feed.FeedScreen
import com.datalift.feed.PostScreen
import kotlinx.serialization.Serializable

@Serializable data object FeedRoute
@Serializable data class PostRoute(val id: String)
@Serializable data object FeedBaseRoute

fun NavController.navigateToFeed(navOptions: NavOptions?) = navigate(route = FeedRoute, navOptions)
fun NavController.navigateToFeed(builder: NavOptionsBuilder.() -> Unit) = navigate(route = FeedRoute, builder)
fun NavController.navigateToPost(id: String) = navigate(route = PostRoute(id))

fun NavGraphBuilder.feedGraph(
    onPostClick: (String) -> Unit,
    navUp: () -> Unit
){
    navigation<FeedBaseRoute>(startDestination = FeedRoute){
        composable<FeedRoute> {
            FeedScreen(
                onPostClick = onPostClick
            )
        }
        composable<PostRoute> {
            PostScreen(
                navUp = navUp
            )
        }
    }
}