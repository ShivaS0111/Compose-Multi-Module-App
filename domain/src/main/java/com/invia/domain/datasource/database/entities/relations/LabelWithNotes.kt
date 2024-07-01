package com.invia.domain.datasource.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.invia.domain.datasource.database.entities.Label
import com.invia.domain.datasource.database.entities.LabelledNotes
import com.invia.domain.datasource.database.entities.Note

data class LabelWithNotes(
    @Embedded val label: Label,
    @Relation(
        parentColumn = "labelId",
        entityColumn = "noteId",
        associateBy = Junction(LabelledNotes::class)
    )
    val notes: List<Note>
)