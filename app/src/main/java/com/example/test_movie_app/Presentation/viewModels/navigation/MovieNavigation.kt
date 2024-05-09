package com.example.test_movie_app.Presentation.viewModels.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.test_movie_app.Presentation.viewModels.screens.MovieListScreen

@Composable
fun MovieNavigation(navHostController:NavHostController){
    NavHost(navController = navHostController, startDestination = "movie_list" ){
        composable("movie_list"){
            MovieListScreen(navHostController = navHostController)
        }
    }

}