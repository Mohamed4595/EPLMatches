package com.matches.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun TeamImage(imageLoader: ImageLoader, teamImage: String) {
    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(teamImage)
            .build(), imageLoader = imageLoader
    )
    Image(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(28.dp),
        painter = painter,
        contentDescription = null,
    )
}
