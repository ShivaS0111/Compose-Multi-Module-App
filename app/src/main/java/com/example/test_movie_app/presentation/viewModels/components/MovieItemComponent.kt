package com.example.test_movie_app.presentation.viewModels.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import com.example.test_movie_app.R
import com.invia.domain.model.ShowsResponseItem

@Composable
fun ShowItem(showsResponseItem:ShowsResponseItem) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, //Card background color
            // contentColor = Color.White  //Card content color,e.g.text
        ),
        modifier = Modifier
            //.padding(8.dp)
            .wrapContentSize()
    ) {
        Column(
        ) {
            showsResponseItem.image?.let {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = it.original,
                        imageLoader = ImageLoader.Builder(LocalContext.current)
                            .placeholder(R.drawable.placeholder).crossfade(true)
                            .diskCachePolicy(CachePolicy.ENABLED).build()
                    ),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .aspectRatio(matchHeightConstraintsFirst = false, ratio = 0.68f)
                        .testTag(showsResponseItem.id?.toString()?:"lwlkd")
                        .then((painter as? AsyncImagePainter)?.let { it.state as? AsyncImagePainter.State.Success }?.painter?.intrinsicSize?.let { intrinsicSize ->
                            Modifier.aspectRatio(intrinsicSize.width / intrinsicSize.height)
                        } ?: Modifier),
                )
            }
            Text(
                text = showsResponseItem.name ?:"empty",
                Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

        }
    }
}

@Preview
@Composable
fun Preview() {
    //ShowItem(showsResponseItem = )
}