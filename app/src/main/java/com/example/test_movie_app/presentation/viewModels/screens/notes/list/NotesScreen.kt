package com.example.test_movie_app.presentation.viewModels.screens.notes.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.test_movie_app.presentation.viewModels.components.NoteWidget
import com.example.test_movie_app.presentation.viewModels.navigation.ResultViewModel
import com.example.test_movie_app.presentation.viewModels.navigation.Route
import com.example.test_movie_app.presentation.viewModels.screens.movies.movielist._button

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NotesScreen(
    navHostController: NavHostController,
    resultViewModel: ResultViewModel,
    viewModel: NotesViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    // Call getData() only once when the composable is first composed
    LaunchedEffect(Unit) { viewModel.getData() }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.getData() })

    NoteData(Modifier.fillMaxSize(), viewModel, isRefreshing, pullRefreshState,
        navHostController,
        onClick = {
        navHostController.navigate(Route.AddNote)
    })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteData(
    modifier: Modifier,
    viewModel: NotesViewModel,
    isRefreshing: Boolean,
    pullRefreshState: PullRefreshState,
    navHostController: NavHostController,
    onClick: () -> Unit
) {

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row {
            _button(0, "Add Note", onClick =  { navHostController.navigate(Route.AddNote) })
            _button(1, "Add Label", onClick = { navHostController.navigate(Route.AddLabel) })
            _button(0, "Delete All Notes", onClick = { viewModel.deleteAll() })
            _button(1, "Add Label", onClick = { viewModel.onCoroutine() })
            //_button(4, "co", viewModel)
        }
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .fillMaxSize()
        ) {

            val result = viewModel.response.collectAsStateWithLifecycle()
            if (result.value.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.testTag("Loading...")
                    )
                }
            } else {
                if (result.value.error.isNotBlank()) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text(text = result.value.error)
                    }
                }

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .padding(8.dp)
                        .matchParentSize()
                        .testTag("list"),
                    verticalItemSpacing = 10.dp, // Vertical spacing
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {

                    result.value.data?.let {
                        items(it.size) { index ->
                            NoteWidget(note = it[index], onNoteClick = { noteId->
                                val route = "${Route.EditNote}/$noteId"
                                println("edit-noteId: $noteId, route:$route")
                                navHostController.navigate( route)
                            })
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}