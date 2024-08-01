package com.example.test_movie_app.presentation.viewModels.screens.notes.labels.list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_movie_app.presentation.viewModels.stateHolders.StateHolder
import com.invia.domain.datasource.database.dao.LabelDAO
import com.invia.domain.datasource.database.entities.Label
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelsViewModel @Inject constructor(
    private var labelDAO: LabelDAO,
) : ViewModel() {
    internal var selected = mutableStateListOf<Long>()
    private var _response = MutableStateFlow(StateHolder<Label>().apply { })

    /*val response: StateFlow<StateHolder<Label>>
        get() = _selected.asStateFlow()*/

    val response: StateFlow<StateHolder<Label>>
        get() = _response.asStateFlow()

    init {
        labelSearch("")
    }

    fun setSelected(selected: List<Long>){
        this.selected.addAll(selected)
    }

    fun labelSearch(searchTerm: String) {
        viewModelScope.launch {
            if (searchTerm.isNotEmpty()) {
                labelDAO.getLabelBySearch(searchTerm)
            } else {
                labelDAO.getAllLabels()
            }.collectLatest {
                System.out.println("collectLatest ==>data: ${it.size}")
                _response.value = StateHolder(data = it)
            }
        }
    }

    fun labelsSearch(searchTerm: String): List<Label> {
        return labelDAO.getLabelBySearchList(searchTerm)
    }

    fun onToggleSelect(labelId: Long) {
        println("==>labelId:$labelId")
        if (selected.contains(labelId)) selected.remove(labelId)
        else selected.add(labelId)
    }
}