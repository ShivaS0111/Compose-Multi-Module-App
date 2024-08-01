package com.example.test_movie_app.presentation.viewModels.components

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.test_movie_app.R
/*

@Composable
fun RichEditorComponent(input: State<String>) {
    println("==>input:$input")
    val editorState =RichEditorState.Builder()
            .setInput(input.value)
            .adapter(JsonEditorParser())
            .build()

    Column {
        StyleContainer(editorState)
        RichEditor(
            state = editorState,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Yellow)
                .weight(1f)
                .border(1.dp, Color.Gray)
                .padding(5.dp)
        )
    }
}


@Composable
fun StyleContainer(
    state: RichEditorState,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.Start,
    ) {

        TitleStyleButton(state)
        StyleButton(
            icon = R.drawable.ic_bold,
            style = TextSpanStyle.BoldStyle,
            value = state
        )

        StyleButton(
            icon = R.drawable.ic_italic,
            style = TextSpanStyle.ItalicStyle,
            value = state,
        )

        StyleButton(
            icon = R.drawable.ic_underlined,
            style = TextSpanStyle.UnderlineStyle,
            value = state,
        )

        IconButton(
            modifier = Modifier
                .padding(2.dp)
                .size(48.dp),
            onClick = {
                Log.d("XXX", "json ${state.output()}")
                // state.reset()
            },
        ) {
            Icon(
                Icons.Default.Check, contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

    }
}


@Composable
fun TitleStyleButton(
    value: RichEditorState
) {
    var expanded by remember { mutableStateOf(false) }

    val onItemSelected = { style: TextSpanStyle ->
        value.updateStyle(style)
        expanded = false
    }

    Row {
        IconButton(
            modifier = Modifier
                .padding(2.dp)
                .size(48.dp),
            onClick = { expanded = true },
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_title),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize(),
            properties = PopupProperties(false)
        ) {

            DropDownItem(text = "Text",
                isSelected = value.hasStyle(TextSpanStyle.Default),
                onItemSelected = { onItemSelected(TextSpanStyle.Default) })
            DropDownItem(text = "Header 1", isSelected = value.hasStyle(TextSpanStyle.H1Style),
                onItemSelected = { onItemSelected(TextSpanStyle.H1Style) })
            DropDownItem(text = "Header 2", isSelected = value.hasStyle(TextSpanStyle.H2Style),
                onItemSelected = { onItemSelected(TextSpanStyle.H2Style) })
            DropDownItem(text = "Header 3", isSelected = value.hasStyle(TextSpanStyle.H3Style),
                onItemSelected = { onItemSelected(TextSpanStyle.H3Style) })
            DropDownItem(text = "Header 4", isSelected = value.hasStyle(TextSpanStyle.H4Style),
                onItemSelected = { onItemSelected(TextSpanStyle.H4Style) })
            DropDownItem(text = "Header 5", isSelected = value.hasStyle(TextSpanStyle.H5Style),
                onItemSelected = { onItemSelected(TextSpanStyle.H5Style) })
            DropDownItem(text = "Header 6", isSelected = value.hasStyle(TextSpanStyle.H6Style),
                onItemSelected = { onItemSelected(TextSpanStyle.H6Style) })
        }
    }
}

@Composable
fun DropDownItem(
    text: String,
    isSelected: Boolean,
    onItemSelected: () -> Unit
) {

    DropdownMenuItem(
        text = {
            Text(text = text)
        }, onClick = onItemSelected,
        modifier = Modifier.background(
            color = if (isSelected) {
                Color.Gray.copy(alpha = 0.2f)
            } else {
                Color.Transparent
            }, shape = RoundedCornerShape(6.dp)
        )
    )
}

@Composable
fun StyleButton(
    @DrawableRes icon: Int,
    style: TextSpanStyle,
    value: RichEditorState,
) {
    IconButton(
        modifier = Modifier
            .padding(2.dp)
            .size(48.dp)
            .background(
                color = if (value.hasStyle(style)) {
                    Color.Gray.copy(alpha = 0.2f)
                } else {
                    Color.Transparent
                }, shape = RoundedCornerShape(6.dp)
            ),
        onClick = {
            value.toggleStyle(style)
        },
    ) {
        Icon(
            painter = painterResource(id = icon), contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

const val data = "{\n" +
        "  \"spans\": [\n" +
        "    {\n" +
        "      \"from\": 0,\n" +
        "      \"to\": 10,\n" +
        "      \"style\": \"h1\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 0,\n" +
        "      \"to\": 10,\n" +
        "      \"style\": \"bold\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 19,\n" +
        "      \"to\": 27,\n" +
        "      \"style\": \"bold\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 44,\n" +
        "      \"to\": 59,\n" +
        "      \"style\": \"bold\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 44,\n" +
        "      \"to\": 59,\n" +
        "      \"style\": \"italic\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 44,\n" +
        "      \"to\": 59,\n" +
        "      \"style\": \"underline\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 61,\n" +
        "      \"to\": 69,\n" +
        "      \"style\": \"h3\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 62,\n" +
        "      \"to\": 69,\n" +
        "      \"style\": \"bold\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 103,\n" +
        "      \"to\": 118,\n" +
        "      \"style\": \"bold\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 119,\n" +
        "      \"to\": 126,\n" +
        "      \"style\": \"italic\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 130,\n" +
        "      \"to\": 138,\n" +
        "      \"style\": \"underline\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 160,\n" +
        "      \"to\": 167,\n" +
        "      \"style\": \"h3\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 161,\n" +
        "      \"to\": 167,\n" +
        "      \"style\": \"bold\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 169,\n" +
        "      \"to\": 180,\n" +
        "      \"style\": \"bold\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 224,\n" +
        "      \"to\": 235,\n" +
        "      \"style\": \"bold\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 224,\n" +
        "      \"to\": 235,\n" +
        "      \"style\": \"italic\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 224,\n" +
        "      \"to\": 235,\n" +
        "      \"style\": \"underline\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 237,\n" +
        "      \"to\": 251,\n" +
        "      \"style\": \"h4\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 238,\n" +
        "      \"to\": 250,\n" +
        "      \"style\": \"bold\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 238,\n" +
        "      \"to\": 250,\n" +
        "      \"style\": \"italic\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"from\": 238,\n" +
        "      \"to\": 250,\n" +
        "      \"style\": \"underline\"\n" +
        "    }\n" +
        "  ],\n" +
        "  \"text\": \"Rich Editor\\nAndroid WYSIWYG Rich editor for Jetpack compose.\\n\\nFeatures\\nThe editor offers the following options\\n\\n- Bold\\n- Italic\\n- Underline\\n- Different headers\\n\\nCredits\\nRich Editor for compose is owned and maintained by the canopas team\\n\\nThanks You ☺\uFE0F\\n\"\n" +
        "}"
*/
