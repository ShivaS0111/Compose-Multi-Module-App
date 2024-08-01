package com.example.test_movie_app.presentation.viewModels.screens.notes.addnote

import androidx.lifecycle.viewModelScope
import com.darkrockstudios.richtexteditor.model.RichTextValue
import com.example.test_movie_app.BaseViewModel
import com.invia.domain.datasource.database.dao.LabelDAO
import com.invia.domain.datasource.database.dao.LabelledNotesDAO
import com.invia.domain.datasource.database.dao.NotesDAO
import com.invia.domain.datasource.database.entities.Label
import com.invia.domain.datasource.database.entities.LabelledNotes
import com.invia.domain.datasource.database.entities.Note
import com.invia.domain.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AddNotesViewModel @Inject constructor(
    private var repository: NotesRepository,
    private var notesDAO: NotesDAO,
    private var labelDAO: LabelDAO,
    private var labelledNotesDAO: LabelledNotesDAO
) : BaseViewModel() {

    private var noteId: Long = 0

    var ids: String = ""


    val note: StateFlow<String>
        get() = _note.asStateFlow()
    private var _note = MutableStateFlow("")

    val richTextValue: StateFlow<RichTextValue>
        get() = _richTextValue.asStateFlow()
    private var _richTextValue = MutableStateFlow(
        RichTextValue.get()
    )

    val selectedLabels: StateFlow<List<Label>>
        get() = _selectedLabels.asStateFlow()
    private var _selectedLabels = MutableStateFlow(listOf<Label>())


    init {
        viewModelScope.launch {}
    }


    fun updateNoteId(noteId: Long) {
        this.noteId = noteId
        viewModelScope.launch(Dispatchers.IO) {
            val noteById = notesDAO.getNotesById(noteId)
            println("==>noteId:$noteId, note:${noteById?.note}")
            noteById?.let {
                _note.value = it.note.note

                _richTextValue.value = RichTextValue.fromString(text =it.note.note, styleMapper = CustomStyleMapper() )
                if (it.labels.isNotEmpty())
                    updateSelectedLabels(it.labels)
            }
        }
    }

    fun labelSearch(searchTerm: String) {

    }

    private fun updateSelectedLabels(selectedList: List<Label>) {
        println("==>updateSelectedLabels: ${selectedList.size}: $selectedList")
        _selectedLabels.value = selectedList
    }

    override fun onResult(screenResult: Any?) {
        super.onResult(screenResult)
        if (screenResult is List<*> && screenResult.isNotEmpty()) {
            val ids = screenResult.mapNotNull { it as? Long }
            println("==>ids: $ids")
            this.ids = ids.joinToString(",")

            val labels = labelDAO.getLabelByIds(ids)
            println("==>labels: $labels")
            //resultCallBack.setResult(labels)
            updateSelectedLabels(labels)
        }
    }

    override fun onCleared() {
        saveNote()
        super.onCleared()
        println("==>onCleared: ${this.javaClass.name}")
    }

    fun onDeleteLabel(labelId: Long) {
        println("==>onDeleteLabel: $labelId")
        updateSelectedLabels(_selectedLabels.value.filter { it.labelId != labelId })
    }

    fun saveNote() {
        println("==>note data: ${note.value} ")

        viewModelScope.launch(Dispatchers.IO) {
            val noteData = richTextValue.value.getLastSnapshot().text
            val title = if (noteData.length > 20) noteData.substring(0,20) else noteData

            if (noteId.toInt() == 0) {
                noteId = notesDAO.insert(Note(title = title, note = noteData))
            } else {
                notesDAO.update(Note(noteId = noteId, title = title, note = noteData))
            }

            println("==>Note Id: $noteId")
            val labelledNotes: List<LabelledNotes> = selectedLabels.value.mapNotNull {
                it.labelId?.let { labelId -> LabelledNotes(noteId = noteId, labelId = labelId) }
            }
            labelledNotesDAO.insert(labelledNotes)
        }
    }

    fun setRichTextValue(richTextVal: RichTextValue) {
        _note.value = richTextVal.getLastSnapshot().text
        _richTextValue.value = richTextVal
    }

}