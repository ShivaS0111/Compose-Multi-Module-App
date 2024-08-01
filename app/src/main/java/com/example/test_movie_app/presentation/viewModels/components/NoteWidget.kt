package com.example.test_movie_app.presentation.viewModels.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.invia.domain.datasource.database.entities.Label
import com.invia.domain.datasource.database.entities.Note
import com.invia.domain.datasource.database.entities.relations.NoteWithLabels

@Composable
fun NoteWidget(note: NoteWithLabels, onNoteClick: ((id: Long) -> Unit)? = null) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, //Card background color
            // contentColor = Color.White  //Card content color,e.g.text
        ),
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                println("==>Note: ${note.note}")
                note.note.noteId?.let { onNoteClick?.invoke(it) }
            }
    ) {
        Column(Modifier.padding(8.dp)) {
            TitleWidget(title = note.note.title)
            SmallDescriptionWidget(desc = note.note.note)
            Box(modifier = Modifier.padding(3.dp))
            LabelsWidget(labels = note.labels, isChips = true)
        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun NoteWidgetPreview() {
    val note = Note(
        title = "Note title", note = "Both BasicText and Text have overflow and maxLines " +
                "arguments which can help you.BasicText and Text have overflow and maxLines arguments " +
                "which can help you.BasicText and Text have overflow and maxLines " +
                "arguments which can help you."
    )
    val labels = arrayListOf<Label>().apply {
        for (i in 1..10) {
            add(Label(label = "label ${(1..10).random()}"))
        }
    }

    NoteWidget(note = NoteWithLabels(note, labels))

}