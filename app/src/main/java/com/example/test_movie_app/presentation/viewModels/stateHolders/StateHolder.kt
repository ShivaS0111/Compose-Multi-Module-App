package com.example.test_movie_app.presentation.viewModels.stateHolders

import com.invia.domain.datasource.database.entities.Movie

data class StateHolder<T>(
    val isLoading:Boolean = false,
    val data: List<T>? = null,
    val error: String = ""
)