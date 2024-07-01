package com.invia.domain.datasource.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.invia.domain.datasource.database.entities.Label
import com.invia.domain.datasource.database.entities.LabelledNotes
import com.invia.domain.datasource.database.entities.Note

data class NoteWithLabels(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "labelId",
        associateBy = Junction(LabelledNotes::class)
    )
    val labels: List<Label>
)