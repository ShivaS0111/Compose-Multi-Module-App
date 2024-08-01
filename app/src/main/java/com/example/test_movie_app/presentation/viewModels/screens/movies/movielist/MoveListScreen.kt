package com.example.test_movie_app.presentation.viewModels.screens.movies.movielist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.test_movie_app.ResultCallBack
import com.example.test_movie_app.presentation.viewModels.components.ShowItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navHostController: NavHostController,
    viewModel: MoviesViewModel = hiltViewModel(),
    resultCallback: ResultCallBack?=null
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.getAllTvShows()
        }
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Movie List") },
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = Color.Green
            )
        )
    }) { innerPadding ->
        _body(
            Modifier
                .fillMaxSize()
                .padding(innerPadding), viewModel, navHostController
        )
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun _body(modifier: Modifier, viewModel: MoviesViewModel, navController: NavHostController) {

    val coroutineScope = rememberCoroutineScope()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.getAllTvShows() })
    val result = viewModel.response.collectAsStateWithLifecycle()

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row {
            _button(0, "co", onClick = {
                navController.navigate("notes_list")
            })
            _button(1, "join", onClick = { viewModel.onCoroutine() })
            _button(2, "cancel", onClick = { viewModel.onCoroutine() })
            _button(3, "cancelJoin", onClick = { viewModel.onCoroutine() })
            //_button(4, "co", viewModel)
        }
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .fillMaxSize()
        ) {
            if (result.value.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment
                    = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .testTag("Loading...")
                    )
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
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .padding(8.dp)
                        .testTag("list"),
                    verticalItemSpacing = 10.dp, // Vertical spacing
                    horizontalArrangement = Arrangement.spacedBy(10.dp),


                    ) {
                    result.value.data?.let { it ->
                        items(it.size) { index ->
                            ShowItem(showsResponseItem = it[index])
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }
    }
}

fun _button1(case: String, text: String, onClick: () -> Unit) {

}

@Composable
fun _button(case: Int, text: String, onClick: (() -> Unit)? = null) {
    Button(
        modifier = Modifier
            .height(60.dp)
            .padding(10.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Color.LightGray,
            contentColor = Color.Red
        ),
        onClick = {
            onClick?.invoke()
        }
    ) {
        Text(text = text)
    }
}
