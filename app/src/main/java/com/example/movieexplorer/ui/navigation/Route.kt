package com.example.movieexplorer.ui.navigation

import com.example.movieexplorer.utils.Urls

// Define the routes for the app (route is the base path, routeWithArgs is the path with arguments which is using because of the navigation component)
sealed class Route {
    // Base route for the app
    data class HomeScreen( val route: String = "homeScreen") : Route()
    // Route for the detail screen, which takes a movie ID as an argument
    data class FilmScreen(
        val route: String = "FilmScreen",
        val routeWithArgs: String = "$route/{${Urls.MOVIE_ID}}",
    ) : Route() {
        fun getRouteWithArgs(id: Int) : String { // Function to get the route with the movie ID
            return "$route/$id"
        }
    }
}