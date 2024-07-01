package com.invia.domain.datasource.database.datasource

import com.invia.domain.datasource.database.dao.LabelDAO
import com.invia.domain.datasource.database.entities.Label
import kotlinx.coroutines.flow.Flow

interface LabelDataSource {
    var dao: LabelDAO
    suspend fun insert(label: Label): Long
    suspend fun insert(labels: List<Label>): List<Long>
    fun getAllLabels(): Flow<List<Label>>
    fun getLabelById(id: Long): Flow<List<Label>>
    fun getLabelBySearch(term: String): Flow<List<Label>>
    fun getLabelBySearchTerm(term: String): Flow<List<Label>>

}