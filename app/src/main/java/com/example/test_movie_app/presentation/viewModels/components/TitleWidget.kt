package com.example.test_movie_app.presentation.viewModels.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TitleWidget(title:String) {
    Text(text = title, style = TextStyle( fontWeight = FontWeight.SemiBold))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TitleWidgetPreview() {
    TitleWidget(title = "MyNote")
}