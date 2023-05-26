package com.mhmd.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(
    iconRes: Int,
    modifier: Modifier = Modifier,
    iconColor: Color? = null,
    backgroundColor: Color = Color.Transparent,
    iconSize:Int = 32,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = "",
        colorFilter = if (iconColor == null) null else ColorFilter.tint(color = iconColor),
        alignment = Alignment.Center,
        modifier = modifier
            .height(iconSize.dp)
            .width(iconSize.dp)
            .clip(
                RoundedCornerShape(iconSize.dp)
            )
            .clickable {
                onClick()
            }.background(backgroundColor)
            .padding(6.dp),
    )
}
