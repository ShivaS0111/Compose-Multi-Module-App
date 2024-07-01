package com.invia.domain.datasource.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "labelled_notes",
    primaryKeys = ["noteId", "labelId"],
    foreignKeys = [
        ForeignKey(entity = Note::class, parentColumns = ["noteId"], childColumns = ["noteId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Label::class, parentColumns = ["labelId"], childColumns = ["labelId"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index(value = ["noteId"]), Index(value = ["labelId"])]
)
data class LabelledNotes(
    val noteId: Long,
    val labelId: Long,
)