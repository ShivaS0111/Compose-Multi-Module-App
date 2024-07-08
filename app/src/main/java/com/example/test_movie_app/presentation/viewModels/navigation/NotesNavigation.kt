package com.example.test_movie_app.presentation.viewModels.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.test_movie_app.presentation.viewModels.screens.MovieListScreen
import com.example.test_movie_app.presentation.viewModels.screens.NotesScreen

@Composable
fun NotesNavigation(navHostController:NavHostController){
    NavHost(navController = navHostController, startDestination = "notes" ){
        composable("notes"){
            NotesScreen(navHostController = navHostController)
        }
    }

}