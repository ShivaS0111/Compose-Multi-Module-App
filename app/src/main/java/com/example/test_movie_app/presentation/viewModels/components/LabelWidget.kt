package com.example.test_movie_app.presentation.viewModels.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.invia.domain.datasource.database.entities.Label

@Composable
fun LabelChipWidget(label: Label) {
    val chipTextColor = label.textColor?.let { Color(it) } ?: Color.Black
    val chipBgColor = label.color?.let { Color(it) } ?: Color.Gray
    Text(
        label.label, style = TextStyle(fontSize = 12.sp, color = chipTextColor),
        modifier = Modifier
            .background(color = chipBgColor, shape = CircleShape)
            .padding(vertical = 4.dp, horizontal = 10.dp)
    )
}

@Composable
fun LabelWidget(
    label: Label,
    selected: Boolean? = false,
    onLabelClick: (id: Long) -> Unit? = {},
    onDeleteClick: ((id: Long) -> Unit)? =null,
) {
    val chipBgColor =
        if (selected == true) Color.Yellow else label.color?.let { Color(it) } ?: Color.Gray
    val chipTextColor =
        if (selected == true) Color.Black else label.textColor?.let { Color(it) } ?: Color.Black

    TextButton(
        modifier = Modifier// Border with rounded corners
            .background(color = chipBgColor, shape = CircleShape)
            .border(
                if (selected == true) 4.dp else 0.dp,
                if (selected == true) Color.Black else Color.Transparent,
                RoundedCornerShape(50.dp)
            )
            .border(
                if (selected == true) 2.dp else 0.dp,
                if (selected == true) Color.White else Color.Transparent,
                RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 4.dp)
            .height(40.dp),
        onClick = {
            label.labelId?.let { onLabelClick.invoke(it) }
        }
    ) {
        Text(
            text = label.label,
            style = TextStyle(
                fontSize = 15.sp,
                color = chipTextColor,
                fontWeight = FontWeight.SemiBold
            )
        )
        if (onDeleteClick!=null) {
            IconButton(
                onClick = { label.labelId?.let { onDeleteClick.invoke(it) } },
                Modifier
                    .padding(start = 5.dp)
                    .size(15.dp)
            ) {
                androidx.compose.material.Icon(
                    Icons.Filled.Close, contentDescription = null,
                    modifier = Modifier
                        .background(color = Color.Gray, shape = CircleShape)
                )
            }
        }
    }
}