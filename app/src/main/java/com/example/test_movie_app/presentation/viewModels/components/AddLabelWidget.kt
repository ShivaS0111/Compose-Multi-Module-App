package com.example.test_movie_app.presentation.viewModels.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun AddLabelWidget(modifier: Modifier? = Modifier) {
    Column(modifier!!.padding(10.dp)) {
        TextField(modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = {
                Text("Enter Tag name")
            }
        )
        Button(modifier = Modifier.fillMaxWidth(),
            onClick = { }) {
            Text(text = "Add Tag")
        }
    }
}

@Composable
@Preview
fun AddLabelWidgetPreview() {
    AddLabelWidget()
}