package com.invia.data.datasource.database

import com.invia.domain.datasource.database.dao.MovieDAO
import com.invia.domain.datasource.database.datasource.MoviesDataSourceLocal
import com.invia.domain.datasource.database.entities.Movie
import javax.inject.Inject

class MoviesDataSourceLocalImpl @Inject constructor( override var dao: MovieDAO) : MoviesDataSourceLocal {
    override suspend fun insert(label: Movie) = dao.insert(label)

    override suspend fun insert(movies: List<Movie>) = dao.insert(movies)

    override fun getAllMovies() = dao.getAllMovies()

    override fun getMovieById(id: Int) = dao.getMovieById(id)

    override fun getMovieBySearch(term: String) = dao.getMovieBySearch(term)

    override fun getMovieBySearchTerm(term: String) = dao.getMovieBySearchTerm(term)

}