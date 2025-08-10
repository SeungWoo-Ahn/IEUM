package com.ieum.presentation.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.ieum.domain.model.image.ImageSource

@Composable
fun IEUMNetworkImage(
    modifier: Modifier = Modifier,
    source: ImageSource,
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        modifier = modifier,
        model = when (source) {
            is ImageSource.Local -> source.file.absolutePath
            is ImageSource.Remote -> source.url
        },
        contentDescription = null,
        contentScale = contentScale,
    )
}