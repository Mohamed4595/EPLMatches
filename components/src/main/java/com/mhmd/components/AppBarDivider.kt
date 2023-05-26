package com.mhmd.components

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppBarDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        color = GrayDividerColor,
        thickness = .5.dp
    )
}
