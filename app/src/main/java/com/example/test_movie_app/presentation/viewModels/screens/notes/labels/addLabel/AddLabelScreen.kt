package com.example.test_movie_app.presentation.viewModels.screens.notes.labels.addLabel

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Tab
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.test_movie_app.presentation.viewModels.components.LabelChipWidget
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import com.godaddy.android.colorpicker.toColorInt
import okhttp3.internal.wait

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AddLabelScreen(
    navHostController: NavHostController,
    viewModel: AddLabelsViewModel = hiltViewModel()
) {
    val tag = viewModel.tag.collectAsStateWithLifecycle()
    val textColorSelectionSelected =
        viewModel.textColorSelectionSelected.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Tag") },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.Green
                )
            )
        },

        ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Column(
                Modifier
                    .weight(1.0F)
                    .padding(20.dp)
                    .verticalScroll(scrollState)
            ) {
                TextField(modifier = Modifier.fillMaxWidth(),
                    value = (tag.value?.label ?: ""),
                    onValueChange = {
                        viewModel.updateTagText(it)
                    },
                    placeholder = {
                        Text("Enter Tag name")
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    tag.value?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically // Vertically align children to the center
                        ) {
                            Text(text = "Tag look like:  ")
                            LabelChipWidget(label = it)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        Tab(
                            modifier = Modifier.weight(1.0f).height(40.dp),
                            selected = textColorSelectionSelected.value,
                            selectedContentColor = Color.Blue,
                            unselectedContentColor = Color.White,
                            onClick = { viewModel.updateSelectionType(true) }) {
                            Text("Text Color")
                        }
                        Tab(
                            modifier = Modifier.weight(1.0f).height(40.dp),
                            selected = !textColorSelectionSelected.value ,
                            selectedContentColor = Color.Blue,
                            unselectedContentColor = Color.White,
                            onClick = { viewModel.updateSelectionType(false)  }) {
                            Text("Bg Color")
                        }
                    }
                    Row{
                        Box(
                            modifier = Modifier
                                .weight(1.0f)
                                .height(5.dp)
                                .background(color = if (textColorSelectionSelected.value) Color.LightGray else Color.Transparent)
                        ) {
                        }
                        Box(
                            modifier = Modifier
                                .weight(1.0f)
                                .height(5.dp)
                                .background(color = if (!textColorSelectionSelected.value) Color.LightGray else Color.Transparent)
                        ) {
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))
                    ClassicColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        color = HsvColor.from(color = Color.Yellow),
                        showAlphaBar = true,
                        onColorChanged = {
                            if (textColorSelectionSelected.value) {
                                viewModel.updateTagTextColor(it.toColorInt())
                            } else {
                                viewModel.updateTagBg(it.toColorInt())
                            }
                        }
                    )
                }
            }
            Button(modifier = Modifier.fillMaxWidth().height(60.dp),
                onClick = {
                    viewModel.addTag()
                }) {
                Text(text = "Add Tag", style = TextStyle(color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 22.sp))
            }
        }
    }
}
