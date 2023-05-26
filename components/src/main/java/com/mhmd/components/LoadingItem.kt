package com.mhmd.components

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingItem(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary
) {
    CircularProgressIndicator(
        color = color,
        modifier = modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}
