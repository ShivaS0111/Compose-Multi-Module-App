package com.example.test_movie_app.presentation.viewModels.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.test_movie_app.presentation.viewModels.screens.movies.movielist.MovieListScreen
import com.example.test_movie_app.presentation.viewModels.screens.notes.addnote.AddNoteScreen
import com.example.test_movie_app.presentation.viewModels.screens.notes.labels.addLabel.AddLabelScreen
import com.example.test_movie_app.presentation.viewModels.screens.notes.labels.list.LabelScreen
import com.example.test_movie_app.presentation.viewModels.screens.notes.list.NotesScreen

class Route {
    companion object {

        const val MovieList = "movie-list"

        const val Notes = "notes"
        const val AddNote = "add-note"
        const val Labels = "labels"
        const val LablesSelected = "labels/{selected}"
        const val EditNote = "edit-note"
        const val EditNoteById = "$EditNote/{noteId}"
        const val AddLabel = "add-label"


        const val startDestination = Notes
    }
}

@Composable
fun NavigationComponent(navHostController: NavHostController, resultViewModel: ResultViewModel) {
    NavHost(navController = navHostController, startDestination = Route.Notes) {
        composable(Route.MovieList) {
            MovieListScreen(navHostController = navHostController)
        }
        composable(Route.Notes) {
            NotesScreen(navHostController = navHostController, resultViewModel)
        }
        composable(Route.AddNote) {
            AddNoteScreen(navHostController = navHostController, resultViewModel)
        }
        composable(Route.EditNoteById,
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLong()
            AddNoteScreen(navHostController = navHostController, resultViewModel, noteId=noteId)
        }

        composable(Route.Labels) {
            LabelScreen(navHostController = navHostController, resultViewModel)
        }
        composable(
            Route.LablesSelected,
            arguments = listOf(navArgument("selected") { type = NavType.StringType })
        ) { backStackEntry ->
            val selected = backStackEntry.arguments?.getString("selected")
            val selectedList = selected?.split(",")?.map { it.toLong()  }?.toList()
            println("==>Navigation: $selectedList")
            LabelScreen(
                navHostController = navHostController,
                resultViewModel,
                selected = selectedList
            )
        }

        composable(Route.AddLabel) {
            AddLabelScreen(navHostController = navHostController)
        }
    }

}