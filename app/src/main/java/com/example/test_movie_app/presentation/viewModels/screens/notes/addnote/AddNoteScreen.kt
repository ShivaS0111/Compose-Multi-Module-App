package com.example.test_movie_app.presentation.viewModels.screens.notes.addnote

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.darkrockstudios.richtexteditor.mappers.StyleMapper
import com.darkrockstudios.richtexteditor.model.RichTextValue
import com.darkrockstudios.richtexteditor.model.Style
import com.darkrockstudios.richtexteditor.ui.RichText
import com.darkrockstudios.richtexteditor.ui.RichTextEditor
import com.darkrockstudios.richtexteditor.ui.RichTextFieldStyle
import com.darkrockstudios.richtexteditor.ui.defaultRichTextStyle
import com.example.test_movie_app.R
import com.example.test_movie_app.presentation.viewModels.components.LabelsWidget
import com.example.test_movie_app.presentation.viewModels.components.TitleWidget
import com.example.test_movie_app.presentation.viewModels.components.rememberSearchState
import com.example.test_movie_app.presentation.viewModels.navigation.ResultViewModel
import com.example.test_movie_app.presentation.viewModels.navigation.Route
import com.invia.domain.datasource.database.entities.Label
import kotlin.random.Random

@Composable
fun AddNoteScreen(
    navHostController: NavHostController,
    resultViewModel: ResultViewModel,
    viewModel: AddNotesViewModel = hiltViewModel(),
    noteId: Long? = null
) {

    noteId?.let { viewModel.updateNoteId(it) }
    val state = rememberSearchState()
    val screenResult by viewModel.resultCallBack.result.collectAsStateWithLifecycle()
    val noteLabels = viewModel.selectedLabels.collectAsStateWithLifecycle()
    val note = viewModel.note.collectAsStateWithLifecycle()
    val richTextValue = viewModel.richTextValue.collectAsStateWithLifecycle()
    viewModel.setResultViewModel(resultViewModel)

    Column {
        Column(Modifier.weight(1.0f)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Labels",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                )
                TextButton(
                    onClick = {
                        val route: String =
                            if (viewModel.ids.isNotEmpty()) "${Route.Labels}/${viewModel.ids}" else Route.Labels
                        println("==>route: $route")
                        navHostController.navigate(route)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                ) {
                    Text("ADD")
                }
            }

            if (screenResult is List<*> && (screenResult as List<*>).isNotEmpty()) {
                val labels = (screenResult as List<*>).mapNotNull { it as? Label }
                LabelsWidget(labels = labels, onDeleteClick = { id -> viewModel.onDeleteLabel(id) })
            }
            if (noteLabels.value.isNotEmpty()) {
                LabelsWidget(
                    labels = noteLabels.value,
                    onDeleteClick = { id -> viewModel.onDeleteLabel(id) })
            }
            /*//RichEditorComponent(note)
            //RichTextSample()
            val richTextState = rememberRichTextState()
            richTextState.config.linkColor = Color.Blue
            richTextState.config.linkTextDecoration = TextDecoration.Underline
            richTextState.config.codeSpanColor = Color.Yellow
            richTextState.config.codeSpanBackgroundColor = Color.Transparent
            richTextState.config.codeSpanStrokeColor = Color.LightGray

            RichTextEditor(
                state = richTextState,
            )*/

            //var value = richTextValue.value
            var value by remember {
                mutableStateOf(
                    RichTextValue.fromString(
                        text = note.value,
                        // Optional parameter; leave it blank if you want to use provided styles
                        // But if you want to customize the user experience you're free to do that
                        // by providing a custom StyleMapper
                        styleMapper = CustomStyleMapper()
                    )
                )
            }


            Column(modifier = Modifier.fillMaxSize()) {


                Box(
                    modifier = Modifier.fillMaxWidth().height(30.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(Color.DarkGray)
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Button for a custom style
                        IconButton(onClick = {
                            value = value.insertStyle(BoldRedStyle)
                        }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_bold),
                                tint = if (value.currentStyles.contains(BoldRedStyle)) {
                                    Color.Red
                                } else {
                                    Color.Red.copy(alpha = 0.3f)
                                },
                                contentDescription = null
                            )
                        }

                        EditorAction(
                            iconRes = R.drawable.ic_bold,
                            active = value.currentStyles.contains(Style.Bold)
                        ) {
                            value = value.insertStyle(Style.Bold)
                        }
                        EditorAction(
                            iconRes = R.drawable.ic_underlined,
                            active = value.currentStyles.contains(Style.Underline)
                        ) {
                            value = value.insertStyle(Style.Underline)
                        }
                        EditorAction(
                            iconRes = R.drawable.ic_italic,
                            active = value.currentStyles.contains(Style.Italic)
                        ) {
                            value = value.insertStyle(Style.Italic)
                        }
                        EditorAction(
                            iconRes = R.drawable.ic_underlined,
                            active = value.currentStyles.contains(Style.Strikethrough)
                        ) {
                            value = value.insertStyle(Style.Strikethrough)
                        }
                        EditorAction(
                            iconRes = R.drawable.ic_underlined,
                            active = value.currentStyles.contains(Style.AlignLeft)
                        ) {
                            value = value.insertStyle(Style.AlignLeft)
                        }
                        EditorAction(
                            iconRes = R.drawable.ic_underlined,
                            active = value.currentStyles.contains(Style.AlignCenter)
                        ) {
                            value = value.insertStyle(Style.AlignCenter)
                        }
                        EditorAction(
                            iconRes = R.drawable.ic_underlined,
                            active = value.currentStyles.contains(Style.AlignRight)
                        ) {
                            value = value.insertStyle(Style.AlignRight)
                        }
                        EditorAction(
                            iconRes = R.drawable.ic_underlined,
                            active = value.currentStyles
                                .filterIsInstance<Style.TextSize>()
                                .isNotEmpty()
                        ) {
                            // Remove all styles in selected region that changes the text size
                            value = value.clearStyles(Style.TextSize())

                            // Here you would show a dialog of some sorts and allow user to pick
                            // a specific text size. I'm gonna use a random one between 50% and 200%

                            value = value.insertStyle(
                                Style.TextSize(
                                    (Random.nextFloat() *
                                            (Style.TextSize.MAX_VALUE - Style.TextSize.MIN_VALUE) +
                                            Style.TextSize.MIN_VALUE).toFloat()
                                )
                            )
                        }
                        EditorAction(
                            iconRes = R.drawable.ic_image,
                            active = value.currentStyles
                                .filterIsInstance<Style.TextColor>()
                                .isNotEmpty()
                        ) {
                            // Remove all styles in selected region that changes the text color
                            value = value.clearStyles(Style.TextColor())

                            // Here you would show a dialog of some sorts and allow user to pick
                            // a specific color. I'm gonna use a random one

                            value = value.insertStyle(
                                Style.TextColor(Random.nextInt(360).hueToColor())
                            )
                        }
                        EditorAction(com.google.android.material.R.drawable.ic_clear_black_24, active = true) {
                            value = value.insertStyle(Style.ClearFormat)
                        }
                        EditorAction(
                            iconRes = com.google.android.material.R.drawable.m3_popupmenu_background_overlay ,
                            active = value.isUndoAvailable
                        ) {
                            value = value.undo()
                        }
                        EditorAction(
                            iconRes =R.drawable.ic_image,
                            active = value.isRedoAvailable
                        ) {
                            value = value.redo()
                        }
                    }
                }
                RichTextEditor(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .padding(16.dp),
                    value = value,
                    onValueChange = { value = it },
                    textFieldStyle = defaultRichTextFieldStyle().copy(
                        textColor = Color.Black,
                        placeholderColor = Color.LightGray,
                        placeholder = "My rich text editor in action"
                    )
                )

                // If you want to display formatted text you can use [RichText] instead of [RichTextEditor]
                /*RichText(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    value = value,
                    textStyle = defaultRichTextStyle().copy(
                        textColor = Color.Black,
                    )
                )*/
            }
        }

        Button(onClick = {
            viewModel.saveNote()
            navHostController.popBackStack()

        }, Modifier.fillMaxWidth()) {
            TitleWidget(title = "Save Note")
        }
    }
}

