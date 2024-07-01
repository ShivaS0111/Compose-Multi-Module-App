package com.example.test_movie_app.presentation.viewModels.stateHolders

import com.invia.domain.datasource.database.entities.Movie

data class MovieStateHolder(
    val isLoading:Boolean = false,
    val data: List<Movie>? = null,
    val error: String = ""
)