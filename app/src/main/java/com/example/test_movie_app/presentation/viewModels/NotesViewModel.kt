package com.example.test_movie_app.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_movie_app.presentation.viewModels.stateHolders.StateHolder
import com.invia.domain.common.Result
import com.invia.domain.datasource.database.dao.LabelDAO
import com.invia.domain.datasource.database.dao.LabelledNotesDAO
import com.invia.domain.datasource.database.dao.NotesDAO
import com.invia.domain.datasource.database.datasource.NotesDataSource
import com.invia.domain.datasource.database.entities.Label
import com.invia.domain.datasource.database.entities.LabelledNotes
import com.invia.domain.datasource.database.entities.Movie
import com.invia.domain.datasource.database.entities.Note
import com.invia.domain.datasource.database.entities.relations.NoteWithLabels
import com.invia.domain.useCases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class NotesViewModel @Inject constructor(private var dataSource: NotesDataSource) : ViewModel() {

    @Inject
    lateinit var labelDAO: LabelDAO

    @Inject
    lateinit var notesDAO: NotesDAO

    @Inject
    lateinit var labelledNotesDAO: LabelledNotesDAO

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()


    private var _response = MutableStateFlow(StateHolder<NoteWithLabels>().apply { })
    val response: StateFlow<StateHolder<NoteWithLabels>>
        get() = _response.asStateFlow()

    init {
        viewModelScope.launch {
            createSampleData()
            dataSource.getAllNotes().collect {
                println("==>note: ${it.size}")
                _response.value = StateHolder(data = it)
            }
        }
    }

    private suspend fun createSampleData() {

        dataSource.getAllNotes().collect { notes->

            if (notes.isEmpty()) {

                println("\n\n===========> notes=========== $notesDAO")
                arrayListOf<Note>().apply {
                    for (i in 1..10) {
                        add(Note(title = "title$i", note = "Sample note abc $i "))
                    }
                }.apply {
                    //notesDAO.deleteAll()
                    notesDAO?.insert(this)
                        ?.zip(this)
                        ?.forEach {
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
                    labelDAO?.insert(this)
                        ?.zip(this)
                        ?.forEach {
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
                    labelledNotesDAO?.insert(this)
                        ?.zip(this)
                        ?.forEach {
                            println("${it.first}  => ${it.second}")
                        }
                }
            }
        }
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            _response.value = StateHolder(isLoading = true)
            labelledNotesDAO.getAllNotes().let {
                println("getData ===>notes: ${it.size}")
                _response.value = StateHolder(data = it)
            }
        }
    }

    fun onCoroutine() {
    }
}