@Composable
fun defaultRichTextFieldStyle() = RichTextFieldStyle(
    keyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences,
    ),
    placeholder = "",
    textStyle = MaterialTheme.typography.body1,
    textColor = MaterialTheme.colors.onPrimary,
    placeholderColor = MaterialTheme.colors.secondaryVariant,
    cursorColor = MaterialTheme.colors.secondary,
)


object CustomParagraphStyle : Style
object CustomStyle : Style
class CustomStyleMapper : StyleMapper() {

    override fun fromTag(tag: String): Style =
        runCatching { super.fromTag(tag) }.getOrNull() ?: when (tag) {
            // It is necessary to ensure undo/redo actions work correctly
            "${CustomStyle.javaClass.simpleName}/" -> CustomStyle
            "${CustomParagraphStyle.javaClass.simpleName}/" -> CustomParagraphStyle
            else -> throw IllegalArgumentException()
        }

    @OptIn(ExperimentalUnitApi::class)
    override fun toSpanStyle(style: Style): SpanStyle? = super.toSpanStyle(style) ?: when (style) {
        // Here we're customizing the behavior of the style
        is CustomStyle -> SpanStyle(
            color = Color.Red,
            fontWeight = FontWeight.Bold,
        )

        else -> null
    }

    override fun toParagraphStyle(style: Style): ParagraphStyle? =
        super.toParagraphStyle(style) ?: when (style) {
            is CustomParagraphStyle -> ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 12.sp)
            )

            else -> null
        }
}

@Composable
private fun EditorAction(
    @DrawableRes iconRes: Int?=null,
    active: Boolean,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = Modifier.size(24.dp),
            //painter = painterResource(id = iconRes!!),
            painter = painterResource(id = R.drawable.ic_italic),
            tint = if (active) Color.White else Color.Black,
            contentDescription = null
        )
    }
}

private fun Int.hueToColor(saturation: Float = 1f, value: Float = 0.5f): Color = Color(
    ColorUtils.HSLToColor(floatArrayOf(this.toFloat(), saturation, value))
)

object BoldRedStyle : Style
