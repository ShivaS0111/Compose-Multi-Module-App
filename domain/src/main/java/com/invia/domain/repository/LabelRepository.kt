package com.invia.domain.repository

import com.invia.domain.common.Result
import com.invia.domain.datasource.database.entities.Label
import com.invia.domain.datasource.database.entities.Movie
import com.invia.domain.datasource.database.entities.Note
import com.invia.domain.datasource.database.entities.relations.NoteWithLabels
import kotlinx.coroutines.flow.Flow

interface LabelRepository {
    suspend fun searchLabel(searchTerm:String): Flow<List<Label>>

}