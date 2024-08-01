package com.example.test_movie_app.presentation.viewModels.screens.notes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_movie_app.BaseViewModel
import com.example.test_movie_app.presentation.viewModels.stateHolders.StateHolder
import com.invia.domain.datasource.database.dao.LabelDAO
import com.invia.domain.datasource.database.dao.LabelledNotesDAO
import com.invia.domain.datasource.database.dao.NotesDAO
import com.invia.domain.datasource.database.entities.Label
import com.invia.domain.datasource.database.entities.LabelledNotes
import com.invia.domain.datasource.database.entities.Note
import com.invia.domain.datasource.database.entities.relations.NoteWithLabels
import com.invia.domain.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private var repository: NotesRepository,
    private var notesDAO: NotesDAO,
    private var labelDAO: LabelDAO,
    private var labelledNotesDAO: LabelledNotesDAO
) : BaseViewModel() {


    private var _response = MutableStateFlow(StateHolder<NoteWithLabels>().apply { })
    val response: StateFlow<StateHolder<NoteWithLabels>>
        get() = _response.asStateFlow()

    init {
        viewModelScope.launch {
            _response.value = StateHolder(isLoading = true)
            notesDAO.getAllNotes().collectLatest {
                println("===>notes: ${it.size}")

                _response.value = StateHolder(data = it)
                //if (it.isEmpty()) createSampleData()
            }
        }
    }

    private suspend fun createSampleData() {
        println("\n\n===========> notes=========== $notesDAO")
        arrayListOf<Note>().apply {
            for (i in 1..10) {
                add(Note(title = "title$i", note = "Sample note abc $i "))
            }
        }.apply {
            //notesDAO.deleteAll()
            notesDAO.insert(this)
                .zip(this)
                .forEach {
                    println("${it.first}  => ${it.second}")
                }
        }

        println("\n\n===========> labels =========== $labelDAO")
        arrayListOf<Label>().apply {
            for (i in 1..10) {
                add(Label(label = "label $i"))
            }
        }.apply {
            //notesDAO.deleteAll()
            labelDAO.insert(this)
                .zip(this)
                .forEach {
                    println("${it.first}  => ${it.second}")
                }
        }


        println("\n\n===========> relations =========== $labelledNotesDAO")
        arrayListOf<LabelledNotes>().apply {
            for (i in 1..30) {
                val noteId = (1..10).random()
                val labelId = (1..10).random()
                add(LabelledNotes(noteId = noteId.toLong(), labelId = labelId.toLong()))
            }
        }.apply {
            //notesDAO.deleteAll()
            labelledNotesDAO.insert(this)
                .zip(this)
                .forEach {
                    println("${it.first}  => ${it.second}")
                }
        }
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            _response.value = StateHolder(isLoading = true)
            notesDAO.getRefreshNotes().let {
                println("getData ===>notes: ${it.size}")
                _response.value = StateHolder(data = it)
            }
        }
    }

    fun onCoroutine() {
    }

    fun addNote() {
        viewModelScope.launch {
            val ref = (1..10).random()
            val n = notesDAO.insert( Note(title = "title:$ref ", note = "sampleNote:$ref"))
            val l = labelDAO.insert( Label( label = "Label: ${(1..10).random()}"))
            labelledNotesDAO.insert(LabelledNotes(n, l))
        }
    }

    fun addLabel() {

    }

    fun deleteAll() {
        viewModelScope.launch {
            notesDAO.deleteAll()
            labelDAO.deleteAll()
            labelledNotesDAO.deleteAll()
        }
    }
}