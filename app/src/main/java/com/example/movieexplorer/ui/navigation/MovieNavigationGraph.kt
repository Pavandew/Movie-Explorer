package com.example.movieexplorer.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.movieexplorer.ui.detail.MovieDetailScreen
import com.example.movieexplorer.ui.home.HomeScreen
import com.example.movieexplorer.utils.Urls

@Composable
fun MovieNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController  // Navigation controller for managing the navigation state

) {
    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen().route,
        modifier = modifier.fillMaxSize()
        ) {
        composable (
            route = Route.HomeScreen().route,
            enterTransition = { fadeIn() + scaleIn()},  // Animation when entering the home screen
            exitTransition = { fadeOut() + scaleOut() }  // Animation when exiting the home screen
        ) {
            HomeScreen (
                onMovieClick =  {
                    navController.navigate(
                        Route.FilmScreen().getRouteWithArgs(id = it)
                    ) {
                        launchSingleTop = true  // this is used to avoid multiple instances of the same screen in the back stack
                        // This will pop up to the start destination of the graph when navigating to the detail screen
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = false}  // false means we click back button, we will not pop the home screen
                    }
                }
            )
        }


        composable(
            route = Route.FilmScreen().routeWithArgs,
            arguments = listOf(navArgument(name = Urls.MOVIE_ID) { type = NavType.IntType })
        ) {
            MovieDetailScreen(
                onNavigateUp = { navController.navigateUp() },
                // when the user clicks on a movie in the detail screen, navigate to the another detail screen
                onMovieClick = {
                    navController.navigate(
                        Route.FilmScreen().getRouteWithArgs(id = it)
                    ) {
                        launchSingleTop = true  // this is used to avoid multiple instances of the same screen in the back stack
                        // This will pop up to the start destination of the graph when navigating to the detail screen
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = false}  // false means we click back button, we will not pop the home screen
                    }
                },
                onActorClick = {}
            )

        }
    }
}
