package com.invia.domain.datasource.database.datasource

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.invia.domain.common.Result
import com.invia.domain.datasource.database.dao.LabelDAO
import com.invia.domain.datasource.database.dao.MovieDAO
import com.invia.domain.datasource.database.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesDataSourceLocal {
    var dao: MovieDAO

    suspend fun insert(label: Movie)

    suspend fun insert(movies: List<Movie>)

    fun getAllMovies(): Flow<List<Movie>>

    fun getMovieById(id: Int): Flow<List<Movie>>

    fun getMovieBySearch(term: String): Flow<List<Movie>>

    fun getMovieBySearchTerm(term: String): Flow<List<Movie>>
}