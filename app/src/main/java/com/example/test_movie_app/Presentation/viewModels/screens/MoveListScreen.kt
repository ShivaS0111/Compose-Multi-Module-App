package com.example.test_movie_app.Presentation.viewModels.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.test_movie_app.Presentation.viewModels.MoviesViewModel
import com.example.test_movie_app.Presentation.viewModels.components.ShowItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navHostController: NavHostController,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Movie List") },
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = Color.Green
            )
        )
    }) { innerPadding ->
        Column(
            Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {

                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(10.dp)
                    .background(color = Color.Yellow)
            ) {
                Text(text = "Button")
            }
            Box(modifier = Modifier.padding(innerPadding)) {
                val result = viewModel.response.value
                /* val data = remember {
                    mutableStateListOf<ShowsResponseItem>()
                }*/
                if (result.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment
                        = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.testTag("progress"))
                    }
                }
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
                    modifier = Modifier.padding(8.dp),
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