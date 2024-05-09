package com.example.test_movie_app.Presentation.viewModels.stateHolders

import com.invia.domain.datasource.ShowsResponse

data class MovieStateHolder(
    val isLoading:Boolean = false,
    val data: ShowsResponse? = null,
    val error: String = ""
)