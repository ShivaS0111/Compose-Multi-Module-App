package com.invia.domain.datasource.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.invia.domain.datasource.database.entities.Label
import com.invia.domain.datasource.database.entities.Note
import com.invia.domain.datasource.database.entities.relations.LabelWithNotes
import com.invia.domain.datasource.database.entities.relations.NoteWithLabels
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDAO {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note):Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: List<Note>):List<Long>

    @Transaction
    @Delete
    suspend fun delete(labels: List<Note>)

    @Transaction
    @Query("delete FROM notes")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM notes order by dateTime")
     fun getAllNotes(): Flow<List<Note>>

    @Transaction
    @Query("SELECT * FROM notes where noteId = (:id) order by dateTime")
     fun getNoteById(id: Long): Flow<List<Note>>

    @Transaction
    @Query("SELECT * FROM notes where noteId LIKE (:term) order by dateTime")
     fun getNotesBySearch(term: String): Flow<List<Note>>

    @Transaction
    @Query("SELECT * FROM notes WHERE note LIKE '%' || :term || '%' order by dateTime")
     fun getNotesBySearchTerm(term: String): Flow<List<Note>>

    @Transaction
    @Query("SELECT * FROM notes")
    fun getNotes(): List<NoteWithLabels>

    @Transaction
    @Query("SELECT * FROM labels")
    fun getSongsWithPlaylists(): List<LabelWithNotes>

}



