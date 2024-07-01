package com.invia.domain.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.invia.domain.datasource.database.entities.Label
import com.invia.domain.datasource.database.entities.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(label: Movie)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movie>)

    @Transaction
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<Movie>>

    @Transaction
    @Query("SELECT * FROM movies where id = (:id)")
    fun getMovieById(id: Int): Flow<List<Movie>>

    @Transaction
    @Query("SELECT * FROM movies where name LIKE (:term)")
    fun getMovieBySearch(term: String): Flow<List<Movie>>

    @Transaction
    @Query("SELECT * FROM movies WHERE name LIKE '%' || :term || '%'")
    fun getMovieBySearchTerm(term: String): Flow<List<Movie>>

}
