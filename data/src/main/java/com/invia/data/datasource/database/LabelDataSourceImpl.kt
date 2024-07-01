package com.invia.data.datasource.database

import com.invia.domain.datasource.database.dao.LabelDAO
import com.invia.domain.datasource.database.datasource.LabelDataSource
import com.invia.domain.datasource.database.entities.Label
import javax.inject.Inject

class LabelDataSourceImpl @Inject constructor(override var dao: LabelDAO) : LabelDataSource {
    override suspend fun insert(label: Label) = dao.insert(label)

    override suspend fun insert(labels: List<Label>) = dao.insert(labels)

    override fun getAllLabels() = dao.getAllLabels()

    override  fun getLabelById(id: Long) = dao.getLabelById(id)

    override  fun getLabelBySearch(term: String) = dao.getLabelBySearch(term)

    override  fun getLabelBySearchTerm(term: String) = dao.getLabelBySearchTerm(term)
}