package com.example.test_movie_app.presentation.viewModels.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.invia.domain.datasource.database.entities.Label


@Composable
fun LabelsNoteWidget(labels: List<Label>) {
    ChipVerticalGrid(
        spacing = 7.dp,
        modifier = Modifier
            .padding(1.dp)
    ) {
        labels.map { label ->
            val chipTextColor = getColorFromString(label.textColor) ?: Color.Black
            val chipBgColor = getColorFromString(label.color) ?: Color.Gray
            Text(
                label.label, style = TextStyle(fontSize = 10.sp, color = chipTextColor),
                modifier = Modifier
                    .background(color = chipBgColor, shape = CircleShape)
                    .padding(vertical = 3.dp, horizontal = 5.dp)
            )
        }
    }
}

fun getColorFromString(color: String?) =
    if (color != null) Color(android.graphics.Color.parseColor(color)) else null


@Preview(showBackground = true)
@Composable
fun LabelsNoteWidgetPreview() {
    val desc = "Both BasicText and Text have overflow and maxLines " +
            "arguments which can help you. BasicText and Text have overflow and maxLines arguments " +
            "which can help you.BasicText and Text have overflow and maxLines " +
            "arguments which can help you."
    val labels = desc.split(" ").map {
        Label(label = it)
    }
    LabelsNoteWidget(labels)
}