package com.example.test_movie_app.presentation.viewModels.stateHolders

import com.invia.domain.model.ShowsResponseItem

data class MovieStateHolder(
    val isLoading:Boolean = false,
    val data: List<ShowsResponseItem>? = null,
    val error: String = ""
)