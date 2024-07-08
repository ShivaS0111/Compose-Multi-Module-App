package com.example.test_movie_app.presentation.viewModels.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun SmallDescriptionWidget(desc: String) {
    Text(
        text = desc,
        style = TextStyle(fontWeight = FontWeight.Normal),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview(showBackground = true)
@Composable
fun SmallDescriptionWidgetPreview() {
    val desc = "Both BasicText and Text have overflow and maxLines " +
            "arguments which can help you.BasicText and Text have overflow and maxLines arguments " +
            "which can help you.BasicText and Text have overflow and maxLines " +
            "arguments which can help you.";
    SmallDescriptionWidget(desc)
}