package com.invia.domain.datasource.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.invia.domain.datasource.database.entities.Label
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDAO {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(label: Label):Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(labels: List<Label>):List<Long>

    @Transaction
    @Delete
    suspend fun delete(labels: List<Label>)

    @Transaction
    @Query("delete FROM labels")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM labels")
    fun getAllLabels(): Flow<List<Label>>

    @Transaction
    @Query("SELECT * FROM labels where labelId = (:id)")
    fun getLabelById(id: Long): Flow<List<Label>>

    @Transaction
    @Query("SELECT * FROM labels where label LIKE (:term)")
    fun getLabelBySearch(term: String): Flow<List<Label>>

    @Transaction
    @Query("SELECT * FROM labels WHERE label LIKE '%' || :term || '%'")
    fun getLabelBySearchTerm(term: String): Flow<List<Label>>

}


/*
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insert(fruittie: Fruittie)

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insert(fruggie: List<Fruittie>)

@Query("SELECT * FROM Fruittie")
fun getAllAsFlow(): Flow<List<Fruittie>>

@Query("SELECT COUNT(*) as count FROM Fruittie")
suspend fun count(): Int

@Query("SELECT * FROM Fruittie WHERE id in (:ids)")
suspend fun loadAll(ids: List<Long>): List<Fruittie>

@Query("SELECT * FROM Fruittie WHERE id in (:ids)")
suspend fun loadMapped(ids: List<Long>):
        Map<@MapColumn(columnName = "id") Long, Fruittie, >
        */

