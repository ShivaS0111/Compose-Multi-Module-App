package com.example.test_movie_app.presentation.viewModels.screens.notes.labels.addLabel

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
class AddLabelsViewModel @Inject constructor(
    private var labelDAO: LabelDAO,
) : ViewModel() {


    private var _textColorSelectionSelected = MutableStateFlow(false)
    val textColorSelectionSelected: StateFlow<Boolean>
        get() = _textColorSelectionSelected.asStateFlow()

    private var _tag = MutableStateFlow<Label?>(null)
    val tag: StateFlow<Label?>
        get() = _tag.asStateFlow()

    private var _tagBgColor = MutableStateFlow<Label?>(null)
    val tagBgColor: StateFlow<Label?>
        get() = _tagBgColor.asStateFlow()

    private var _tagTextColor = MutableStateFlow<Label?>(null)
    val tagTextColor: StateFlow<Label?>
        get() = _tagTextColor.asStateFlow()

    fun updateSelectionType(isSelected: Boolean) {
        _textColorSelectionSelected.value = isSelected
    }

    fun updateTagText(label: String) {
        _tag.value = _tag.value?.copy(label = label) ?: Label(label = label)
        println(" ==>label:$label, ${_tag.value}")
    }

    fun updateTagBg(bgColor: Int) {
        _tag.value = _tag.value?.copy(color = bgColor) ?: Label(label = "", color = bgColor)
        println(" ==>bgColor:$bgColor, ${_tag.value}")
    }

    fun updateTagTextColor(textColor: Int) {
        _tag.value = _tag.value?.copy(textColor = textColor) ?: Label(
            label = "",
            textColor = textColor
        )
        println(" ==>textColor:$textColor, ${_tag.value}")
    }

    fun addTag() {
        viewModelScope.launch {
            tag.value?.let {
                println("==>Tag Insert: $it")
                labelDAO.insert(it)
            }
        }
    }

}