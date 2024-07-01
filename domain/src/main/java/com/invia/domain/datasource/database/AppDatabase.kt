package com.invia.domain.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.invia.domain.datasource.database.dao.LabelDAO
import com.invia.domain.datasource.database.dao.LabelledNotesDAO
import com.invia.domain.datasource.database.dao.MovieDAO
import com.invia.domain.datasource.database.dao.NotesDAO
import com.invia.domain.datasource.database.entities.Label
import com.invia.domain.datasource.database.entities.LabelledNotes
import com.invia.domain.datasource.database.entities.Movie
import com.invia.domain.datasource.database.entities.Note

internal const val dbFileName = "notes.db"
@Database(
    entities = [
        Note::class,
        Label::class,
        LabelledNotes::class,
        Movie::class,
    ],
    version = 1
)
@TypeConverters(RoomDataTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDAO(): NotesDAO
    abstract fun labelsDAO(): LabelDAO
    abstract fun moviesDAO(): MovieDAO
    abstract fun labelledNotesDao(): LabelledNotesDAO

}