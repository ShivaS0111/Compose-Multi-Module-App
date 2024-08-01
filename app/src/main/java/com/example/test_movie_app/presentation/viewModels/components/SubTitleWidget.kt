package com.example.test_movie_app.presentation.viewModels.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun SubTitleWidget(title: String) {
    Text(text = title, style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 20.sp))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SubTitleWidgetPreview() {
    SubTitleWidget(title = "Sub Title")
}