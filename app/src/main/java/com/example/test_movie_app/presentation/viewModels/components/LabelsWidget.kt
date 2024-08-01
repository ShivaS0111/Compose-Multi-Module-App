package com.example.test_movie_app.presentation.viewModels.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.invia.domain.datasource.database.entities.Label


@Composable
fun LabelsWidget(
    labels: List<Label>,
    selected: List<Long> = mutableListOf(),
    onLabelClick: (id: Long) -> Unit? = {},
    onDeleteClick:((id: Long) -> Unit)?=null,
    isChips:Boolean = false
) {
    Box(Modifier.padding(horizontal = 15.dp)) {
        ChipVerticalGrid(
            spacing = 7.dp,
            modifier = Modifier.padding(2.dp),
        ) {
            labels.map { label ->
                if(isChips){
                    LabelChipWidget(label = label)
                }else {
                    LabelWidget(
                        label,
                        selected = selected.contains(label.labelId),
                        onLabelClick = onLabelClick,
                        onDeleteClick = onDeleteClick
                    )
                }
            }
        }
    }
}

fun getColorFromString(color: String?) =
    if (color != null) Color(android.graphics.Color.parseColor(color)) else null


@Preview(showBackground = true)
@Composable
fun LabelsWidgetPreview() {
    val desc = "Both BasicText and Text have overflow and maxLines " +
            "arguments which can help you. BasicText and Text have overflow and maxLines arguments " +
            "which can help you.BasicText and Text have overflow and maxLines " +
            "arguments which can help you."
    val labels = desc.split(" ").map {
        Label(label = it)
    }
    Column {
        LabelsWidget(labels, isChips = true)
        LabelsWidget(labels)
    }
}