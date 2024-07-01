package com.example.test_movie_app.presentation.viewModels.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.test_movie_app.presentation.viewModels.MoviesViewModel
import com.example.test_movie_app.presentation.viewModels.components.ShowItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navHostController: NavHostController,
    viewModel: MoviesViewModel = hiltViewModel()
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
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row{
                _button(0, "co", viewModel)
                _button(1, "join", viewModel)
                _button(2, "cancel", viewModel)
                _button(3, "cancelJoin", viewModel)
                //_button(4, "co", viewModel)
            }
            Box(modifier = Modifier) {
                val result = viewModel.collectData().value
                /* val data = remember {
                    mutableStateListOf<ShowsResponseItem>()
                }*/
                if (result.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment
                        = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier
                            .testTag("Loading...")
                        )
                    }
                }else {
                    if (result.error.isNotBlank()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = result.error)
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
                        result.data?.let { it ->
                            items(it.size) { index ->
                                ShowItem(showsResponseItem = it[index])
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun _button(case: Int, text:String, viewModel: MoviesViewModel) {
    Button(
        modifier = Modifier
            .height(60.dp)
            .padding(10.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Color.LightGray,
            contentColor = Color.Red
        ),
        onClick = {
            //viewModel.onCoroutine()
        }
    ) {
        Text(text = text)
    }
}
