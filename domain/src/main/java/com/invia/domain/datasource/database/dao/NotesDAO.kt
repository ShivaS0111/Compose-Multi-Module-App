package com.invia.domain.datasource.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note):Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: List<Note>):List<Long>

    @Transaction
    @Delete
    suspend fun delete(labels: List<Note>)

    @Transaction
    @Query("delete FROM notes")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM notes order by dateTime")
     fun getNotes1(): Flow<List<Note>>

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

    @Transaction
    @Query("select  n.*, l.*  from notes n, labels l, labelled_notes ln where  n.noteId = ln.noteId and l.labelId = ln.labelId")
    fun getNotesList(): List<NoteWithLabels>

    @Transaction
    @Query("SELECT n.*, l.* FROM notes n LEFT JOIN labelled_notes ln ON n.noteId = ln.noteId LEFT JOIN labels l ON ln.labelId = l.labelId")
    fun getAllNotes(): Flow<List<NoteWithLabels>>
    @Transaction
    @Query("SELECT  n.*, l.*  FROM notes n LEFT JOIN labelled_notes ln ON n.noteId = ln.noteId LEFT JOIN labels l ON ln.labelId = l.labelId")
    fun getRefreshNotes(): List<NoteWithLabels>
    @Transaction
    @Query("SELECT  n.*, l.*  FROM notes n LEFT JOIN labelled_notes ln ON n.noteId = ln.noteId LEFT JOIN labels l ON ln.labelId = l.labelId where n.noteId = (:noteId)" )
    fun getNotesById(noteId:Long): NoteWithLabels?
    @Transaction
    @Query("select  n.*, l.*  from notes n, labels l, labelled_notes ln where  n.noteId = ln.noteId and l.labelId = ln.labelId")
    fun getAllNotes1(): Flow<List<NoteWithLabels>>

    @Transaction
    @Query("select  n.*, l.*  from notes n, labels l, labelled_notes ln where  n.noteId = ln.noteId and l.labelId = ln.labelId")
    fun getNotesQry(): Flow<List<NoteWithLabels>>

    @Transaction
    @Query("select  n.*, l.*  from notes n, labels l, labelled_notes ln where  n.noteId = ln.noteId and l.labelId = ln.labelId")
    fun getLabelsQry(): Flow<List<LabelWithNotes>>


    @Transaction
    @Query("select  n.*, l.*  from notes n, labels l, labelled_notes ln " +
            "where n.noteId = ln.noteId " +
            "and l.labelId = ln.labelId " +
            "and  ln.noteId = (:noteId)"
    )
    fun getNotesByIdQry(noteId:Long): Flow<List<NoteWithLabels>>

    @Transaction
    @Query("select  n.*, l.*  from notes n, labels l, labelled_notes ln " +
            "where n.noteId = ln.noteId " +
            "and l.labelId = ln.labelId " +
            "and  ln.labelId = (:labelId)"
    )
    fun getLabelsByIdQry(labelId:Long): Flow<List<NoteWithLabels>>

//noteId LIKE (:term)
    @Transaction
    @Query("select  n.*, l.*  from notes n, labels l, labelled_notes ln " +
            "where  n.noteId = ln.noteId " +
                "and l.labelId = ln.labelId "+
                "and n.note LIKE '%' || :term || '%'"
    )
    fun getNotesBySearchQry(term: String): Flow<List<NoteWithLabels>>

    //WHERE note LIKE '%' || :term || '%'
    @Transaction
    @Query("select  n.*, l.*  from notes n, labels l, labelled_notes ln " +
            "where  n.noteId = ln.noteId " +
            "and l.labelId = ln.labelId "+
            "and n.note LIKE '%' || :term || '%'"
    )
    fun getNotesBySearchTermQry(term: String): Flow<List<NoteWithLabels>>

}



