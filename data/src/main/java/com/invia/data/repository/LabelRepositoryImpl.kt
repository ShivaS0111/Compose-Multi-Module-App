package com.invia.data.repository

import com.invia.domain.common.Result
import com.invia.domain.datasource.database.datasource.LabelDataSource
import com.invia.domain.datasource.database.datasource.NotesDataSource
import com.invia.domain.datasource.database.entities.Label
import com.invia.domain.datasource.database.entities.Movie
import com.invia.domain.datasource.database.entities.Note
import com.invia.domain.datasource.database.entities.relations.NoteWithLabels
import com.invia.domain.repository.LabelRepository
import com.invia.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LabelRepositoryImpl @Inject constructor(private var dataSource: LabelDataSource):LabelRepository {
    override suspend fun searchLabel(searchTerm: String) = dataSource.getLabelBySearch(searchTerm)

}