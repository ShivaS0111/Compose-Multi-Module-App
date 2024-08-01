package com.example.test_movie_app.presentation.viewModels.screens.notes.labels.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.test_movie_app.presentation.viewModels.components.LabelsWidget
import com.example.test_movie_app.presentation.viewModels.components.SearchBar
import com.example.test_movie_app.presentation.viewModels.components.SearchDisplay
import com.example.test_movie_app.presentation.viewModels.components.rememberSearchState
import androidx.compose.runtime.setValue
import com.example.test_movie_app.presentation.viewModels.components.SearchState
import com.example.test_movie_app.presentation.viewModels.navigation.ResultViewModel
import com.example.test_movie_app.presentation.viewModels.stateHolders.StateHolder
import com.invia.domain.datasource.database.entities.Label

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LabelScreen(
    navHostController: NavHostController,
    resultViewModel: ResultViewModel,
    viewModel: LabelsViewModel = hiltViewModel(),
    selected: List<Long>? = null
) {
    val state = rememberSearchState()
    val result = viewModel.response.collectAsStateWithLifecycle()
    var showSheet by remember { mutableStateOf(false) }

    viewModel.setSelected(selected = selected ?: mutableListOf())

    /*Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "MyNotes") },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.Green
                )
            )
        },
        floatingActionButton = {
            Box(modifier = Modifier.padding(end = 20.dp, bottom = 60.dp)) {
                ExtendedFloatingActionButton(
                    text = { Text(text = "ADD Label") },
                    onClick = {
                        //showSheet = true
                              navHostController.navigate(Route.AddLabel)
                    },
                    icon = { Icon(Icons.Filled.Add, "") }
                )
            }
        },
        bottomBar = {
            if (viewModel.selected.isNotEmpty()) {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), onClick = { }) {
                    Text("Submit")
                }
            }
        }

    ) { innerPadding ->
        if (showSheet) {
            CustomBottomSheetWidget(onDismiss = {
                showSheet = false
            }, widget = { AddLabelWidget() })
        }
    }*/

    Body(viewModel, state, result, onClick = {
        resultViewModel.setResult(viewModel.selected.toList())
        navHostController.popBackStack()
    })
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun Body(
    viewModel: LabelsViewModel,
    state: SearchState,
    result: State<StateHolder<Label>>,
    onClick: () -> Unit,
) {
    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1.0f)
        ) {
            SearchBar(
                query = state.query,
                onQueryChange = { state.query = it },
                onSearchFocusChange = { state.focused = it },
                onClearQuery = { state.query = TextFieldValue("") },
                onBack = { state.query = TextFieldValue("") },
                searching = state.searching,
                focused = state.focused,
                modifier = Modifier
            )

            LaunchedEffect(state.query.text) {
                viewModel.labelSearch(state.query.text)
                state.searching = true
                //delay(100)
                println("===> search: ${state.query.text}")
                state.searchResults = viewModel.response.value.data ?: mutableListOf()
                state.searching = false
            }

            when (state.searchDisplay) {
                SearchDisplay.InitialResults -> {}
                SearchDisplay.NoResults -> {}
                SearchDisplay.Suggestions -> {}
                SearchDisplay.Results -> {}
            }

            if (result.value.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(Modifier.testTag("Loading..."))
                }
            } else {
                if (result.value.error.isNotBlank()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = result.value.error)
                    }
                }
                result.value.data?.let { labels ->
                    LabelsWidget(
                        labels = labels,
                        selected = viewModel.selected,
                        onLabelClick = { labelId -> viewModel.onToggleSelect(labelId) })
                }
            }
        }

        if (viewModel.selected.isNotEmpty()) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = onClick,
            ) {
                Text("Submit")
            }
        }
    }
}
