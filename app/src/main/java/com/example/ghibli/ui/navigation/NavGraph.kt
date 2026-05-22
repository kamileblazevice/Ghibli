package com.example.ghibli.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.ghibli.ui.features.detail.FilmDetailScreen
import com.example.ghibli.ui.features.list.FilmListScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Destination,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {

        navigation<Destination.AppGraph>(
            startDestination = Destination.FilmListScreen,
        ) {
            composable<Destination.FilmListScreen> {
                FilmListScreen()
            }

            composable<Destination.FilmDetailScreen> { backStackEntry ->
                val args = backStackEntry.toRoute<Destination.FilmDetailScreen>()
                FilmDetailScreen(filmId = args.id)
            }
        }
    }
}
