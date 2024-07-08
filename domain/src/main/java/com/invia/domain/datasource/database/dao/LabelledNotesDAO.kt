package com.invia.domain.datasource.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.invia.domain.datasource.database.entities.LabelledNotes
import com.invia.domain.datasource.database.entities.relations.LabelWithNotes
import com.invia.domain.datasource.database.entities.relations.NoteWithLabels
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelledNotesDAO {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: LabelledNotes):Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: List<LabelledNotes>):List<Long>

    @Transaction
    @Delete
    suspend fun delete(labels: List<LabelledNotes>)

    @Transaction
    @Query("delete FROM labelled_notes")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM labelled_notes")
     fun getAllLabelledNotes(): Flow<List<LabelledNotes>>

    @Transaction
    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<NoteWithLabels>>

    @Transaction
    @Query("SELECT * FROM labels")
    fun getLabels(): List<LabelWithNotes>

    @Transaction
    @Query("select * from notes n, labels l, labelled_notes ln where  n.noteId = ln.noteId and l.labelId = ln.labelId")
    fun getAllNotes(): List<NoteWithLabels>
    @Transaction
    @Query("select * from notes n, labels l, labelled_notes ln where  n.noteId = ln.noteId and l.labelId = ln.labelId")
    fun getNotesQry(): Flow<List<NoteWithLabels>>

    @Transaction
    @Query("select * from notes n, labels l, labelled_notes ln where  n.noteId = ln.noteId and l.labelId = ln.labelId")
    fun getLabelsQry(): Flow<List<LabelWithNotes>>


    @Transaction
    @Query("select * from notes n, labels l, labelled_notes ln " +
                "where n.noteId = ln.noteId " +
                    "and l.labelId = ln.labelId " +
                    "and  ln.noteId = (:noteId)"
    )
    fun getNotesByIdQry(noteId:Long): Flow<List<NoteWithLabels>>

    @Transaction
    @Query("select * from notes n, labels l, labelled_notes ln " +
                "where n.noteId = ln.noteId " +
                    "and l.labelId = ln.labelId " +
                    "and  ln.labelId = (:labelId)"
    )
    fun getLabelsByIdQry(labelId:Long): Flow<List<NoteWithLabels>>
}



